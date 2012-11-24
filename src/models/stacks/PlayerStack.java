package models.stacks;

import java.util.LinkedList;

import models.cards.Card;

public abstract class PlayerStack extends Stack {
	
	public LinkedList<Card> getCards() {
		return this.cards;		
	}

}
