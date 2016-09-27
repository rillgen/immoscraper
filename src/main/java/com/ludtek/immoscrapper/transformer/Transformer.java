package com.ludtek.immoscrapper.transformer;

import java.util.List;

import com.ludtek.immoscrapper.model.Publication;

public interface Transformer {
	
	public List<Publication> parse(String html); 

}
