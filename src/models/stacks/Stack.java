package models.stacks;

import java.util.LinkedList;

import models.cards.Card;

/**
 * Top abstract stacks mother class. Is a kind of LinkedList.
 * 
 * @author Simon RENOULT
 * @version 1.1
 */
public abstract class Stack extends LinkedList<Card> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1306537448576986203L;

	public String toString() {
		String s = "| ";
		for( Card c : this ) {
			s += c.toString() + " | ";
		}

		return s;
	}

	public Card shiftTopCardTo( Stack destination ) {
		return shift( this.getFirst(), destination );
	}

	public Card shift( Card c, Stack destination ) {
		destination.addFirst( c );
		this.remove( c );
		return c;
	}
}
