package com.ludtek.immoscraper

import com.ludtek.immoscraper.http.Crawler
import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropPublicationTransformer
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropURLGenerator

class Immoscraper {
	
	private static final File OUTPUT_FILE = new File("/Users/rillgen/immoscrapper", "test.csv")

	public static void main(String[] args) {

		def crawler = new Crawler(new ArgenpropURLGenerator(8119298), new ArgenpropPublicationTransformer())

		OUTPUT_FILE.createNewFile()
		
		def filewr = OUTPUT_FILE.newWriter()

		crawler.crawl() { Publication it ->
			if(it.description) {
				filewr.append(it.properties.findAll{key,value -> key != 'class'}.values().join(","))
				filewr.newLine()
			}
		}
		
		filewr.flush()
		filewr.close()

	}
}
