package models.players;

import models.cards.Card;
import models.moves.Move;
import models.players.strategies.Strategy;

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
	
	public void discard() {
		
	}
	
	public Card draw() {
		return null;
		
	}
	
	public void player( Move m ) {
		
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
