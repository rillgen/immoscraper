package com.ludtek.immoscraper.transformer;

import com.ludtek.immoscraper.model.Publication;

public interface PublicationTransformer {
	
	public Publication parse(String html);

}
