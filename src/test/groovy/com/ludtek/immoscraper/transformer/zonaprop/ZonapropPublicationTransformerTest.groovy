package com.ludtek.immoscraper.transformer.zonaprop

import spock.lang.Specification;

class ZonapropPublicationTransformerTest extends Specification {
	
	def testDepto1() {
		given:
			def transformer = new ZonapropPublicationTransformer()
			def html = this.getClass().getResource('/providers/zonaprop/depto1.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 60000
			output.currency == "USD"
			output.provincia == "Capital Federal"
			output.partido == "Capital Federal"
			output.localidad == "Capital Federal"
			output.barrio == "San Cristobal"
			output.operation == "Venta"
			output.propertyType == "Departamento"
			output.title == "2 AMBIENTES |  AV SAN JUAN al 2300 D"
			output.url == "http://www.zonaprop.com.ar/propiedades/2-ambientes-av-san-juan-al-2300-d-41607613.html"
			output.id == 41607613
			output.area == 37
			output.provider == "zonaprop"
			output.dormcount == 1
			output.disposition == "Contrafrente"
			
			output.description == 'Unidad al contrafrente a reciclar, ubicada sobre Av. San Juan a dos cuadras Av. Jujuy. Con accesibilidad a numerosas líneas de colectivo, subte "E" y "H". Consta de living-comedor, dormitorio con placard, cocina independiente equipada, lavadero y baño completo. Los ambientes son muy luminosos y poseen piso de parquet. Oportunidad.'
		
	}

}
