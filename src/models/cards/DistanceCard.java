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
	
	// ------------ ATTRIBUTES ------------ //
	
	private int range;

	// ------------ CONSTRUCTORS ------------ //
	
	protected DistanceCard( int rangeValue ) {
		super();
		this.range = rangeValue;
		
		setType();
	}
	
	// ------------ METHODS ------------ //
	
	private void setType() {
		switch ( this.range ) {
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

	public void setRange( int range ) {
		this.range = range;
	}
}
