package com.ludtek.immoscraper.resource;

import java.io.Closeable;

import com.ludtek.immoscraper.model.Publication;

public interface PublicationReader extends Closeable{
	
	public Publication next();

}
