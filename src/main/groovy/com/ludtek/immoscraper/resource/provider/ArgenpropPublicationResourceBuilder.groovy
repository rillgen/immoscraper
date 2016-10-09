package com.ludtek.immoscraper.resource.provider;

import static groovyx.net.http.Method.GET
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter
import com.ludtek.immoscraper.transformer.PublicationTransformer
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropPublicationTransformer
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropURLGenerator

class ArgenpropPublicationResourceBuilder extends AbstractPublicationResourceBuilder {

	@Override
	public boolean applies(URI url) {
		url.scheme == 'provider' && url.host == 'argenprop'
	}

	@Override
	protected PublicationWriter createWriter(URI url) {
		return new UnsupportedOperationException()
	}

	@Override
	protected PublicationReader createReader(URI url) {
		return new ArgenpropPublicationReader()
	}

	private static class ArgenpropPublicationReader implements PublicationReader {
		
		final ArgenpropURLGenerator urlGenerator;
		final HTTPBuilder http
		final PublicationTransformer publicationTransformer = new ArgenpropPublicationTransformer()
		
		int count = 10
		
		public ArgenpropPublicationReader() {
			this.urlGenerator = new ArgenpropURLGenerator(8121158)
			this.http = new HTTPBuilder(urlGenerator.baseUrl())
			
		}

		@Override
		public void close() throws IOException {
			// Nothing to do
			
		}

		@Override
		public Publication next() {
			if(!count) {
				return null
			}
			
			def path = urlGenerator.nextPath()
			def publication
			
			http.request(GET, ContentType.TEXT) { req ->
				uri.path = path

				response.success = { resp, reader ->
					publication = publicationTransformer.parse(reader.text)
					count--
				}

				response.'404' = { resp -> println "Path ${path} Not found" }
			}
			
			publication		
		}
	}
	
}
