package controllers.tui.gameProcessing;

import models.Game;
import models.players.Player;
import views.tui.TUIGameView;

public abstract class StepController {

	protected TUIGameView tui;
	protected Game currentGame;
	protected Player currentPlayer;
	
	public StepController( TUIGameView t, Game g ) {
		super();
		this.tui = t;
		this.currentGame = g;
	}

	abstract public boolean run ( );
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer( Player currentPlayer ) {
		this.currentPlayer = currentPlayer;
	}
	
	
}
