package com.ludtek.immoscraper.resource.file

import java.io.IOException;

import com.ludtek.immoscraper.model.Publication
import com.ludtek.immoscraper.resource.AbstractPublicationResourceBuilder
import com.ludtek.immoscraper.resource.PublicationReader
import com.ludtek.immoscraper.resource.PublicationWriter

class FilePublicationResourceBuilder extends AbstractPublicationResourceBuilder {

	@Override
	public boolean applies(URI url) {
		url.scheme == 'file'
	}

	@Override
	protected PublicationWriter createWriter(URI url) {
		return new PublicationFileWriter(url.path);
	}

	@Override
	protected PublicationReader createReader(URI url) {
		return new PublicationFileReader(url.path);
	}


	private static class PublicationFileWriter implements PublicationWriter {

		final ObjectOutputStream writer;

		public PublicationFileWriter(String filepath) {
			File file = new File(filepath)

			if(!file.exists()) {
				file.getParentFile().mkdirs()
				file.createNewFile()
			}

			writer = file.newObjectOutputStream()
		}


		@Override
		public void close() throws IOException {
			writer.close()
		}

		@Override
		public void write(Publication publication) {
			if(publication.description) {
				writer.writeObject(publication)
				writer.flush()
			}
		}
	}

	private static class PublicationFileReader implements PublicationReader {
		final ObjectInputStream reader
		
		private PublicationFileReader(String path) {
			File file = new File(path)
			
			if(!file.exists()) {
				throw new IllegalArgumentException("File not found for reading")				
			}
			
			reader = file.newObjectInputStream()
		}

		@Override
		public Publication next() {
			try {
				reader.readObject()
			} catch(EOFException eof) {
				return null
			}
		}

		@Override
		public void close() throws IOException {
			reader.close()
		}
	}
}
