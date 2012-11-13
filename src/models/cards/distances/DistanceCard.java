package models.cards.distances;

import models.cards.Card;
import models.cards.OppositeCardType;

/**
 * Distance card "factory".
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class DistanceCard extends Card {
	
	// ------------ ATTRIBUTES ------------ //
	
	private static final OppositeCardType OPPOSITE_CLASS = OppositeCardType.GoStop;
	
	public static final int MAX_25 = 6;
	public static final int MAX_50 = 6;
	public static final int MAX_75 = 6;
	public static final int MAX_100 = 9;
	public static final int MAX_200 = 3;
	
	private int range;

	// ------------ CONSTRUCTORS ------------ //
	
	public DistanceCard( int rangeValue ) {
		this.range = rangeValue;
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Distance " + this.range;
	}
	
	// ------------ GETTERS ------------ //
	
	public int getRange() {
		return range;
	}
	
	// ------------ SETTERS ------------ //

	public void setRange( int range ) {
		this.range = range;
	}
}
