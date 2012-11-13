package models.cards.distances;

import models.cards.Card;

/**
 * Distance card "factory".
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class DistanceCard extends Card {
	
	public static final int MAX_25 = 6;
	public static final int MAX_50 = 6;
	public static final int MAX_75 = 6;
	public static final int MAX_100 = 9;
	public static final int MAX_200 = 3;
	
	private int range;

	public DistanceCard( int rangeValue ) {
		this.range = rangeValue;
	}
	
	public int getRange() {
		return range;
	}

	public void setRange( int range ) {
		this.range = range;
	}
	
	public String toString() {
		return "Distance " + this.range;
	}
}
