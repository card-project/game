package models.players.strategies;

import models.cards.Card;
import models.stacks.game.GameStack;

/**
 * @author Adrien Saunier
 * @author Simon RENOULT
 *
 */
public interface Strategy {
	GameStack chooseStackToDraw();	
	Card chooseCardToPlay();
	Card chooseCardToDiscard();
}
