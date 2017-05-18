package com.ludtek.immoscraper.transformer.immobilienscout

import com.ludtek.immoscraper.transformer.URLGenerator
import com.ludtek.immoscraper.util.Direction

import static com.ludtek.immoscraper.util.Direction.DESCENDING;

class ImmobilienscoutURLGenerator implements URLGenerator {
	
	private final int startId
	private final Direction direction
	private int current

	public ImmobilienscoutURLGenerator(int startId, Direction direction) {
		this.startId = startId;
		this.direction = direction;
		this.current = startId;
	}

	@Override
	public String nextPath() {
		current>0?"/expose/${direction==DESCENDING?current--:current++}":null;
	}

	@Override
	public String baseUrl() {
		"https://www.immobilienscout24.de"
	}

}
