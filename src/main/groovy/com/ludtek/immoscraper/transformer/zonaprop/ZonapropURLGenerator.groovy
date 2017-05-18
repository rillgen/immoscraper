package com.ludtek.immoscraper.transformer.zonaprop

import static com.ludtek.immoscraper.util.Direction.DESCENDING

import com.ludtek.immoscraper.transformer.URLGenerator
import com.ludtek.immoscraper.util.Direction

class ZonapropURLGenerator implements URLGenerator {
	
	private final int startId
	private final Direction direction
	private int current

	public ZonapropURLGenerator(int startId, Direction direction) {
		this.startId = startId;
		this.direction = direction;
		this.current = startId;
	}
	
	@Override
	public String nextPath() {
		current>0?"/propiedades/Departamento-en-Alquiler-en-Capital-Federal-${direction==DESCENDING?current--:current++}.html":null
	}

	@Override
	public String baseUrl() {
		"http://www.zonaprop.com.ar"
	}

}
