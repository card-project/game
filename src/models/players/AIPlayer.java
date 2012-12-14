package models.players;

import java.util.ArrayList;

import models.cards.Card;
import models.players.strategies.Brain;

/**
 * Virtual player.
 * 
 * @author Simon RENOULT
 * @author Adrien SAUNIER
 * 
 * @version 0.1.1
 */
public class AIPlayer extends Player {

	// ------------ ATTRIBUTES ------------ //

	private Brain brain;
	private ArrayList<Player> opponents;
	
	// ------------ CONSTRUCTORS ------------ //

	public AIPlayer() {
		
	}
	
	public AIPlayer( ArrayList<Player> opponents ) {
		this.opponents = opponents;
	}

	// ------------ METHODS ------------ //

	public Card draw() {
		return super.draw( brain.chooseStackToDraw() );
	}
	
	public boolean play() {
		return false;
	}
	
	public void discard() {
		super.discard( brain.chooseCardToDiscard() );
	}
	
	@Override
	public String toString() {
		return this.alias;
	}

}
