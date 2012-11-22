package models.stacks;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import models.cards.Card;
import models.cards.CardType;

/**
 * Top abstract stacks mother class. Is a kind of LinkedList.
 * 
 * @author Simon RENOULT
 * @version 1.2.1
 */
public abstract class Stack {
	
	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -1306537448576986203L;

	protected LinkedList<Card> cards;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Stack() {
		this.cards = new LinkedList<Card>();
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		String s = "| ";
		for( Card c : this.cards ) {
			s += c.toString() + " | ";
		}

		return s;
	}

	public Card shiftTopCardTo( Stack destination ) {
		return shift( this.cards.getFirst(), destination );
	}

	public Card shift( Card c, Stack destination ) {
		destination.addFirst( c );
		this.cards.remove( c );
		return c;
	}
	
	public void addFirst( Card c ) {
		this.cards.addFirst( c );
	}
	
	public Card get( int index ) {
		return this.cards.get( index );
	}
	
	public Card getFirst() {
		try {
			return this.cards.getFirst();
		} catch ( NoSuchElementException e ) {
			return null;
		}
	}
	
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	public int size() {
		return this.cards.size();
	}
	
	public boolean exists( CardType type ) {
		for( Card c : this.cards ) {
			if ( c.getType() == type ) {
				return true;
			}
		}
		
		return false;
	}
	
	// ------------ GETTERS ------------ //
	
	// ------------ SETTERS ------------ //
	
}
