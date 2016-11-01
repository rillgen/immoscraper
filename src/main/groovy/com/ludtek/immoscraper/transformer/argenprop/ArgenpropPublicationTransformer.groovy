package com.ludtek.immoscraper.transformer.argenprop

import groovy.util.slurpersupport.GPathResult

import com.ludtek.immoscraper.model.GeoLocation
import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.transformer.AbstractHTMLPublicationTransformer

class ArgenpropPublicationTransformer extends AbstractHTMLPublicationTransformer {

	def BASE_URL = "http://www.argenprop.com"
	
	def PROVIDER = "argenprop"
	
	def PRICE_REGEX = ~/(.+) ([0-9\\.]+)/
	def ID_REGEX = ~/Propiedades\/Detalles\/([0-9]+)--.+/
	def AREA_REGEX = ~/(\d+) .+/
	//def GEO_REGEX = ~/.+var latitud = parseFloat\((.+)\);.+var longitud = parseFloat\((.+)\);.+/
	def GEO_REGEX = ~/(?s).+var latitud = parseFloat\((.+?)\);.+var longitud = parseFloat\((.+?)\);.+/

	@Override
	public Publication parse(GPathResult rootnode) {
		Publication parsed = new Publication()
		
		def additionalInfoNode = rootnode.body.'**'.find { node ->
			node.name() == 'div' && node.@class == 'section additionalInfo'
		}
		
		def meta = parseMeta(rootnode)
		
		parsed.with {
			
			switch(meta["cXenseParse:precio"]) {
				case PRICE_REGEX:
					currency = m[0][1] == '$'?"ARS":"USD"
					amount = m[0][2].replaceAll("\\.","") as long
			}
			
			operation = meta["cXenseParse:tipooperacion"]
			propertyType = meta["cXenseParse:tipopropiedad"]
			provider = PROVIDER
			
			partido = sanitize(meta["cXenseParse:partido"])
			localidad = sanitize(meta["cXenseParse:localidad"])
			provincia = sanitize(meta["cXenseParse:provincia"])
			barrio = sanitize(meta["cXenseParse:barrio"])
			
			url =  BASE_URL + meta["og:url"]
			
			title = sanitize(meta["og:title"].trim())
			
			def dormvalue = meta["cXenseParse:cantidaddedormitorios"]						
			dormcount = dormvalue&&dormvalue!="Monoambiente"?dormvalue.toInteger():0
			
			switch(meta["cXenseParse:superficiecubierta"]) {
				case AREA_REGEX:
					area = m[0][1].toInteger()
			}
			
			disposition = meta["cXenseParse:disposición"]
			
			description = sanitize(additionalInfoNode?.div?.find { it.@class == "fields" }?.text()?.trim())
			
			id = ID_REGEX.matcher(meta["og:url"])[0][1].toLong()
			
			//Geodata
			location = parseGeodata(rootnode)
			
			timestamp = new Date()
		}
		
		parsed
	}
	
	
	
	def parseGeodata(GPathResult rootnode) {
		rootnode.body.div.find {it.@class == 'gird-980 clearfix'}.script.findResult { script ->
			switch(script.text()) {
				case GEO_REGEX:
					def lat = m[0][1]?.toDouble()
					def lon = m[0][2]?.toDouble()
					if(lat&&lon) {
						return new GeoLocation(lat, lon)
					}
				default:
					return null	
			}			
		}
		
	}
		
}
