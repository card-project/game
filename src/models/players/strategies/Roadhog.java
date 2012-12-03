package models.players.strategies;

import models.cards.Card;
import models.players.AIPlayer;
import models.stacks.game.GameStack;

public class Roadhog implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	private AIPlayer player;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Roadhog(AIPlayer player) {
		this.player = player;
	}
	
	// ------------ METHODS ------------ //
	
	@Override
	public GameStack chooseStackToDraw() {
		return null;
	}

	@Override
	public Card chooseCardToPlay() {
		return null;
	}

	@Override
	public Card chooseCardToDiscard() {
		return null;
	}

}
