package com.ludtek.immoscraper.resource;

import java.io.Closeable;

import com.ludtek.immoscraper.model.Publication;

public interface PublicationWriter extends Closeable{

	public void write(Publication publication);
	
}
