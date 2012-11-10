package models.players;

import models.cards.Card;
import models.cards.distances.DistanceCard;
import models.moves.Move;
import models.players.strategies.Strategy;
import models.stacks.DiscardStack;

/**
 * Virtual player.
 * 
 * @author Simon RENOULT
 * @version 0.1
 */
public class AIPlayer extends Player {

	// ------------ ATTRIBUTES ------------ //
	
	private Integer level;
	private Strategy strategy;

	// ------------ METHODS ------------ //
	
	public void discard( DiscardStack discardStack ) {
		super.discard( strategy.chooseCardToDiscard(), discardStack );
	}
	
	public void draw() {
		super.draw( strategy.chooseStackToDraw() );
	}
	
	public void play( ) {
		//strategy.chooseCardToPlay();
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
