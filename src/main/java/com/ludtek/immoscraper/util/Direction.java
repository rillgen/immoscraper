package com.ludtek.immoscraper.util;

public enum Direction {
	ASCENDING("asc"), DESCENDING("desc");
	
	private final String direction;
	
	public String getDirection() {
		return direction;
	}

	private Direction(String direction) {
		this.direction = direction;
	}
	
	public static Direction fromDirection(String direction) {
		for(Direction dir: Direction.values()) {
			if(dir.getDirection().equals(direction))
				return dir;
		}
		return ASCENDING;
	}

}
