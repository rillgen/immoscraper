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
			output.timestamp != null
			
			output.description == 'XINTEL(LEP-LEP-9034)  Unidad al contrafrente a reciclar  ubicada sobre Av. San Juan a dos cuadras Av. Jujuy. Con accesibilidad a numerosas líneas de colectivo  subte "E" y "H". Consta de living-comedor  dormitorio con placard  cocina independiente equipada  lavadero y baño completo. Los ambientes son muy luminosos y poseen piso de parquet. Oportunidad.   LEPORE PROPIEDADES consultas@lepore.com.ar 49055500  LEPORE Propiedades S.A.    CUIT : 33-60234274-9        C.U.C.I.C.B.A. Matrícula Nº 931 (Sucursales Caballito - Palermo - Centro)        AVISO LEGAL: Las descripciones arquitectónicas y funcionales  valores de expensas  impuestos y servicios  fotos y medidas de este inmueble son aproximados. Los datos fueron proporcionados por el propietario y pueden no estar actualizados a la hora de la visualización de este aviso por lo cual pueden arrojar inexactitudes y discordancias con las que surgen de los las facturas  títulos y planos legales del inmueble. El interesado deberá realizar las verificaciones respectivas previamente a la realización de cualquier operación  requiriendo por sí o sus profesionales las copias necesarias de la documentación que corresponda.        Venta supeditada al cumplimiento por parte del propietario de los requisitos de la resolución general Nº 2371 de la AFIP (pedido de COTI)'
		
	}
	
	def testDepto2() {
		given:
			def transformer = new ZonapropPublicationTransformer()
			def html = this.getClass().getResource('/providers/zonaprop/depto2.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 5600
			output.currency == "ARS"
			output.provincia == "Capital Federal"
			output.partido == "Capital Federal"
			output.localidad == "Capital Federal"
			output.barrio == "Villa del Parque"
			output.operation == "Alquiler"
			output.propertyType == "Departamento"
			output.title == "Excelente Loft en Villa Devoto (Sin comisión inmobiliaria)"
			output.url == "http://www.zonaprop.com.ar/propiedades/excelente-loft-en-villa-devoto-sin-comision-41535420.html"
			output.id == 41535420
			output.area == 50
			output.provider == "zonaprop"
			output.dormcount == 1
			output.disposition == null
			output.timestamp != null
			
			output.description == 'Se trata de un departamento tipo loft  ubicado en la zona de Villa del Parque  Consta de una amplia cocina  baño y una inmejorable vista al este de la ciudad.  El departamento está en excelentes condiciones  recién pintado y sin detalles.  Los muebles de la cocina son nuevos con estilo moderno  El edificio tiene una antiguedad de 6 años y posee piscina  con deck.  A pocas cuadras del shopping de Devoto.  Imperdible por su condición  ubicación y precio!  Tiene un tamaño de 50 M2.'
		
	}
	
	def testDeptoGBA() {
		given:
			def transformer = new ZonapropPublicationTransformer()
			def html = this.getClass().getResource('/providers/zonaprop/deptogba.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 65000
			output.currency == "USD"
			output.provincia == "Buenos Aires"
			output.partido == "Morón"
			output.localidad == "Castelar"
			output.barrio == "Castelar"
			output.operation == "Venta"
			output.propertyType == "Departamento"
			output.title == "Excelentes monoamb. MZ BOX. Ideal inversion de Pozo,Castelar.-"
			output.url == "http://www.zonaprop.com.ar/propiedades/excelentes-monoamb.-mz-box.-ideal-inversion-de-pozo-41482261.html"
			output.id == 41482261
			output.area == 37
			output.provider == "zonaprop"
			output.dormcount == 0
			output.disposition == "Frente"
			output.timestamp != null
			
			output.description == 'Excelente oportunidad  hermosos y modernos departamentos.  Venta de pozo  anticipo + cuotas.-  URBANO   HOME + OFFICE  Sobre la Avenida Mitre  a metros de la estación de Castelar  nace MZ Box con la premisa del uso profesional y respondiendo a la tendencia mundial de optimización de la tierra en las áreas centrales.  MZ Box se presenta como la opción ideal para el inversor rentista  avalado por el diseño de cada detalle  el confort del funcionamiento interior y su ubicación privilegiada.  12 Microdepartamentos  1 Local comercial  Terraza equipada  Laundry  Solárium  Parrilla  Cocheras opcionales  Detalles de diseño a los estándares MZ  Servicio de postventa  UBICACIÓN - B. Mitre 2423 (Castelar)'
		
	}
	
	def testDeptoAlqVen() {
		given:
			def transformer = new ZonapropPublicationTransformer()
			def html = this.getClass().getResource('/providers/zonaprop/alqven1.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 145000
			output.currency == "USD"
			output.provincia == "Buenos Aires"
			output.partido == "Moreno"
			output.localidad == "Paso del Rey"
			output.barrio == "Paso del Rey"
			output.operation == "Venta"
			output.propertyType == "Casa"
			output.title == "Hermoso Chalet  en Alquiler estilo quinta en Paso del Rey, calle Graham Bell"
			output.url == "http://www.zonaprop.com.ar/propiedades/hermoso-chalet-en-alquiler-estilo-quinta-en-paso-del-41607612.html"
			output.id == 41607612
			output.area == 590
			output.provider == "zonaprop"
			output.dormcount == 3
			output.disposition == null
			output.timestamp != null
			
			output.description != null
		
	}
	
	def testDeptoCordoba() {
		given:
			def transformer = new ZonapropPublicationTransformer()
			def html = this.getClass().getResource('/providers/zonaprop/deptocordoba.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.amount == 129000
			output.currency == "USD"
			output.provincia == "Cordoba"
			output.partido == null
			output.localidad == "Villa General Belgrano"
			output.barrio == null
			output.operation == "Venta"
			output.propertyType == "Departamento"
			output.title == "Champaquí Golf 0 - Villa General Belgrano - Córdoba"
			output.url == "http://www.zonaprop.com.ar/propiedades/champaqui-golf-0-villa-general-belgrano-cordoba-21788083.html"
			output.id == 21788083
			output.area == 90
			output.provider == "zonaprop"
			output.dormcount == 2
			output.disposition == "Frente"
			output.timestamp != null
			
			output.description != null
		
	}
	
	
	def testBoveda() {
		given:
			def transformer = new ZonapropPublicationTransformer()
			def html = this.getClass().getResource('/providers/zonaprop/boveda.html').text
			
		when:
			def output = transformer.parse(html)
			
		then:
			output != null
			output.propertyType == 'Bóveda, nicho o parcela'
		
	}

}
