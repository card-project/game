package models.players;

import models.players.strategies.Strategy;
import models.stacks.DiscardStack;

/**
 * Virtual player.
 * 
 * @author Simon RENOULT
 * @version 0.1.1
 */
public class AIPlayer extends Player {

	// ------------ ATTRIBUTES ------------ //
	
	private Brain mind = new Brain(this);

	// ------------ METHODS ------------ //
	
	public void draw() {
		super.draw( mind.chooseStackToDraw() );
	}
	
	public void play( ) {
		//strategy.chooseCardToPlay();
	}
	
	public void discard( DiscardStack d ) {
		super.discard( mind.chooseCardToDiscard(), d );
	}	
	
	@Override
	public String toString() {
		return this.alias;
	}

}
