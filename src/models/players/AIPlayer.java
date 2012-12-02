package models.players;

import java.util.ArrayList;

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
	
	private Brain mind = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public AIPlayer(ArrayList<Player> opponents) {
		this.mind = new Brain(this, opponents);
	}

	// ------------ METHODS ------------ //
	
	public void draw() {
		super.draw( mind.chooseStackToDraw() );
	}
	
	public void play( ) {
		//super.play(mind.chooseCardToPlay());
	}
	
	public void discard( DiscardStack d ) {
		super.discard( mind.chooseCardToDiscard(), d );
	}	
	
	@Override
	public String toString() {
		return this.alias;
	}

}
