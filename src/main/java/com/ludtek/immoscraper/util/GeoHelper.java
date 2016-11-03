package com.ludtek.immoscraper.util;

public class GeoHelper {

	public static boolean validGeopoints(Double lat, Double lon) {
		return lat != null && lon != null
				&& lat <= 90 && lat >= -90
				&& lon <= 180 && lon >= -180;
	}

}
