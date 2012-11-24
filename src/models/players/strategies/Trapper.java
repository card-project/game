package models.players.strategies;

import models.cards.Card;
import models.players.AIPlayer;
import models.stacks.GameStack;

public class Trapper implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	private AIPlayer player;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Trapper(AIPlayer player) {
		this.player = player;
	}
	
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
