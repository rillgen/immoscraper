package com.ludtek.immoscraper.transformer.argenprop

import spock.lang.Specification;

class ArgenpropURLGeneratorTest extends Specification {

	
	def testGeneration() {
		given:
			def start = 8003269
			def number = 10
			def generator = new ArgenpropURLGenerator(start, number)
			def generated = []
			
		when:
			def nextval = generator.nextPath()
			while(nextval) {
				generated << nextval
				nextval = generator.nextPath()
			}
		
		then:
			generated.size() == 10
			println generated
		
	}
}
