package models.stacks;

import java.util.LinkedList;

import models.cards.Card;

/**
 * Top abstract stacks mother class. Is a kind of LinkedList.
 * 
 * @author Simon RENOULT
 * @version 1.1.1
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
		destination.getCards().addFirst( c );
		this.getCards().remove( c );
		return c;
	}

	// ------------ GETTERS ------------ //
	
	public LinkedList<Card> getCards() {
		return cards;
	}

	// ------------ SETTERS ------------ //
	
	public void setCards( LinkedList<Card> cards ) {
		this.cards = cards;
	}
}
