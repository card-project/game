package controllers.tui;

import java.util.HashMap;
import java.util.Map.Entry;

import models.Game;
import models.cards.Card;
import models.cards.hazards.HazardCard;
import models.exceptions.IllegalMoveException;
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
 * @version 0.3.2
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
	public void run( int firstPlayerIndex ) {
		this.playCurrentGame( firstPlayerIndex );
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
			
			// STEP 2 : draw a card
			menu.inform( "-- > DRAWING STEP" );
			this.draw ( currentPlayer );
			
			// STEP 3 : play a card
			menu.inform( "-- > PLAYING STEP" );
			// TODO
			this.play( currentPlayer );
			
			// STEP 4 : discard a card
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
			
			// STEP 1 : Choose card to play.			
			bm.setCardToPlay( this.chooseCardToPlay( p ) );
			
			// STEP 2 : Check its type.
			
			// STEP 2.1 : Hazard card has been chosen.
			if ( bm.getCardToPlay() instanceof HazardCard ) {
				// STEP 2.1.1 : Choose an opponent.
				try {
					bm.setTarget( this.chooseTarget( p ) );
				} catch ( IllegalAccessError | IllegalMoveException e ) {
					e.printStackTrace();
				}
			}
			// STEP 2.2 : Remedy/Distance/Safety card has been chosen.
			else {
				try {
					bm.setTarget( p );
				} catch ( IllegalAccessError | IllegalMoveException e ) {
					e.printStackTrace();
				}
			}
			
			// STEP 3 : play the card
			p.play( bm );
		}
	}
	
	/**
	 * @param p
	 * @return
	 */
	private Card chooseCardToPlay( Player p ) {

		boolean userChoiceIsCorrect = true;
		int cardIndex = 0;
		
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
		
		return p.getHandStack().get( cardIndex - 1 );
	}
	
	/**
	 * @param p
	 * @return
	 */
	private Player chooseTarget( Player p ) {

		boolean userChoiceIsCorrect = true;
		int opponentIndex = 0;
		HashMap<Integer, Player> opponentMap = this.getOpponentsAliasMap( p );
		
		do {
			
			userChoiceIsCorrect = true;
			
			try {
				opponentIndex = menu.askTargetingOpponent( this.getOpponentsString( opponentMap ) );
			} catch ( NumberFormatException e ) {
				menu.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( ! opponentMap.containsKey( opponentIndex ) ) {
				menu.warn( "Please choose a correct index." );
				userChoiceIsCorrect = false;
			}
			
		} while ( ! userChoiceIsCorrect );
		
		return opponentMap.get( opponentIndex - 1 );
	}
	
	
	/**
	 * @param currentPlayer
	 * @return
	 */
	private HashMap<Integer, Player> getOpponentsAliasMap( Player currentPlayer ) {
		HashMap<Integer, Player> opponentsMap = new HashMap<>();
		
		for( int i = 0 ; i < currentGame.getPlayers().length ; i++ )  {
			if ( ! ( currentPlayer.equals( currentGame.getPlayers()[i] ) ) ) {
				opponentsMap.put( i, currentGame.getPlayers()[i] );
			}
		}
		
		return opponentsMap;
	}
	
	private String getOpponentsString( HashMap<Integer, Player> opponentsMap ) {
		String playerList = "| ";
		
		for ( Entry<Integer, Player> entry : opponentsMap.entrySet() ) { 
			playerList += entry.getKey() + " : " + entry.getValue().getAlias() + " | ";
		}
				
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
