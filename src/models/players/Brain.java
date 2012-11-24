package models.players;

import java.util.LinkedList;

import models.cards.Card;
import models.players.strategies.Protector;
import models.players.strategies.Strategy;
import models.stacks.GameStack;

public class Brain implements Strategy{
	
	// ------------ ATTRIBUTES ------------ //
	
	private LinkedList<Strategy> mind = new LinkedList<Strategy>();
	private AIPlayer player = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Brain(AIPlayer p) {
		
		this.player = p;
		this.mind.add(new Protector(this.player));
	}
	
	
	// ------------ METHODS ------------ //
	
	@Override
	public Card chooseCardToDiscard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Card chooseCardToPlay() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GameStack chooseStackToDraw() {
		// TODO Auto-generated method stub
		return null;
	}

}
