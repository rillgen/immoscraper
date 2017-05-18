package com.ludtek.immoscraper.transformer.argenprop

import com.ludtek.immoscraper.model.Publication;
import com.ludtek.immoscraper.transformer.PublicationTransformer
import com.ludtek.immoscraper.transformer.URLGenerator
import com.ludtek.immoscraper.util.Direction;
import static com.ludtek.immoscraper.util.Direction.DESCENDING;

class ArgenpropURLGenerator implements URLGenerator {
		
	private final int startId
	private final Direction direction
	private int current

	public ArgenpropURLGenerator(int startId, Direction direction) {
		this.startId = startId;
		this.direction = direction;
		this.current = startId;
	}	
	
	@Override
	public String nextPath() {
		current>0?"/Propiedades/Detalles/${direction==DESCENDING?current--:current++}--Departamento-en-Alquiler-en-Capital-Federal":null
	}

	@Override
	public String baseUrl() {
		"http://www.argenprop.com"
	}

}
