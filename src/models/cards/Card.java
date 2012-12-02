package models.cards;

import java.util.ArrayList;

/**
 * Top Card class. Create a Card object with its type and its families.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public abstract class Card {
	
	// ------------ ATTRIBUTES ------------ //
	
	protected ArrayList<CardFamily> families;
	protected CardType type;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Card() { }
	
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
