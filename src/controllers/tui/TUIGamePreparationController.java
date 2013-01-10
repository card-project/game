package controllers.tui;

import models.Game;
import views.tui.TUIGamePreparationView;
import views.tui.TUIGameView;

/**
 * @version 1.0
 * 
 * TUI (Textual User Interface) game preparation menu.
 * 
 * Prepare the upcoming {@link Game}. 
 * 
 * @author Simon RENOULT
 */
public class TUIGamePreparationController {

	// ------------ ATTRIBUTES ------------ //

	private TUIGamePreparationView menu;
	private Game currentGame;	

	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build the unique TUIGameController object and assign the
	 * {@link TUIGameView} and {@link Game} defined in the mother
	 * class to current object attributes.
	 * 
	 * @param gv {@link TUIGamePreparationView} to work on.
	 * @param g {@link Game} to work on.
	 */
	public TUIGamePreparationController( TUIGamePreparationView gv, Game g ) {
		this.menu = gv;
		this.currentGame = g;
	}
	
	
	// ------------ METHODS ------------ //

	/**
	 * Initialize some {@link Game} object attributes. 
	 */
	public void run() {
		currentGame.getDeckStack().shuffle();
		menu.inform( "Shuffling deck..." + '\n' );
		
		currentGame.distributeCardsToPlayers();
		menu.inform( "Distributing cards to players..." + '\n' );
	}
	
	/**
	 * Define the first player index.
	 * 
	 * @return The first player index as an <em>Integer</em>. 
	 */
	public int getFirstPlayerIndex() {
		boolean userChoiceIsCorrect;
		final int RANDOM_INDEX = 0; 
		int	firstPlayerIndex = RANDOM_INDEX;
		
		do {
			
			userChoiceIsCorrect = true;
			
			try {
				
				firstPlayerIndex = menu.askFirstPlayer( this.getPlayersListWithIndex() );
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number" );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				if ( firstPlayerIndex < 0 || firstPlayerIndex > currentGame.getPlayers().length ) {
					userChoiceIsCorrect = false;
					menu.warn( "Please enter a number between 0 and " + currentGame.getPlayers().length );
				} else if ( firstPlayerIndex == RANDOM_INDEX ) {
					firstPlayerIndex = this.getFirstPlayerIndexAsRandom();
				} else {
					// Need to decrement as the player chooses between 1 and X
					// instead of 0 and X-1.
					firstPlayerIndex--;
				}
			}
			
		} while ( ! userChoiceIsCorrect );
				
		return firstPlayerIndex;
	}
	
	private int getFirstPlayerIndexAsRandom() {
		return (int) ( Math.random() * ( currentGame.getPlayers().length ) );
	}
	
	private String getPlayersListWithIndex() {
		String playerList = "| ";
		for ( int i = 0; i < currentGame.getPlayers().length; i++, playerList += " | " ) {
			playerList += (i+1) + " : " + currentGame.getPlayers()[i].getAlias();
		}
		
		return playerList;
	}

}
