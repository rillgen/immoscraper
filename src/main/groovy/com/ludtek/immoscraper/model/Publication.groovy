package com.ludtek.immoscraper.model

import groovy.transform.*

@ToString
@Canonical
class Publication {
	long id
	String url
	String title
	String description
	String propertyType
	String operation
	String provincia
	String partido
	String localidad
	String barrio
	int amount
	String currency
	
	
}
