package com.ludtek.immoscraper.resource.console

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter

class ConsolePublicationResourceBuilder extends AbstractPublicationResourceBuilder {

	@Override
	public boolean applies(URI uri) {
		return uri.scheme == 'console';
	}

	@Override
	protected PublicationWriter createWriter(URI uri) {
		new ConsolePublicationWriter(uri)
	}

	@Override
	protected PublicationReader createReader(URI uri) {
		throw new UnsupportedOperationException()
	}


	private static class ConsolePublicationWriter implements PublicationWriter {

		final PrintStream consoleStream

		private ConsolePublicationWriter(URI url) {
			consoleStream = url.host ==	'stderr'?System.err:System.out
		}

		@Override
		public void close() throws IOException {
		}

		@Override
		public void write(Publication publication) {
			consoleStream.println(publication)
		}
	}
}
