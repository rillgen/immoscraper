package com.ludtek.immoscraper.transformer.immobilienscout

import spock.lang.Specification;

class ImmobilienscoutPublicationTransformerTest extends Specification {

	def testWohnung1() {
		given:
		def transformer = new ImmobilienscoutPublicationTransformer()
		def html = this.getClass().getResource('/providers/immobilienscout/wohnung1.html').text
		
	when:
		def output = transformer.parse(html)
		
	then:
		output != null
		output.amount == 1211
		output.area == 121.41
		output.barrio == 'Frankenberg'
		output.currency == 'EUR'
		//output.description
		output.dormcount == 4
		output.id == 90141069
		output.localidad == 'Aachen'
		output.provincia == 'Nordrhein-Westfalen'
		output.propertyType == 'Erdgeschosswohnung'
		output.provider == 'immobilienscout'
		output.title == 'Preistipp***Neubau zum Einzug bereit***4 Zimmer Wohnung***Weitere Info : www.frankenberger-höfe.de'
		output.operation == 'RENT'
		output.url == 'https://www.immobilienscout24.de/expose/90141069'
		output.timestamp != null
		output.location.lat == 50.77052851513431
		output.location.lon == 6.112933897033864
		
		//Deutschland
		output.etage == 0
		output.plz == 52068
		output.adresse == 'In den Kronprinzengärten 11'
		output.balkon == true
		output.keller == true
		output.aufzug == true
		output.ebk == true
		output.garten == true
		output.gaestewc == true
		
	}
	
	
}
