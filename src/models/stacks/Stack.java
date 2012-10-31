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
	private static final long serialVersionUID = 1L;

	public String toString() {
		String s = new String();
		for ( int i = 0; i < this.size() - 1; i++ ) {
			s += this.get( i ).toString() + " | ";
		}

		return s + this.getLast();
	}

	public Card shiftTopCard( Stack destination ) {
		return shift( this.getFirst(), destination );
	}

	public Card shift( Card c, Stack destination ) {
		this.remove( c );
		destination.addFirst( c );
		return c;
	}
}
