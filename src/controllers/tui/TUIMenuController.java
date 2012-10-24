package controllers.tui;

import models.Game;
import models.exceptions.AliasAlreadyChosenException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import views.tui.TUIMenu;

/**
 * TUI (Textual User Interface) view menu.
 * 
 * Allow user to setup the {@link Game} options such as
 * distance goal, players number, etc.
 * 
 * @author Simon RENOULT
 * @version 0.1
 */
public class TUIMenuController {

	// ------------ ATTRIBUTES ------------ //
	
	private TUIMenu menu;
	private Game currentGame;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build the unique TUIMenuController object and assign
	 * the {@link TUIMenu} and {@link Game} defined in the
	 * mother class to current object attributes.
	 * 
	 * @param m {@link TUIMenu} to work on.
	 * @param g {@link Game} to work on.
	 */
	public TUIMenuController( TUIMenu m, Game g ) {
		this.menu = m;
		this.currentGame = g;
	}
	
	// ------------ METHODS ------------ //
	
	
	/**
	 * Unique public object method, it allows the user
	 * to set game options through the {@link TUIMenu}. 
	 */
	public void run() {
		this.defineGlobalPlayersNumber();
		this.defineHumanPlayersNumber();
		this.defineDistanceGoal();
		this.defineHumanPlayersAlias();
		this.defineAIPlayersAlias();
		this.defineAIPlayersLevel();
		this.distributeCardsToPlayers();
	}

	private void defineGlobalPlayersNumber() {
		int playersNumber = 0;
		Boolean userChoiceIsCorrect;
		do {
			userChoiceIsCorrect = true;
			
			try {
				playersNumber = menu.askGlobalPlayersNumber();
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect && ( playersNumber < 1 || playersNumber > 6 ) ) {
				menu.warn( "Please enter a number between 1 and 6." );
				userChoiceIsCorrect = false;
			}
		} while ( ! userChoiceIsCorrect );

		currentGame.setPlayersNumber( playersNumber );
	}
	
	private void defineHumanPlayersNumber() {
		int humanPlayersNumber = 0;
		Boolean userChoiceIsCorrect;
		do {
			userChoiceIsCorrect = true;
			
			try {
				humanPlayersNumber = menu.askHumanPlayersNumber();
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect && ( humanPlayersNumber < 1 || humanPlayersNumber > currentGame.getNbPlayers() ) ) {
				menu.warn( "Please enter a number between 1 and " + currentGame.getNbPlayers() + "." );
				userChoiceIsCorrect = false;
			}
		} while ( ! userChoiceIsCorrect );
		
		currentGame.setHumanPlayers( humanPlayersNumber );
	}
	
	private void defineDistanceGoal() {
		int distanceGoal = 0;
		Boolean userChoiceIsCorrect;
		do {
			userChoiceIsCorrect = true;
			
			try {
				distanceGoal = menu.askDistanceGoal();
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect && ( distanceGoal < 700 || distanceGoal > 1000 || distanceGoal%25 != 0 ) ) {
				menu.warn( "Please enter a number between 700 and 1000 and multiple of 25." );
				userChoiceIsCorrect = false;
			}
		} while ( ! userChoiceIsCorrect );
		
		currentGame.setDistanceGoal ( distanceGoal );
	}

	private void defineHumanPlayersAlias() {
		for ( int i = 0; i < currentGame.getNbPlayers() ; i++ ) {
			if ( currentGame.getPlayers()[i] instanceof HumanPlayer ) {
				Boolean aliasIsAlreadyChosen;
				do {
					aliasIsAlreadyChosen = false;
					try {
						currentGame.setPlayerAlias( currentGame.getPlayers()[i], menu.askPlayerAlias( i ) );
					} catch ( AliasAlreadyChosenException e ) {
						aliasIsAlreadyChosen = true;
						menu.warn( e.getMessage() );
					}
				} while ( aliasIsAlreadyChosen );
			}
		}
	}
	
	private void defineAIPlayersAlias() {
		currentGame.setAIPlayersAlias();
	}
	
	private void defineAIPlayersLevel() {
		for( Player p : currentGame.getPlayers() ) {
			if ( p instanceof AIPlayer ) {
				Boolean userChoiceIsCorrect;
				int chosenLevel = 0;
				do {
					userChoiceIsCorrect = true;
					try {
						chosenLevel = menu.askAIPlayerLevel( (AIPlayer) p );
					} catch ( NumberFormatException e ) {
						menu.warn( "Please enter a number." );
						userChoiceIsCorrect = false;
					}
					
					if ( userChoiceIsCorrect && ( chosenLevel < 1 || chosenLevel > 3 ) ) {
						menu.warn( "Please enter a number between 1 and 3." );
						userChoiceIsCorrect = false;
					}
				} while ( ! userChoiceIsCorrect );

				currentGame.setAIPlayerLevel( (AIPlayer) p, chosenLevel );
			}
		}
	}

	public void distributeCardsToPlayers() {
		currentGame.preparePlayersHand();
	}
}
