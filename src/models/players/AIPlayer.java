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
	
	private Integer level;
	private Strategy strategy;

	// ------------ METHODS ------------ //
	
	public void draw() {
		super.draw( strategy.chooseStackToDraw() );
	}
	
	public void play( ) {
		//strategy.chooseCardToPlay();
	}
	
	public void discard( DiscardStack d ) {
		super.discard( strategy.chooseCardToDiscard(), d );
	}	
	
	@Override
	public String toString() {
		return this.alias + " (" + this.level + ").";
	}

	// ------------ SETTERS ------------ //
	
	public void setLevel( Integer level ) {
		this.level = level;
	}
}
