package com.ludtek.immoscraper.transformer.immobilienscout

import com.ludtek.immoscraper.model.GeoLocation;
import com.ludtek.immoscraper.model.Publication;
import com.ludtek.immoscraper.transformer.AbstractHTMLPublicationTransformer

import groovy.util.slurpersupport.GPathResult;;

class ImmobilienscoutPublicationTransformer extends AbstractHTMLPublicationTransformer {
	
	def DATA_REGEX = ~/(?ms).*var keyValues = \{"(.+)"\};.*/

	@Override
	public Publication parse(GPathResult rootNode) {
		Publication parsed = new Publication()
		
		def values = readKeyValues(rootNode)
		
		parsed.amount = values['obj_totalRent'] as Long
		parsed.area = values['obj_livingSpace'] as Float
		parsed.barrio = values['obj_regio3']
		parsed.localidad = values['obj_regio2']
		parsed.provincia = values['obj_regio1']
		parsed.currency = 'EUR'
		parsed.dormcount = values['obj_noRoomsRange'] as Integer
		parsed.id = values['obj_scoutId'] as Integer
		
		parsed.propertyType = rootNode.body.'**'.find { node ->
			node.name() == 'dd' && node.@class.toString().startsWith('is24qa-wohnungstyp ')
		}.text()?.trim()
		
		parsed.provider = 'immobilienscout'
		
		def meta = parseMeta(rootNode)
		parsed.title = meta['og:title']
		parsed.operation = values['obj_immotype'].split('_')[-1]
		parsed.url = "https://www.immobilienscout24.de/expose/${parsed.id}"
		
		//TODO: Geodata
		parsed.location = [lat: 3232, lon:323] as GeoLocation
		
		parsed.etage = values['obj_floor'] as Integer
		parsed.plz = values['geo_plz'] as Integer
		parsed.adresse = values['obj_streetPlain']?.replaceAll('_',' ')+ ' ' +values['obj_houseNumber']
		
		parsed.balkon = toBool(values['obj_balcony'])
		parsed.keller = toBool(values['obj_cellar'])
		parsed.ebk = toBool(values['obj_hasKitchen'])
		parsed.aufzug = toBool(values['obj_lift'])
		parsed.garten = toBool(values['obj_garden'])
		
		parsed.timestamp = new Date()
		
		return parsed;
	}
	
	def toBool(value) {
		'y' == value
	}

	private Map readKeyValues(GPathResult rootNode) {
		def dataScript = rootNode.head.'**'.find { node ->
			node.name() == 'script' && node.@type == 'text/javascript' && node.text().indexOf("var keyValues") != -1
		}?.text()?.trim()

		switch(dataScript) {
			case DATA_REGEX:
				return m[0][1].split("\",\"").collectEntries { pairtxt ->
					def pair = pairtxt.split("\":\"").collect { (it =~ "[\"]{0,1}(.+)[\"]{0,1}")[0][1] }
					[(pair[0]):pair[1]]
				}
		}
		return [:]
	}

}
