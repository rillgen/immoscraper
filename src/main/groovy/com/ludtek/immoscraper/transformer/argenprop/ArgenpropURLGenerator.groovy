package com.ludtek.immoscraper.transformer.argenprop

import com.ludtek.immoscraper.model.Publication;
import com.ludtek.immoscraper.transformer.PublicationTransformer
import com.ludtek.immoscraper.transformer.URLGenerator;;

class ArgenpropURLGenerator implements URLGenerator {
	
	private final int startId;
	private final int quantity;
	private int current;
	
	public ArgenpropURLGenerator(int startId) {
		this(startId, startId);
	}

	public ArgenpropURLGenerator(int startId, int quantity) {
		super();
		this.startId = startId;
		this.quantity = quantity;
		this.current = startId;
	}	
	
	@Override
	public String nextPath() {
		current>startId-quantity?"/Propiedades/Detalles/${current--}--Departamento-en-Alquiler-en-Capital-Federal":null;
	}

	@Override
	public String baseUrl() {
		return "http://www.argenprop.com";
	}

}
