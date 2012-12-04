package models.stacks.game;

import java.util.Stack;

import models.cards.Card;
import models.cards.CardType;
import models.exceptions.IllegalCardTypeException;
import models.stacks.CardsStack;

public abstract class GameStack extends CardsStack {


	// ------------ ATTRIBUTES ------------ //
	
	protected Stack<Card> cards;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public GameStack() {
		this.cards = new Stack<Card>();
	}
	
	// ------------ METHODS ------------ //
	
	public void shiftTopCardTo( CardsStack destination ) {
		try {
			destination.push( this.cards.pop() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
	}
	
	public boolean exists( CardType ct ) {
		for ( Card c : this.cards ) {
			if ( ct == c.getType() ) {
				return true;
			}
		}

		return false;
	}
	
	@Override
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
	
	@Override
	public Card pop() {
		return cards.pop();
	}
	
	@Override
	public void push( Card item ) {
		this.cards.push( item );
	}
	
	@Override
	public Card peek() {
		return this.cards.peek();
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

	public Stack<Card> getCards() {
		return cards;
	}
}
