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
		output.area == 121
		output.barrio == 'Frankenberg'
		output.currency == 'EUR'
		//output.description
		output.dormcount == 4
		output.id == 90141069
		output.localidad == 'Aachen'
		output.provincia == 'Nordrhein_Westfalen'
		output.propertyType == 'APARTMENT'
		output.provider == 'immobilienscout'
		output.title == 'Preistipp***Neubau zum Einzug bereit***4 Zimmer Wohnung***Weitere Info : www.frankenberger-höfe.de'
		output.operation == 'RENT'
		output.url == 'https://www.immobilienscout24.de/expose/90141069'
		output.timestamp != null
		output.location.lat == 50.77052851513431
		output.location.lon == 6.112933897033864
		output.description != null
		
		//Deutschland
		output.category == 'APARTMENT_RENT'
		output.etage == 0
		output.plz == '52068'
		output.adresse == 'In den Kronprinzengärten 11'
		output.balkon == true
		output.keller == true
		output.aufzug == true
		output.ebk == true
		output.garten == true	
	}
	
	def testWohnung2() {
		given:
		def transformer = new ImmobilienscoutPublicationTransformer()
		def html = this.getClass().getResource('/providers/immobilienscout/wohnung2.html').text
		
	when:
		def output = transformer.parse(html)
		
	then:
		output != null
		output.amount == 329976
		output.area == 91
		output.barrio == 'Plagwitz'
		output.currency == 'EUR'
		output.dormcount == 3
		output.id == 94741545
		output.localidad == 'Leipzig'
		output.provincia == 'Sachsen'
		output.propertyType == 'APARTMENT'
		output.provider == 'immobilienscout'
		output.title == 'EXKLUSIVER NEUBAU | PALMENGARTEN & CLARA PARK direkt um die Ecke | PROVISIONSFREI | LIFT | GARAGE'
		output.operation == 'BUY'
		output.url == 'https://www.immobilienscout24.de/expose/94741545'
		output.timestamp != null
		output.location.lat == 51.33208984254621
		output.location.lon == 12.343105898705554
		output.description != null
		
		//Deutschland
		output.category == 'APARTMENT_BUY'
		output.etage == 2
		output.plz == '04229'
		output.adresse == 'Karl-Heine-Straße 27'
		output.balkon == true
		output.keller == true
		output.aufzug == true
		output.ebk == false
		output.garten == false
	}

		def testWohnung3() {
		given:
		def transformer = new ImmobilienscoutPublicationTransformer()
		def html = this.getClass().getResource('/providers/immobilienscout/wohnung3.html').text
		
	when:
		def output = transformer.parse(html)
		
	then:
		output != null
		output.amount == 371
		output.area == 74
		output.barrio == 'Herford'
		output.currency == 'EUR'
		output.dormcount == 3
		output.id == 95412937
		output.localidad == 'Herford_Kreis'
		output.provincia == 'Nordrhein_Westfalen'
		output.propertyType == 'APARTMENT'
		output.provider == 'immobilienscout'
		output.title == '750 EUR Umzugszuschuss vom Vermieter geschenkt!'
		output.operation == 'RENT'
		output.url == 'https://www.immobilienscout24.de/expose/95412937'
		output.timestamp != null
		output.location.lat == 52.11205352427271
		output.location.lon == 8.70022767205948
		output.description != null
		
		//Deutschland
		output.category == 'APARTMENT_RENT'
		output.etage == 2
		output.plz == '32049'
		output.adresse == null
		output.balkon == false
		output.keller == false
		output.aufzug == false
		output.ebk == false
		output.garten == false
	}
		
}
