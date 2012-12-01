package models.cards;


/**
 * Distance card "factory".
 * 
 * @author Simon RENOULT
 * @version 1.1.1
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
		
		switch ( rangeValue ) {
		case 25:
			this.type = CardType.Distance25;
			break;
		case 50:
			this.type = CardType.Distance50;
			break;
		case 75:
			this.type = CardType.Distance75;
			break;
		case 100:
			this.type = CardType.Distance100;
			break;
		case 200:
			this.type = CardType.Distance200;
			break;
		}
	}
	
	// ------------ METHODS ------------ //
	
	@Override
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
