package com.ludtek.immoscraper.resource;

import java.net.URI;

public interface PublicationResourceBuilder {

	public boolean applies(URI uri);
	
	public PublicationReader reader(URI uri);
	
	public PublicationWriter writer(URI uri);
	
}
