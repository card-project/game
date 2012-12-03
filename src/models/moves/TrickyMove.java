package models.moves;

import models.exceptions.moveExceptions.IllegalMoveException;
import models.players.Player;


/**
 * Create an object manipulating a tricky move. <br />
 * 
 * It enables the use of a move in the non standard playing stream.
 * It happens when an opponent attacks you through a hazard and you
 * have the correspondent safety card. 
 * 
 * @deprecated Non implemented yet. 
 * @author Simon RENOULT
 * @version 0.1
 */
public class TrickyMove extends Move {

	public TrickyMove( Player moveInitiator ) {
		super( moveInitiator );
	}

	@Override
	public boolean realize() {
		return false;
		
	}

	@Override
	public boolean targetIsCompatible( Player targetPlayer )
			throws IllegalMoveException {
		return true;
	}
}
