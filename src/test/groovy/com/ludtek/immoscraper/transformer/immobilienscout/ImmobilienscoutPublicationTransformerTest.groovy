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
		output.amount == 1816
		output.area == 111
		output.barrio == 'Schöneberg_Schöneberg'
		output.currency == 'EUR'
		output.dormcount == 1.5
		output.id == 109663466
		output.localidad == 'Berlin'
		output.provincia == 'Berlin'
		output.propertyType == 'APARTMENT'
		output.provider == 'immobilienscout'
		output.title == '3-Zimmer-Wohnung mit 2 Balkonen im einzigartigen Schöneberg'
		output.operation == 'RENT'
		output.url == 'https://www.immobilienscout24.de/expose/109663466'
		output.timestamp != null

		//Deutschland
		output.category == 'APARTMENT_RENT'
		output.etage == 3
		output.plz == '10829'
		output.adresse == 'Bautzener Straße 35'
		output.balkon == true
		output.keller == true
		output.aufzug == true
		output.ebk == false
		output.garten == false

		//Geo
		output.location.lat == 52.491471396558694
		output.location.lon == 13.37169518423393
		output.description != null
	}


	def testWohnung2() {
		given:
		def transformer = new ImmobilienscoutPublicationTransformer()
		def html = this.getClass().getResource('/providers/immobilienscout/wohnung2.html').text

		when:
		def output = transformer.parse(html)

		then:
		output != null
		output.amount == 960
		output.area == 35
		output.barrio == 'Heddernheim'
		output.currency == 'EUR'
		output.dormcount == 2
		output.id == 89915229
		output.localidad == 'Frankfurt_am_Main'
		output.provincia == 'Hessen'
		output.propertyType == 'APARTMENT'
		output.provider == 'immobilienscout'
		output.title == 'Bezugsfrei!! Modernes Lifestyle: möblierte 2-Zimmer-Wohnung mit Pantry Küche - H2F'
		output.operation == 'RENT'
		output.url == 'https://www.immobilienscout24.de/expose/89915229'
		output.timestamp != null

		//Deutschland
		output.category == 'APARTMENT_RENT'
		output.etage == 0
		output.plz == '60439'
		output.adresse == 'Olof-Palme-Straße 31'
		output.balkon == false
		output.keller == false
		output.aufzug == true
		output.ebk == true
		output.garten == false

		//Geo
		output.location.lat == 50.17297554738965
		output.location.lon == 8.641633504474244
		output.description != null
	}
}
