package com.ludtek.immoscraper.resource.elastic

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.geo.GeoPoint

class ElasticPublicationResourceBuilder extends AbstractPublicationResourceBuilder {

    @Override
    public boolean applies(URI uri) {
        return uri.scheme == 'elastic' || uri.scheme == 'elastics';
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

        final RestHighLevelClient restClient
        final String elasticIndex
        final String elasticType

        private ElasticPublicationWriter(URI uri) {
            //client = new PreBuiltTransportClient(Settings.builder().put("cluster.name", "immoscraper").build()).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(uri.host), uri.port))
            restClient = new RestHighLevelClient(RestClient.builder(new HttpHost(uri.host, uri.port, uri.scheme == 'elastic' ? 'http' : 'https')))

            def path = uri.path.split("/").grep()

            if (path.size() != 2) throw new IllegalArgumentException("Invalid path ${uri.path} must declare index/type")

            (elasticIndex, elasticType) = path
        }

        @Override
        public void close() throws IOException {
            restClient.close()
        }

        @Override
        public void write(Publication publication) {
            def basemap = publication.properties.findAll { key, value -> key != "class" }
            if (publication.location) {
                basemap['location'] = new GeoPoint(publication.location.lat, publication.location.lon).toString()
            }

            IndexRequest indexRequest = new IndexRequest(elasticIndex)
                    .type(elasticType)
                    .id(createElasticId(publication))
                    .source(basemap)

            restClient.index(indexRequest, RequestOptions.DEFAULT)
        }

        private static String createElasticId(Publication publication) {
            publication.with { "${provider}_${id}" }
        }
    }
}
