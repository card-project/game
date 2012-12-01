package models.stacks.player;

import java.util.LinkedList;

import models.cards.Card;
import models.cards.CardType;
import models.exceptions.IllegalCardTypeException;
import models.stacks.CardsStack;

public abstract class PlayerStack extends CardsStack {

	// ------------ ATTRIBUTES ------------ //

	protected LinkedList<Card> cards;

	// ------------ CONSTRUCTORS ------------ //

	public PlayerStack() {
		this.cards = new LinkedList<Card>();
	}

	// ------------ METHODS ------------ //

	public Card get( int index ) {
		return this.cards.get( index );
	}

	public boolean exists( CardType ct ) {
		for ( Card c : this.cards ) {
			if ( ct == c.getType() ) {
				return true;
			}
		}

		return false;
	}

	public void remove( Card item ) {
		this.cards.remove( item );
	}
	
	public void shiftTo( CardsStack destination, Card c ) throws IllegalCardTypeException {
		destination.push( c );
		cards.remove( c );
	}
	
	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		this.cards.addFirst( item );
	}

	@Override
	public Card pop() {
		return this.cards.pop();
	}

	@Override
	public Card peek() {
		return this.cards.peek();
	}
	
	@Override
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	@Override
	public int size() {
		return this.cards.size();
	}

	@Override
	public String toString() {
		return this.cards.toString();
	}

	// ------------ GETTERS ------------ //

	public LinkedList<Card> getCards() {
		return this.cards;
	}

}
