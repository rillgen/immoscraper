package com.ludtek.immoscraper.resource.elastic

import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter

class ElasticPublicationResourceBuilder extends AbstractPublicationResourceBuilder {

	@Override
	public boolean applies(URI uri) {
		return uri.scheme == 'elastic';
	}

	@Override
	protected PublicationWriter createWriter(URI uri) {
		return new ElasticPublicationWriter(uri);
	}

	@Override
	protected PublicationReader createReader(URI uri) {
		throw new UnsupportedOperationException()
	}

	private static class ElasticPublicationWriter implements PublicationWriter {

		final TransportClient transportClient
		final String elasticIndex
		final String elasticType

		private ElasticPublicationWriter(URI uri) {
			transportClient = TransportClient.builder().settings(Settings.builder().put("cluster.name", "immoscraper").build()).build()
			transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(uri.host), uri.port))

			def path = uri.path.split("/").grep()

			if(path.size()!=2) throw new IllegalArgumentException("Invalid path ${uri.path} must declare index/type")

			(elasticIndex, elasticType) = path
		}

		@Override
		public void close() throws IOException {
			transportClient.close()
		}

		@Override
		public void write(Publication publication) {
			def basemap = publication.properties.findAll {key, value -> key != "class"}
			if(publication.location) {
				basemap['location'] = [ publication.location.lon , publication.location.lat ]
			}
			transportClient.prepareIndex(elasticIndex,elasticType, createElasticId(publication)).setSource(basemap).get()
		}

		private static String createElasticId(Publication publication) {
			publication.with {"${provider}_${id}"}
		}
	}
}
