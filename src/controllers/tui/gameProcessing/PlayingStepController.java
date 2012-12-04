package controllers.tui.gameProcessing;

import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.moves.BasicMove;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.player.HandStack;
import views.tui.TUIGameView;

/**
 * @author Simon RENOULT
 * @version 0.2
 *
 */
public class PlayingStepController extends StepController {

	// ------------ CONSTRUCTORS ------------ //
	
	public PlayingStepController( TUIGameView t, Game g ) {
		super( t, g );
	}

	// ------------ METHODS ------------ //
	
	@Override
	public void run() {
		this.play();
	}

	/**
	 * Playing card method.
	 * Allow a player to play a card. 
	 * 
	 * @param p Player who plays.
	 */
	private void play( ) {
		if ( super.currentPlayer instanceof AIPlayer ) {
			
			( ( AIPlayer ) super.currentPlayer ).play( );
		
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			
			if ( currentPlayer.canPlay( getOpponents( currentPlayer ) ) ) {

				BasicMove bm = new BasicMove( super.currentPlayer );
				boolean userChoiceIsCorrect;
				
				do {
					userChoiceIsCorrect = true;
				
					// STEP 1 : Choose card to play.		
					bm.setCardToPlay( this.chooseCardToPlay( super.currentPlayer ) );
					
					// STEP 2 : Check its type.
					
					// STEP 2.1 : Hazard card has been chosen.
					if ( bm.getCardToPlay() instanceof HazardCard ) {
						// STEP 2.1.1 : Choose an opponent.
						try {
							userChoiceIsCorrect = bm.setTarget( this.chooseTarget( super.currentPlayer ) );
						} catch ( IllegalAccessError e ) { 
							userChoiceIsCorrect = false;
							tui.warn( e.getMessage() );
						} catch ( IllegalMoveException e ) {
							userChoiceIsCorrect = false;
							tui.warn( e.getMessage() );
						}
					}
					// STEP 2.2 : Remedy/Distance/Safety card has been chosen.
					else {
						try {
							userChoiceIsCorrect = bm.setTarget( super.currentPlayer );
						} catch ( IllegalAccessError e ) { 
							userChoiceIsCorrect = false;
							tui.warn( e.getMessage() );
						} catch ( IllegalMoveException e ) {
							userChoiceIsCorrect = false;
							tui.warn( e.getMessage() );
						}
					}					
				
				} while ( ! userChoiceIsCorrect );
			
				// STEP 3 : play the card
				try {
					bm.realize();
				} catch ( IllegalCardTypeException e ) {
					e.printStackTrace();
				}
			
			} else {
				System.out.println( "No card to play." );
			}
						
		}
	}
	
	/**
	 * Allow the player to choose a card to play in his hand.
	 * 
	 * @param p Player aiming to choose a card to play.
	 * @return Chosen card.
	 */
	private Card chooseCardToPlay( Player p ) {

		boolean userChoiceIsCorrect = true;
		int cardIndex = 0;
		
		do {
			
			userChoiceIsCorrect = true;
			
			try {
				cardIndex = tui.askPlayingCard( p.getHandStack().toString() );
			} catch ( NumberFormatException e ) {
				tui.warn( "Please enter a number." );
				userChoiceIsCorrect = false;	
			}
			
			if ( userChoiceIsCorrect ) {
				if ( cardIndex < HandStack.MIN_CARD_NB || cardIndex > HandStack.MAX_IN_PLAY_CARD ) {
					
					tui.warn( "Please enter a number between " + HandStack.MIN_CARD_NB +
							" and " + HandStack.MAX_IN_PLAY_CARD );
					userChoiceIsCorrect = false;
				
				}
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
		ArrayList<Player> opponents = this.getOpponents( p );
		
		do {
			
			userChoiceIsCorrect = true;
			
			try {
				opponentIndex = tui.askTargetingOpponent( this.getOpponentsAlias( opponents ) );
			} catch ( NumberFormatException e ) {
				tui.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( opponents.get( opponentIndex - 1 ) == null ) {
				tui.warn( "Please choose a correct index." );
				userChoiceIsCorrect = false;
			}
			
		} while ( ! userChoiceIsCorrect );
		
		return opponents.get( opponentIndex - 1 );
	}
	
	private ArrayList<Player> getOpponents( Player currentPlayer ) {
		ArrayList<Player> opponents = new ArrayList<Player>();
		for ( Player p : this.currentGame.getPlayers() ) {
			if ( ! p.equals( currentPlayer ) ) {
				opponents.add( p );
			}
		}
		
		return opponents;
	}
	
	private String getOpponentsAlias( ArrayList<Player> opponents ) {
		String s = "";
		for ( Player p : opponents ) {
			s += p.getAlias();
		}
		
		return s;
	}
}
