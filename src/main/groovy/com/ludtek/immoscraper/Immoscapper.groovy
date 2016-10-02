package com.ludtek.immoscraper

import com.ludtek.immoscraper.http.Crawler
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropPublicationTransformer;
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropURLGenerator

class Immoscapper {

	public static void main(String[] args) {
		
		def crawler = new Crawler(new ArgenpropURLGenerator(8112019, 10), new ArgenpropPublicationTransformer())
		
		crawler.crawl()
		
	}
	
}
