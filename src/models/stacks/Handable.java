package models.stacks;

import models.cards.Card;

public interface Handable {

	public void shiftTo( CardsStack destination, Card c );
	public void shiftTopCardTo( CardsStack destination );
	public void add ( Card c );
	public Card peek();
	public boolean isEmpty();
	public void push( Card item );
}
