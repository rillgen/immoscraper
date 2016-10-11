package com.ludtek.immoscraper.transformer.argenprop

import com.ludtek.immoscraper.util.Direction;

import spock.lang.Specification;

class ArgenpropURLGeneratorTest extends Specification {
	
	def testGeneratorStart() {		
		given:
			def startId = 1000
			def direction = Direction.ASCENDING
			def generator = new ArgenpropURLGenerator(startId, direction)
			
		when:
			def result = generator.nextPath()
		
		then:
			result.startsWith "/Propiedades/Detalles/1000--"
		
	}

}
