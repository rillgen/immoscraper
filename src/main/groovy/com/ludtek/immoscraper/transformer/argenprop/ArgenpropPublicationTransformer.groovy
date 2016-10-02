package com.ludtek.immoscraper.transformer.argenprop

import java.util.regex.Matcher;

import org.ccil.cowan.tagsoup.Parser

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.transformer.PublicationTransformer

import groovy.util.slurpersupport.GPathResult

class ArgenpropPublicationTransformer implements PublicationTransformer {

	def BASE_URL = "http://www.argenprop.com"
	
	def PRICE_REGEX = ~/(.+) ([0-9\\.]+)/
	def ID_REGEX = ~/Propiedades\/Detalles\/([0-9]+)--.+/

	@Override
	public Publication parse(GPathResult rootnode) {
		Publication parsed = new Publication()
		
		def additionalInfoNode = rootnode.body.'**'.find { node ->
			node.name() == 'div' && node.@class == 'section additionalInfo'
		}
		
		def meta = rootnode.head.meta.collectEntries {
			[it.@name.text()?:it.@property.text(), it.@content.text()]
		}
		
		parsed.with {
			
			switch(meta["cXenseParse:precio"]) {
				case PRICE_REGEX:
					currency = m[0][1] == '$'?"ARS":"USD"
					amount = m[0][2].replaceAll("\\.","") as int
			}
			
			operation = meta["cXenseParse:tipooperacion"]
			propertyType = meta["cXenseParse:tipopropiedad"]
			
			partido = meta["cXenseParse:partido"]
			localidad = meta["cXenseParse:localidad"]
			provincia = meta["cXenseParse:provincia"]
			barrio = meta["cXenseParse:barrio"]
			
			url =  BASE_URL + meta["og:url"]
			
			title = meta["og:title"].trim()
			
			description = additionalInfoNode?.div?.find { it.@class == "fields" }?.text()?.replaceAll("<br>|\n|\t|\r|,|/", "")?.trim()
			
			id = ID_REGEX.matcher(meta["og:url"])[0][1].toLong()
			
		}
		
		parsed
	}
		
	@Override
	public Publication parse(String html) {
		def parser=new XmlSlurper(new Parser())
		parse(parser.parseText(html.trim()))
	}
	
	def getM() {
		Matcher.lastMatcher
	}

}
