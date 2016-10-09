package com.ludtek.immoscraper.resource;

import java.net.URI;

public abstract class AbstractPublicationResourceBuilder implements PublicationResourceBuilder {

	private void checkApplies(URI uri) {
		if(!applies(uri)) {
			throw new IllegalArgumentException("Invalid URI for this resource");
		}
	}
	
	@Override
	public final PublicationWriter writer(URI uri) {
		checkApplies(uri);
		return createWriter(uri);
	}
	
	@Override
	public final PublicationReader reader(URI uri) {
		checkApplies(uri);
		return createReader(uri);
	}
	
	protected abstract PublicationWriter createWriter(URI uri);
	
	protected abstract PublicationReader createReader(URI uri);
	
}
