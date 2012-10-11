package models.stacks;

import java.util.LinkedList;

import models.cards.Card;

public abstract class Stack extends LinkedList<Card> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String toString() {
		String s = new String();
		for (int i = 0; i < this.size() - 1; i++) {
			s += this.get( i ).toString() + " | ";
		}
		
		return s + this.getLast();
	}
	
	public static void transferTopCard( Stack source, Stack destination ) {
		transferCard( source.getFirst(), source, destination );
	}
	
	public static void transferCard( Card c, Stack source, Stack destination ) {
		source.remove( c );
		destination.addFirst( c );
	}
}
