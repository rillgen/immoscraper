package com.ludtek.immoscraper.serializer;

import java.util.Iterator;

import com.ludtek.immoscraper.model.Publication;

public interface PublicationSerializer {
	
	public void save(Publication publication);
	
	public Iterator<Publication> getAll();

}
