package com.ludtek.immoscraper.transformer;

import com.ludtek.immoscraper.model.Publication;

import groovy.util.slurpersupport.GPathResult;

public interface PublicationTransformer {
	
	public Publication parse(String html);

	public Publication parse(GPathResult rootNode);
}
