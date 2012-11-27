package models.players.strategies;

import models.cards.Card;
import models.players.AIPlayer;
import models.stacks.game.GameStack;

public class Protector implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	private AIPlayer player = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Protector(AIPlayer player) {
		this.player = player;
	}
	
	// ------------ METHODS ------------ //

	@Override
	public GameStack chooseStackToDraw() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card chooseCardToPlay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card chooseCardToDiscard() {
		// TODO Auto-generated method stub
		return null;
	}

}
