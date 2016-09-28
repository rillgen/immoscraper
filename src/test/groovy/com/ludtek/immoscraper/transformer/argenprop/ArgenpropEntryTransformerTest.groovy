package com.ludtek.immoscraper.transformer.argenprop

import spock.lang.Specification

import com.ludtek.immoscraper.transformer.PublicationEntryTransformer

class ArgenpropEntryTransformerTest extends Specification {
	
	def testParsing() {
		given:
			PublicationEntryTransformer transformer = new ArgenpropEntryTransformer()
			def html = this.getClass().getResource('/providers/argenprop/list1.html').text
		
		when: 
			def output = transformer.parse(html)			
			
		then:
			println output
	}

}
