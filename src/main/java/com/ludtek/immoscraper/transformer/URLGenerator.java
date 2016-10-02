package com.ludtek.immoscraper.transformer;

public interface URLGenerator {
	
	/**
	 * Generate next url
	 * 
	 * @return next path, null if we're done.
	 */
	public String nextPath(); 
	
	public String baseUrl();
	

}
