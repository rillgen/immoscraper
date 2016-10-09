package com.ludtek.immoscraper.model

import groovy.transform.*

@ToString
@Canonical
class Publication implements Serializable {
	int id
	String url
	String title
	String description
	String propertyType
	String operation
	String provincia
	String partido
	String localidad
	String barrio
	String provider
	int area
	int dormcount
	String disposition
	int amount
	String currency
	Date timestamp
	
}
