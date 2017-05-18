package com.ludtek.immoscraper.resource.provider

import org.slf4j.LoggerFactory

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter
import com.ludtek.immoscraper.transformer.PublicationTransformer
import com.ludtek.immoscraper.transformer.immobilienscout.ImmobilienscoutPublicationTransformer
import com.ludtek.immoscraper.transformer.immobilienscout.ImmobilienscoutURLGenerator
import com.ludtek.immoscraper.util.Direction

import static groovyx.net.http.Method.GET

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

class ImmobilienscoutPublicationResourceBuilder extends AbstractPublicationResourceBuilder {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ImmobilienscoutPublicationResourceBuilder.class)

	@Override
	public boolean applies(URI uri) {
		uri.scheme == 'provider' && uri.host == 'immobilienscout'
	}

	@Override
	protected PublicationWriter createWriter(URI uri) {
		throw new UnsupportedOperationException()
	}

	@Override
	protected PublicationReader createReader(URI uri) {
		return new ImmobilienscoutPublicationReader(uri)
	}
	
	private static class ImmobilienscoutPublicationReader implements PublicationReader {
		
		final ImmobilienscoutURLGenerator urlGenerator;
		final HTTPBuilder http
		final PublicationTransformer publicationTransformer = new ImmobilienscoutPublicationTransformer()
		
		int count
		
		public ImmobilienscoutPublicationReader(URI uri) {
			def params = uri.path.split("/").grep()
			
			def pivot = params[0]?.toInteger()?:93342774
			count = params[1]?.toInteger()?:10
			def direction = Direction.fromDirection(params[2])
			
			this.urlGenerator = new ImmobilienscoutURLGenerator(pivot, direction)
			this.http = new HTTPBuilder(urlGenerator.baseUrl())
			
		}

		@Override
		public void close() throws IOException {
			// nothing to do
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
