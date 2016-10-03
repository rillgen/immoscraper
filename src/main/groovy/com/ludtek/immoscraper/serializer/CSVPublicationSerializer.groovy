package com.ludtek.immoscraper.serializer

import java.util.Iterator;

import com.ludtek.immoscraper.model.Publication;

class CSVPublicationSerializer implements PublicationSerializer {

	private final File file
	private static final String NEWLINE = System.getProperty("line.separator")

	public CSVPublicationSerializer(String pathname) {
		file = new File(pathname)
	}

	@Override
	public void save(Publication publication) {
		file.append("${publication}${NEWLINE}")
	}

	@Override
	public Iterator<Publication> getAll() {
		Iterator<String> strIterator = file.newReader().iterator()

		[hasNext: {strIterator.hasNext()},
			next: {
				def (asas,sasa,sarasa) = strIterator.next()?.split(",")
				new Publication()
			}
		] as Iterator<Publication>
	}
}
