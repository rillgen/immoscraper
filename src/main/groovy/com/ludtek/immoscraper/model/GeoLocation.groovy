package com.ludtek.immoscraper.model

import groovy.transform.Canonical;
import groovy.transform.ToString;

@ToString
@Canonical
class GeoLocation implements Serializable {
	double lat
	double lon
}
