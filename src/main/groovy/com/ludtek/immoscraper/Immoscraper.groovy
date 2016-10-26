package com.ludtek.immoscraper

import static java.lang.System.*

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationWriter
import com.ludtek.immoscraper.resource.console.ConsolePublicationResourceBuilder
import com.ludtek.immoscraper.resource.elastic.ElasticPublicationResourceBuilder
import com.ludtek.immoscraper.resource.file.FilePublicationResourceBuilder
import com.ludtek.immoscraper.resource.provider.ArgenpropPublicationResourceBuilder
import com.ludtek.immoscraper.resource.provider.ZonapropPublicationResourceBuilder

class Immoscraper {

	final static Logger LOGGER = LoggerFactory.getLogger(Immoscraper.class)

	final static List<PublicationResourceBuilder> resourcebuilders = [
		new ConsolePublicationResourceBuilder(),
		new ElasticPublicationResourceBuilder(),
		new FilePublicationResourceBuilder(),
		new ArgenpropPublicationResourceBuilder(),
		new ZonapropPublicationResourceBuilder()
	]

	public static void main(String[] args) {
		def cli = new CliBuilder(usage: 'immoscraper [options...] <source> <target>')

		cli.with {
			v(longOpt: 'verbose', "Make the operation more talkative", required:false)
			f(longOpt: 'force', "Force writing invalid properties", required:false)
		}

		def opt = cli.parse(args)

		if(!opt||opt.arguments().size()!=2) {
			cli.usage()
			exit(0)
		}

		def verbose = opt.v
		def force = opt.f

		LOGGER.info("Starting Immoscraper with arguments: ${opt.arguments()}")

		def inputUrl = new URI(opt.arguments()[0])
		def outputUrl = new URI(opt.arguments()[1])

		PublicationReader reader = resourcebuilders.find { it.applies(inputUrl) }?.reader(inputUrl)
		PublicationWriter writer = resourcebuilders.find { it.applies(outputUrl) }?.writer(outputUrl)

		if(!reader) {
			LOGGER.error("No compatible reader found for input url: ${inputUrl}")
			exit(0)
		}

		if(!writer) {
			LOGGER.error("No compatible writer found for input url: ${outputUrl}")
			exit(0)
		}

		def current

		int count = 0

		try {
			while(current = (reader.next())) {
				++count
				if(verbose)
					LOGGER.info("Writing record ${count}")
				if(force||isValid(current)) {
					writer.write(current)
				} else {
					LOGGER.info("Skipping invalid record ${verbose?current:''}")
				}
			}
		} catch(Exception e) {
			LOGGER.error("Processing failed", e)
		} finally {
			LOGGER.info("Processed ${count} records")
			reader.close()
			writer.close()
		}
	}
	
	private static void isValid(Publication publication) {
		publication.amount > 100 && publication.description && publication.title
	}
}
