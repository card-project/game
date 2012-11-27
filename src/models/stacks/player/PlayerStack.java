package models.stacks.player;

import java.util.LinkedList;

import models.cards.Card;
import models.cards.CardType;
import models.stacks.CardsStack;

public abstract class PlayerStack extends CardsStack {
	
	// ------------ ATTRIBUTES ------------ //
	
	protected LinkedList<Card> cards;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public PlayerStack() {
		this.cards = new LinkedList<Card>();
	}
	
	// ------------ METHODS ------------ //
	
	@Override
	public void add( Card c ) {
		this.cards.add( c );
	}
	
	public Card get ( int index ) {
		return this.cards.get( index );
	}
	
	@Override
	public void push( Card item ) {
		this.cards.addFirst( item );
	}
	
	@Override
	public boolean isEmpty() {
		return this.cards.isEmpty();
	}
		
	@Override
	public Card peek() {
		return this.cards.getFirst();
	}
	
	@Override
	public int size() {
		return this.cards.size();
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
	public void shiftTo( CardsStack destination, Card c ) {
		destination.add( c );
		cards.remove( c );
	}
	
	@Override
	public void shiftTopCardTo( CardsStack destination ) {
		shiftTo( destination, cards.getFirst() );
	}
	
	// ------------ GETTERS ------------ //
	
	public LinkedList<Card> getCards() {
		return this.cards;		
	}

}
