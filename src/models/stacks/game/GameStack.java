package models.stacks.game;

import java.util.Stack;
import java.util.Vector;

import models.cards.Card;
import models.stacks.CardsStack;

public abstract class GameStack extends CardsStack {


	// ------------ ATTRIBUTES ------------ //
	
	protected Stack<Card> cards;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public GameStack() {
		this.cards = new Stack<Card>();
	}
	
	// ------------ METHODS ------------ //
	
	public void add(Card c) {
		this.cards.push( c );
	}
	
	@Override
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	@Override
	public Card peek() {
		return cards.peek();
	}
	
	@Override
	public void push( Card item ) {
		this.cards.push( item );
	}
	
	@Override
	public int size() {
		return this.cards.capacity();
	}
	
	@Override
	public void shiftTo( CardsStack destination, Card c ) {
		if ( ! c.equals( cards.peek() )) {
			throw new IllegalAccessError( "Card access unauthorized." );
		} else {
			destination.push( this.cards.pop() );
		}
	}
	
	@Override
	public String toString() {
		return this.cards.toString();
	}
}
