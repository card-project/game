package controllers.tui;

import models.Game;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.IllegalAILevelException;
import models.exceptions.IllegalDistanceException;
import models.exceptions.IllegalHumanPlayerNumberException;
import models.exceptions.IllegalPlayerNumberException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import views.tui.TUIMenuView;

/**
 * TUI (Textual User Interface) view menu.
 * 
 * Allow user to setup the {@link Game} options such as
 * distance goal, players number, etc.
 * 
 * @author Simon RENOULT
 * @version 1.0.3
 */
public class TUIMenuController {

	// ------------ ATTRIBUTES ------------ //
	
	private TUIMenuView tui;
	private Game currentGame;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build the unique TUIMenuController object and assign
	 * the {@link TUIMenuView} and {@link Game} defined in the
	 * mother class to current object attributes.
	 * 
	 * @param mv {@link TUIMenuView} to work on.
	 * @param g {@link Game} to work on.
	 */
	public TUIMenuController( TUIMenuView mv, Game g ) {
		this.tui = mv;
		this.currentGame = g;
	}
	
	// ------------ METHODS ------------ //
	
	
	/**
	 * Unique public object method, it allows the user
	 * to set game options through the {@link TUIMenuView}. 
	 */
	public void run() {
		this.defineGlobalPlayersNumber();
		this.defineHumanPlayersNumber();
		this.defineDistanceGoal();
		this.defineHumanPlayersAlias();
		this.defineAIPlayersAlias();
	}

	private void defineGlobalPlayersNumber() {
		
		int playersNumber = 0;
		boolean userChoiceIsCorrect;
		
		do {
			userChoiceIsCorrect = true;
			
			try {
				playersNumber = tui.askGlobalPlayersNumber();
			} catch ( NumberFormatException e ) {
				tui.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				try {
					currentGame.setPlayersNumber( playersNumber );
				} catch ( IllegalPlayerNumberException e ) {
					tui.warn( "Please enter a number between 2 and 6." );
					userChoiceIsCorrect = false; 
				}
				
			}
			
		} while ( ! userChoiceIsCorrect );

	}
	
	private void defineHumanPlayersNumber() {
		int humanPlayersNumber = 0;
		boolean userChoiceIsCorrect;
		do {
			userChoiceIsCorrect = true;
			
			try {
				humanPlayersNumber = tui.askHumanPlayersNumber();
			} catch ( NumberFormatException e ) {
				tui.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				try {
					currentGame.setHumanPlayersNumber( humanPlayersNumber );
				} catch ( IllegalHumanPlayerNumberException e ) {
					tui.warn( "Please enter a number between 1 and " + currentGame.getPlayers().length + "." );
					userChoiceIsCorrect = false;
				}
			}

		} while ( ! userChoiceIsCorrect );
	}
	
	private void defineDistanceGoal() {
		
		int distanceGoal = 0;
		boolean userChoiceIsCorrect;
		
		do {

			userChoiceIsCorrect = true;
			
			try {
				distanceGoal = tui.askDistanceGoal();
			} catch ( NumberFormatException e ) {
				tui.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				try {
					currentGame.setDistanceGoal ( distanceGoal );
				} catch ( IllegalDistanceException e ) {
					tui.warn( "Please enter a number between 700 and 1000 and multiple of 25." );
					userChoiceIsCorrect = false;
				}	
			}
			
		} while ( ! userChoiceIsCorrect );
	}

	private void defineHumanPlayersAlias() {
		for ( int i = 0; i < currentGame.getPlayers().length ; i++ ) {
			if ( currentGame.getPlayers()[i] instanceof HumanPlayer ) {
				boolean aliasIsAlreadyChosen;
				do {
					aliasIsAlreadyChosen = false;
					try {
						currentGame.setPlayerAlias( currentGame.getPlayers()[i], tui.askPlayerAlias( i ) );
					} catch ( AliasAlreadyChosenException e ) {
						aliasIsAlreadyChosen = true;
						tui.warn( e.getMessage() );
					}
				} while ( aliasIsAlreadyChosen );
			}
		}
	}
	
	private void defineAIPlayersAlias() {
		currentGame.setAIPlayersAlias();
	}	
}
