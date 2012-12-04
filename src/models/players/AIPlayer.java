package models.players;

import java.util.ArrayList;

import models.exceptions.IllegalCardTypeException;
import models.players.strategies.Strategy;
import models.stacks.game.DiscardStack;

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

	public AIPlayer() {
		
	}
	
	public AIPlayer( ArrayList<Player> opponents ) {
		this.mind = new Brain( this, opponents );
	}

	// ------------ METHODS ------------ //

	public void draw() {
		super.draw( mind.chooseStackToDraw() );
	}

	public void play() {
		// super.play(mind.chooseCardToPlay());
	}

	public void discard( ) throws IllegalCardTypeException {
		super.discard( mind.chooseCardToDiscard() );
	}

	@Override
	public String toString() {
		return this.alias;
	}

	public void setLevel( Integer chosenLevel ) {
		
	}

}
