package models.stacks.player;

import java.util.LinkedList;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.stacks.CardsStack;

public abstract class PlayerStack extends CardsStack {

	// ------------ ATTRIBUTES ------------ //

	protected LinkedList<Card> cards;

	// ------------ CONSTRUCTORS ------------ //

	public PlayerStack() {
		this.cards = new LinkedList<Card>();
	}

	// ------------ METHODS ------------ //

	public boolean hasRemedyFor( HazardCard hc ) {
		for ( Card c : this.cards ) {
			if ( c instanceof RemedyCard ) {
				if ( ( (RemedyCard) c ).getFamily() == hc.getFamily() ) {
					return true;
				}
			}
		}

		return false;
	}

	public Card get( int index ) {
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
		destination.push( c );
		cards.remove( c );
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
