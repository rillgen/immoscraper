package com.ludtek.immoscraper.resource.provider

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter
import com.ludtek.immoscraper.transformer.PublicationTransformer
import com.ludtek.immoscraper.transformer.zonaprop.ZonapropPublicationTransformer
import com.ludtek.immoscraper.transformer.zonaprop.ZonapropURLGenerator
import com.ludtek.immoscraper.util.Direction
import org.slf4j.LoggerFactory

import static groovyx.net.http.Method.GET

class ZonapropPublicationResourceBuilder extends AbstractPublicationResourceBuilder {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ZonapropPublicationResourceBuilder.class)

	@Override
	public boolean applies(URI uri) {
		uri.scheme == 'provider' && uri.host == 'zonaprop'
	}

	@Override
	protected PublicationWriter createWriter(URI uri) {
		return new UnsupportedOperationException()
	}

	@Override
	protected PublicationReader createReader(URI uri) {
		return new ZonapropPublicationReader(uri);
	}
	
	private static class ZonapropPublicationReader implements PublicationReader {
		
		final ZonapropURLGenerator urlGenerator;
		final HTTPBuilder http
		final PublicationTransformer publicationTransformer = new ZonapropPublicationTransformer()
		
		int count
		
		public ZonapropPublicationReader(URI uri) {
			def params = uri.path.split("/").grep()
			
			def pivot = params[0]?.toInteger()?:41554903
			count = params[1]?.toInteger()?:10
			def direction = Direction.fromDirection(params[2])
			
			this.urlGenerator = new ZonapropURLGenerator(pivot, direction)
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
						if(!validate(publication)) {
							LOGGER.info("Invalid Zonaprop publication with id ${publication?.id}, skipping...")
							publication = null
							path = urlGenerator.nextPath()
						}
						count--
					}
	
					response.failure = { resp ->
						LOGGER.info("Path ${path} failed with status ${resp.status}, skipping...")
						path = urlGenerator.nextPath()
						count--
					}
				}
			}
			
			publication
		}
		
		def validate(Publication publication) {
			//Filter Columbian listings
			publication?.url&&!publication.url.startsWith("http://www.inmuebles24.co")
		}
	}
}
