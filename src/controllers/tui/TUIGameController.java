package controllers.tui;

import models.Game;
import models.cards.Card;
import models.cards.hazards.HazardCard;
import models.moves.BasicMove;
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
 * @version 0.2.2
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
	 * Unique public object method, it allows the user
	 * to play the game. 
	 * 
	 */
	public void run() {
		this.prepareGame();
		this.playCurrentGame( this.defineFirstPlayerIndex() );
	}

	/**
	 * Initialize some {@link Game} object attributes. 
	 */
	private void prepareGame() {
		currentGame.getDeckStack().shuffle();
		menu.inform( "Shuffling deck..." );
		
		currentGame.distributeCardsToPlayers();
		menu.inform( "Distributing cards to players..." );
	}
	
	/**
	 * Define the first player index.
	 * 
	 * @return The first player index as an <em>Integer</em>. 
	 */
	public int defineFirstPlayerIndex() {
		boolean userChoiceIsCorrect;
		int randomIndex = 0, 
			firstPlayerIndex = randomIndex;
		
		do {
			userChoiceIsCorrect = true;
			try {
				
				firstPlayerIndex = menu.askFirstPlayer( this.getPlayerListWithIndexString() );
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number" );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				if ( firstPlayerIndex < 0 || firstPlayerIndex > currentGame.getPlayers().length ) {
					userChoiceIsCorrect = false;
					menu.warn( "Please enter a number between 0 and " + currentGame.getPlayers().length );
				} else if ( firstPlayerIndex == randomIndex ) {
					firstPlayerIndex = this.getRandomFirstPlayerIndex();
				} else {
					// Need to decrement as the player chooses between 1 and X
					// instead of 0 and X-1.
					firstPlayerIndex--;
				}
			}
		} while ( ! userChoiceIsCorrect );
		
		return firstPlayerIndex;
	}
	
	/**
	 * @return
	 */
	private String getPlayerListWithIndexString() {
		String playerList = "| ";
		for ( int i = 0; i < currentGame.getPlayers().length; i++, playerList += " | " ) {
			playerList += (i+1) + " : " + currentGame.getPlayers()[i].getAlias();
		}
		
		return playerList;
	}
	
	/**
	 * @return
	 */
	private int getRandomFirstPlayerIndex() {
		return (int) ( Math.random() * ( currentGame.getPlayers().length ) );
	}
	
	/**
	 * Main game loop. 
	 * Chain the drawing, playing and discarding steps.
	 * 
	 * @param firstPlayerIndex Index of the first player.
	 */
	public void playCurrentGame( int firstPlayerIndex ) {
		
		boolean gameIsOver = false;
		int currentPlayerIndex = firstPlayerIndex;
		Player currentPlayer;
		
		do {
			currentPlayer = currentGame.getPlayers()[currentPlayerIndex];
			
			// STEP 1 : Show initial hand
			menu.inform( "Your hand : " + currentPlayer.getHandStack() );
			
			// STEP 2 : draw
			menu.inform( "-- > DRAWING STEP" );
			this.draw ( currentPlayer );
			
			// STEP 3 : play
			menu.inform( "-- > PLAYING STEP" );
			// TODO
			this.play( currentPlayer );
			
			// STEP 4 : discard
			if ( currentPlayer.getHandStack().size() > HandStack.MAX_CARD_NB ) {
				menu.inform( "-- > DISCARDING STEP" );
				this.discard ( currentPlayer );
			}
			
			// STEP 5 : check if game is over
			gameIsOver = currentPlayer.getDistanceStack().getTravelledDistance() == currentGame.getGoal();
			
			// STEP 6 : switch to next player
			if ( ! gameIsOver ) {
				currentPlayerIndex = ( ++currentPlayerIndex > currentGame.getPlayers().length - 1 ) ? currentPlayerIndex : 0 ;
			}
			
		} while ( ! gameIsOver );
		
		menu.inform( currentPlayer.getAlias() + " has won !" );
	}

	/**
	 * Drawing method. 
	 * Allow a player (AI or real) to draw a card.
	 * 
	 * @param p Player who draws. 
	 */
	private void draw ( Player p ) {
		if ( p instanceof AIPlayer ) {
			( ( AIPlayer ) p ).draw();
		} else if ( p instanceof HumanPlayer ) {
			
			Card drawnCrad = null;
			
			if ( currentGame.getDiscardStack().isEmpty() ) {
				menu.inform( "Discard stack is empty. Deck stack has been automatically chosen." );
				drawnCrad = p.draw( currentGame.getDeckStack() );
			} else if ( currentGame.getDeckStack().isEmpty() ) {
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
	
	/**
	 * Playing card method.
	 * Allow a player to play a card. 
	 * 
	 * @param p Player who plays.
	 */
	private void play( Player p ) {
		if ( p instanceof AIPlayer ) {
			( ( AIPlayer ) p ).play( );
		} else if ( p instanceof HumanPlayer ) {
			
			BasicMove bm = new BasicMove( p );
			boolean userChoiceIsCorrect = true;
			int cardIndex = 0;
			
			// STEP 1 : choose a card
			do {
				
				userChoiceIsCorrect = true;
				
				try {
					cardIndex = menu.askPlayingCard( p.getHandStack().toString() );
				} catch ( NumberFormatException e ) {
					menu.warn( "Please enter a number." );
					userChoiceIsCorrect = false;	
				}
				
				if ( cardIndex < HandStack.MIN_CARD_NB || cardIndex > HandStack.MAX_IN_PLAY_CARD ) {
					menu.warn( "Please enter a number between " + HandStack.MIN_CARD_NB +
							" and " + HandStack.MAX_IN_PLAY_CARD );
					userChoiceIsCorrect = false;
				}
				
			} while ( ! userChoiceIsCorrect );
			
			bm.setCardToPlay( p.getHandStack().get( cardIndex - 1 ) );
			
			// STEP 2 : check its type
			// STEP 2.1 : hazard case
			if ( bm.getCardToPlay() instanceof HazardCard ) {
				// STEP 2.1.1 : choose an opponent
				do {
					
					userChoiceIsCorrect = true;
					
					try {
						menu.askTargetingOpponent( this.getOpponentWithIndexString( p ) );
					} catch ( NumberFormatException e ) {
						menu.warn( "Please enter a number." );
						userChoiceIsCorrect = false;
					}
					
				} while ( ! userChoiceIsCorrect );
			}
			// STEP 2.2 : conventional case
			else {
				
			}
			
			
			// STEP 3 : play the card
		}
	}
	
	private String getOpponentWithIndexString( Player currentPlayer ) {
		String playerList = "| ";
		
		for( int i = 0 ; i < currentGame.getPlayers().length ; i++ )  {
			if ( ! currentGame.getPlayers()[i].equals( currentPlayer ) ) {
				playerList += i + " : " + currentGame.getPlayers()[i].getAlias();  
			}
		}
		
		playerList += " |";
		
		return playerList;
	}
	
	/**
	 * Discarding method. 
	 * Allow a player (AI or real) to discard a card.
	 * 
	 * @param p Player who discards. 
	 */
	private void discard( Player p ) {
		if ( p instanceof AIPlayer ) {
			( ( AIPlayer ) p ).discard( currentGame.getDiscardStack() );
		} else if ( p instanceof HumanPlayer ) {
			
			boolean userChoiceIsCorrect = true;
			int discardCardIndex = 0;
			
			do {
				try {
					discardCardIndex = menu.askDiscardingCardChoice( p.getHandStack().toString() );
				} catch ( NumberFormatException e ) {
					menu.warn( "Please enter a number." );
					userChoiceIsCorrect = false;
				}
				
				if ( discardCardIndex < HandStack.MIN_CARD_NB || discardCardIndex > HandStack.MAX_IN_PLAY_CARD ) {
					menu.warn( "Please enter a number between " + HandStack.MIN_CARD_NB +
							" and " + HandStack.MAX_IN_PLAY_CARD );
					userChoiceIsCorrect = false;
				}
				
			} while ( ! userChoiceIsCorrect );
			
			p.discard( discardCardIndex, currentGame.getDiscardStack() );
		}
	}
	
}
