package models.stacks;

import models.cards.Card;

public interface Handable {

	public boolean isEmpty();

	public Card peek();

	public void push( Card item );

	public void shiftTo( CardsStack destination, Card c );

	public void shiftTopCardTo( CardsStack destination );
	
	public int size();

	public String toString();
}
