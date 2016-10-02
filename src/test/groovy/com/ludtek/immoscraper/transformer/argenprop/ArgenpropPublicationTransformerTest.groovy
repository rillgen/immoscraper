package com.ludtek.immoscraper.transformer.argenprop

import spock.lang.Specification;

class ArgenpropPublicationTransformerTest extends Specification {
	
	def testDepto1() {
		given:
			def transformer = new ArgenpropPublicationTransformer()
			def html = this.getClass().getResource('/providers/argenprop/depto1.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 5500
			output.currency == "ARS"
			output.provincia == "Capital Federal"
			output.partido == "Capital Federal"
			output.localidad == "Capital Federal"
			output.barrio == "Villa Crespo"
			output.operation == "Alquiler"
			output.propertyType == "Departamento"
			output.title == "Departamento en Alquiler en Capital Federal, Villa Crespo"
			output.url == "http://www.argenprop.com/Propiedades/Detalles/8118804--Departamento-en-Alquiler-en-Capital-Federal"
			output.id == 8118804l
			output.description == 'ALQUILER 2 AMB. EXCELENTE ESTADO. BAÑO Y COCINA REFACCIONADOS A NUEVO.EXCELENTE ZONA CERCA DE TODO: 3 CUADRAS DEL SUBTE "B" Y UNA CUADRA DE CORRIENTES.APTO PROFESIONAL.EL PRECIO DE LAS EXPENSAS INCLUYE AySA.MAZAL BIENES RAÍCES.154-400-4744  153-448-3355  4857-0155'
		
	}
	
	def testDepto2() {
		given:
			def transformer = new ArgenpropPublicationTransformer()
			def html = this.getClass().getResource('/providers/argenprop/depto2.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 53500
			output.currency == "USD"
			output.provincia == "Neuquen"
			output.partido == "Pdo. de Confluencia"
			output.localidad == "Neuquen"
			output.barrio == null
			output.operation == "Venta"
			output.propertyType == "Departamento"
			output.title == "Departamento en Venta en Neuquen, Pdo. de Confluencia, Neuquen"
			output.url == "http://www.argenprop.com/Propiedades/Detalles/8003269--Departamento-en-Venta-en-Pdo.-de-Confluencia"
			output.id == 8003269
			output.description == 'EXCELENTE OPORTUNIDAD DE INVERSIÓN. Monoambiente en venta con balcón y baño completo. Cuenta con 26 m2 de superficie cubierta. 2713 m2 de superficie total. Se ubica en el 3 piso al frente sobre calle Winter al 300. Se vende con contrato de alquiler vigente hasta Marzo 2017. Renta actual: $5.000.- El edificio cuenta con 1 ascensor  las cerraduras de las puertas poseen llaves magnéticas. Valor: U$D 53.500.- Contacto: SOLAR URBANO INVERSIONES INMOBILIARIAS Mail: info@solarurbano.com.ar -Santa Fe 575. NEUQUÉN CAPITAL. TE: (0299) 4436-914  4479-683. -Villegas 555. SAN MARTÍN DE LOS ANDES. TE: (02972) 425-181  (0294) 154596880. http:www.solarurbano.com.ar'
		
	}

}
