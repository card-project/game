package models.cards;


/**
 * Distance card "factory".
 * 
 * @author Simon RENOULT
 * @version 1.1
 */
public class DistanceCard extends Card {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final int MAX_25 = 6;
	public static final int MAX_50 = 6;
	public static final int MAX_75 = 6;
	public static final int MAX_100 = 9;
	public static final int MAX_200 = 3;
	
	private int range;

	// ------------ CONSTRUCTORS ------------ //
	
	public DistanceCard( int rangeValue ) {
		super();
		this.range = rangeValue;
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return String.valueOf( this.range );
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
