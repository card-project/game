package models.players.strategies;

import models.cards.Card;
import models.stacks.GameStack;

public interface Strategy {
	public GameStack chooseStackToDraw();	
	public Card chooseCardToPlay();
	public Card chooseCardToDiscard();
}
