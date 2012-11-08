package controllers.tui;

import models.Game;
import models.cards.Card;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.HandStack;
import views.tui.TUIGameView;

/**
 * TUI (Textual User Interface) game view.
 * 
 * Allow users to play the game.
 * 
 * @author Simon RENOULT
 * @version 0.1
 *
 */
public class TUIGameController {

	// ------------ ATTRIBUTES ------------ //
	
	private TUIGameView menu;
	private Game currentGame;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build the unique TUIGameController object and assign the
	 * {@link TUIGameView} and {@link Game} defined in the mother
	 * class to current object attributes.
	 * 
	 * @param tuiGame {@link TUIGameView} to work on.
	 * @param currentGame {@link Game} to work on.
	 */
	public TUIGameController( TUIGameView gv, Game g ) {
		this.menu = gv;
		this.currentGame = g;
	}
	
	// ------------ METHODS ------------ //

	/**
	 * Unique public object method, it allows the use to
	 * play the game.
	 */
	public void run() {
		this.prepareGame();
		this.playCurrentGame( this.defineFirstPlayerIndex() );
		
	}

	private void prepareGame() {
		currentGame.getDeckStack().shuffle();
		menu.inform( "Shuffling deck..." );
		
		currentGame.distributeCardsToPlayers();
		menu.inform( "Distributing cards to players..." );
	}
	
	public int defineFirstPlayerIndex() {
		boolean userChoiceIsCorrect;
		int randomIndex = 0, 
			firstPlayerIndex = randomIndex;
		do {
			userChoiceIsCorrect = true;
			try {
				
				firstPlayerIndex = menu.askFirstPlayer( this.getPlayerListString() );
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number" );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				if ( firstPlayerIndex < 0 || firstPlayerIndex > currentGame.getNbPlayers() ) {
					userChoiceIsCorrect = false;
					menu.warn( "Please enter a number between 0 and " + currentGame.getNbPlayers() );
				} else if ( firstPlayerIndex == randomIndex ) {
					firstPlayerIndex = this.getRandomFirstPlayerIndex();
				} else {
					// Need to decrement as the player chooses between 1 and X
					// instead of 0 and X-1.
					firstPlayerIndex--;
				}
			}
		} while ( ! userChoiceIsCorrect );
		
		System.out.println( firstPlayerIndex );
		
		return firstPlayerIndex;
	}
	
	private String getPlayerListString() {
		String playerList = "| ";
		for ( int i = 0; i < currentGame.getPlayers().length; i++, playerList += " | " ) {
			playerList += (i+1) + " : " + currentGame.getPlayers()[i].getAlias();
		}
		
		return playerList;
	}
	
	private int getRandomFirstPlayerIndex() {
		return (int) ( Math.random() * ( currentGame.getNbPlayers() ) );
	}
	
	public void playCurrentGame( int firstPlayerIndex ) {
		boolean gameIsOver = false;
		int currentPlayerIndex = firstPlayerIndex;
		Player currentPlayer;
		do {
			currentPlayer = currentGame.getPlayers()[currentPlayerIndex];
			
			// STEP 1 : Show initial hand
			menu.inform( "Your hand : " + currentPlayer.getHandStack().toString() );
			
			// STEP 2 : draw
			this.draw ( currentPlayer );
			
			// STEP 3 : play
			// TODO
			
			// STEP 4 : discard
			if ( currentPlayer.getHandStack().getCards().size() > HandStack.MAX_CARD_NB ) {
				this.discard ( currentPlayer );
			}
			
			// STEP 5 : check if game is over
			gameIsOver = currentPlayer.getDistanceStack().getTravelledDistance() == currentGame.getGoal();
			
			// STEP 6 : switch to next player
			if ( ! gameIsOver ) {
				currentPlayerIndex = ( ++currentPlayerIndex > currentGame.getNbPlayers() - 1 ) ? currentPlayerIndex : 0 ;
			}
			
		} while ( ! gameIsOver );
		
		menu.inform( currentPlayer.getAlias() + " has won !" );
	}

	private void draw ( Player p ) {
		if ( p instanceof AIPlayer ) {
			( ( AIPlayer ) p ).draw();
		} else if ( p instanceof HumanPlayer ) {
			Card drawnCrad = null;
			
			if ( currentGame.getDiscardStack().getCards().isEmpty() ) {
				menu.inform( "Discard stack is empty. Deck stack has been automatically chosen." );
				drawnCrad = p.draw( currentGame.getDeckStack() );
			} else if ( currentGame.getDeckStack().getCards().isEmpty() ) {
				menu.inform( "Deck stack is empty. Discard stack has been automatically chosen." );
				drawnCrad = p.draw( currentGame.getDiscardStack() );
			} else {
				boolean userChoiceIsCorrect = true;
				String userChoice = "";
				do {
					userChoice = menu.askDrawingStack();
					if ( userChoice.equals( "D" ) ) {
						drawnCrad = p.draw( currentGame.getDeckStack() );
					} else if (userChoice.equals( "d" ) ) {
						drawnCrad = p.draw( currentGame.getDiscardStack() );
					} else {
						menu.warn( "Try again." );
						userChoiceIsCorrect = false;
					}
				} while ( ! userChoiceIsCorrect );
			}
			
			menu.inform( "Drawn card : " + drawnCrad );
		}
	}
	
	private void discard( Player p ) {
		if ( p instanceof AIPlayer ) {
			( ( AIPlayer ) p ).discard();
		} else if ( p instanceof HumanPlayer ){
			boolean userChoiceIsCorrect = true;
			int discardCardIndex = 0;
			do {
				try {
					discardCardIndex = menu.askDiscardingCardChoice();
				} catch ( NumberFormatException e ) {
					menu.warn( "Please enter a number." );
					userChoiceIsCorrect = false;
				}
				
				if ( discardCardIndex < HandStack.MIN_CARD_NB || discardCardIndex > HandStack.MAX_IN_PLAY_CARD ) {
					menu.warn( "Please enter a number between " + 
							HandStack.MIN_CARD_NB +
							" and " + HandStack.MAX_IN_PLAY_CARD );
					userChoiceIsCorrect = false;
				}
				
			} while ( ! userChoiceIsCorrect );
			
			p.discard( discardCardIndex, currentGame.getDiscardStack() );
		}
	}
	
}
