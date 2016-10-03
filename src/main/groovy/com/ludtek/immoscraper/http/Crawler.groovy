package com.ludtek.immoscraper.http

import com.ludtek.immoscraper.transformer.PublicationTransformer;
import com.ludtek.immoscraper.transformer.URLGenerator

import groovyx.net.http.ContentType;
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET;

class Crawler {

	private final URLGenerator urlGenerator
	private final HTTPBuilder http
	private final PublicationTransformer publicationTransformer

	public Crawler(URLGenerator urlGenerator, PublicationTransformer publicationTransformer) {
		super()
		this.urlGenerator = urlGenerator
		this.http = new HTTPBuilder(urlGenerator.baseUrl())
		this.publicationTransformer = publicationTransformer
	}

	def crawl(int recordNumber=1000, Closure c) {

		def path = urlGenerator.nextPath()
		
		int count = 0

		while(path&&count<recordNumber) {

			http.request(GET, ContentType.TEXT) { req ->
				uri.path = path

				response.success = { resp, reader ->
					def publication = publicationTransformer.parse(reader.text)
					count++
					c(publication)
				}

				response.'404' = { resp -> println "Path ${path} Not found" }
			}

			path = urlGenerator.nextPath()
		}
	}
}
