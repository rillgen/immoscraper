package com.ludtek.immoscraper

import static org.elasticsearch.node.NodeBuilder.nodeBuilder

import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress

import com.ludtek.immoscraper.model.Publication

class Indexer {

	private static final File INPUT_FILE = new File("/Users/rillgen/immoscrapper", "test.csv")

	public static void main(String[] args) {
		BufferedReader fileReader = INPUT_FILE.newReader()

		//// Groovy way
		TransportClient client = TransportClient.builder().build()//.settings(Settings.settingsBuilder().put("node.local", "true").build())
		
//		.build()

		// add transport addresses
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))

		def fields = new Publication().getProperties().findAll {key, value -> key != "class"}.keySet() as List

		fileReader.eachLine {
			def fieldmap = [fields, it.split(',')].transpose().collectEntries { pair ->
				def key = pair[0]
				def value = pair[1]

				try {
					value = value.toInteger()
				} catch(Exception e) {}
				[key, value]
			}
			
			fieldmap["timestamp"] = new Date()

			println client.prepareIndex("immoscraper","publication").setSource(fieldmap).get()

		}
	}
}
