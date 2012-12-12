package controllers.tui.gameProcessing;

import models.Game;
import models.players.Player;
import views.tui.TUIGameView;

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
	
	protected TUIGameView tui;
	protected Game currentGame;
	protected Player currentPlayer;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public StepController( TUIGameView t, Game g ) {
		super();
		this.tui = t;
		this.currentGame = g;
	}

	// ------------ METHODS ------------ //
	
	/**
	 * Unique public access to a step controller.
	 * 
	 * The overall step controller processing are performed inside.
	 * 
	 * @return True if the execution of this method implies to replay.
	 */
	abstract public boolean run ( );
	
	// ------------ GETTERS ------------ //
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	// ------------ SETTERS ------------ //

	public void setCurrentPlayer( Player currentPlayer ) {
		this.currentPlayer = currentPlayer;
	}
	
	
}
