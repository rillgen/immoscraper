package com.ludtek.immoscraper.transformer.immobilienscout

import com.ludtek.immoscraper.model.Publication;
import com.ludtek.immoscraper.transformer.AbstractHTMLPublicationTransformer

import groovy.util.slurpersupport.GPathResult;;

class ImmobilienscoutPublicationTransformer extends AbstractHTMLPublicationTransformer {

	@Override
	public Publication parse(GPathResult rootNode) {
		Publication parsed = new Publication()
		
		return parsed;
	}

}
