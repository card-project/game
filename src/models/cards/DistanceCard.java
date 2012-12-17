package models.cards;

import models.exceptions.IllegalCardTypeException;
import models.players.Player;


/**
 * Distance card "factory".
 * 
 * @author Simon RENOULT
 * @version 1.1.1
 */
public class DistanceCard extends Card {
	
	// ------------ CONSTANTS ------------ //
	
	public static final int MAX_25 = 6;
	public static final int MAX_50 = 6;
	public static final int MAX_75 = 6;
	public static final int MAX_100 = 9;
	public static final int MAX_200 = 3;
	
	private static final CardFamily cf = CardFamily.Speed;
	
	// ------------ ATTRIBUTES ------------ //
	
	private int range;

	// ------------ CONSTRUCTORS ------------ //
	
	protected DistanceCard( CardType type ) {
		super( cf, type );
		try {
			setRange();
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
	}
	
	// ------------ METHODS ------------ //
	
	public boolean isPlayableOn ( Player p, int distanceGoal ) {
		if ( ! p.hasStarted() ) {
			return false;
		} else {
			if ( p.isAttacked() ) {
				return false;
			} else if ( p.isSlowed() && this.getRange() > 50 ) {
				return false;
			} else if ( this.getRange() + p.getTravelledDistance() > distanceGoal ) {
				return false;
			} else if ( this.getRange() == 200 && p.getDistanceStack().maxNumberOfDistance200IsReached() ) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean playOn( Player p ) {
		try {
			p.getHandStack().shiftTo( p.getDistanceStack(), this );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return String.valueOf( this.range );
	}
	
	// ------------ GETTERS ------------ //
	
	public int getRange() {
		return range;
	}
	
	// ------------ SETTERS ------------ //
	
	private void setRange() throws IllegalCardTypeException {
		switch ( super.type ) {
		case Distance25:
			this.range = 25;
			break;
		case Distance50:
			this.range = 50;
			break;
		case Distance75:
			this.range = 75;
			break;
		case Distance100:
			this.range = 100;
			break;
		case Distance200:
			this.range = 200;
			break;
		default:
			throw new IllegalCardTypeException();
		}
	}

	public void setRange( int range ) {
		if ( range == 25 && range == 50 && range == 75 && range == 100 && range == 200 ) {
			throw new IllegalAccessError();
		} else {
			this.range = range;
		}
	}
	
}
