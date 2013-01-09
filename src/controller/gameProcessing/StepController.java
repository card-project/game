package controller.gameProcessing;

import models.Game;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.Player;

/**
 * @version 1.0
 * 
 * Abstract step controller class.
 * 
 * Define the default behaviors of a StepController.  
 * 
 * @author Simon RENOULT
 */
public abstract class StepController {

	// ------------ ATTRIBUTES ------------ //
	
	protected Game currentGame;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public StepController(Game g ) {
		super();
		this.currentGame = g;
	}

	// ------------ METHODS ------------ //
	
	/**
	 * Unique public access to a step controller.
	 * 
	 * The overall step controller processing are performed inside.
	 * 
	 * @return True if the execution of this method implies to replay.
	 * @throws AvailableCoupFourreException 
	 */
	abstract public boolean run ( ) throws AvailableCoupFourreException;
	
	// ------------ GETTERS ------------ //
	
	public Player getCurrentPlayer() {
		return this.currentGame.getCurrentPlayer();
	}

	
	
}
