package com.ludtek.immoscraper.resource.provider;

import static groovyx.net.http.Method.GET
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import org.slf4j.LoggerFactory

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter
import com.ludtek.immoscraper.transformer.PublicationTransformer
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropPublicationTransformer
import com.ludtek.immoscraper.transformer.argenprop.ArgenpropURLGenerator
import com.ludtek.immoscraper.util.Direction

class ArgenpropPublicationResourceBuilder extends AbstractPublicationResourceBuilder {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ArgenpropPublicationResourceBuilder.class)

	@Override
	public boolean applies(URI url) {
		url.scheme == 'provider' && url.host == 'argenprop'
	}

	@Override
	protected PublicationWriter createWriter(URI url) {
		return new UnsupportedOperationException()
	}

	@Override
	protected PublicationReader createReader(URI uri) {
		return new ArgenpropPublicationReader(uri)
	}

	private static class ArgenpropPublicationReader implements PublicationReader {
		
		final ArgenpropURLGenerator urlGenerator;
		final HTTPBuilder http
		final PublicationTransformer publicationTransformer = new ArgenpropPublicationTransformer()
		
		int count
		
		public ArgenpropPublicationReader(URI uri) {
			def params = uri.path.split("/").grep()
			
			def pivot = params[0]?.toInteger()?:8121158
			count = params[1]?.toInteger()?:10
			def direction = Direction.fromDirection(params[2])
			
			this.urlGenerator = new ArgenpropURLGenerator(pivot, direction)
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
			
			while(!publication&&count) {
				http.request(GET, ContentType.TEXT) { req ->
					uri.path = path
	
					response.success = { resp, reader ->
						publication = publicationTransformer.parse(reader.text)
						count--
					}
	
					response.'404' = { resp -> 
						LOGGER.info("Path ${path} Not found, skipping...")
						path = urlGenerator.nextPath()
						count--
					}
				}
			}
			
			publication		
		}

	}	
}
