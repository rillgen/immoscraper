package com.ludtek.immoscraper.transformer;

import java.util.List;

import com.ludtek.immoscraper.model.PublicationEntry;

public interface PublicationEntryTransformer {
	
	public List<PublicationEntry> parse(String html); 

}
