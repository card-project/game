package models.cards;

import java.util.ArrayList;

/**
 * @version 1.0
 * 
 * Top Card class. Create a Card object with its type and its families.
 * 
 * @author Simon RENOULT
 */
public abstract class Card {
	
	// ------------ ATTRIBUTES ------------ //
	
	protected ArrayList<CardFamily> families = null;
	protected CardType type = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	protected Card( CardFamily initialFamily, CardType cardType ) {
		this.families = new ArrayList<CardFamily>();
		this.addFamily( initialFamily );
		
		this.type = cardType;
	}
	
	// ------------ METHODS ------------ //
	
	public void addFamily( CardFamily family ) {
		this.families.add( family );
	}
	
	public String toString() {
		return this.type.toString();
	}
	
	public boolean isGoRoll() {
		boolean isGoStopFamily = false;
		for( CardFamily cf : this.getFamilies() ) {
			if( cf == CardFamily.GoStop ) {
				isGoStopFamily = true;
			}
		}
		
		return this instanceof RemedyCard && isGoStopFamily ;
	}
	
	public boolean isSpeedLimit() {
		boolean isSpeedFamily = false;
		for( CardFamily cf : this.getFamilies() ) {
			if ( cf == CardFamily.Speed ) {
				isSpeedFamily = true;
			}
		}
		
		return this instanceof HazardCard && isSpeedFamily;
	}
	
	public boolean isRightOfWay() {
		boolean isSpeedOrGoStopFamily = false;
		
		for( CardFamily cf : this.getFamilies() ) {
			if ( cf == CardFamily.Speed || cf == CardFamily.GoStop ) {
				isSpeedOrGoStopFamily = true;
			}
		}
		
		return this instanceof HazardCard && isSpeedOrGoStopFamily;
	}
	
	public boolean counteract( CardFamily hazardFamily ) {
		if ( this instanceof RemedyCard || this instanceof SafetyCard ) {
			for ( CardFamily cf : this.getFamilies() ) {
				if ( cf == hazardFamily ) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	// ------------ GETTERS ------------ //
	
	public CardFamily getFamily() {
		return this.getFamilies().get( 0 );
	}
	
	public ArrayList<CardFamily> getFamilies() {
		return this.families;
	}
	
	public CardType getType() {
		return this.type;
	}
}
