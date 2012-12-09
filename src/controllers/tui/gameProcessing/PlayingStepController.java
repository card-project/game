package controllers.tui.gameProcessing;

import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
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
	public boolean run() {
		return this.play();
	}

	/**
	 * Playing card method.
	 * Allow a player to play a card. 
	 * 
	 * @param p Player who plays.
	 */
	private boolean play( ) {
		
		boolean replay = false;
		
		if ( super.currentPlayer instanceof AIPlayer ) {
			
			( ( AIPlayer ) super.currentPlayer ).play( );
		
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			
			if ( currentPlayer.canPlay( getOpponents( currentPlayer ), currentGame.getGoal() ) ) {

				boolean userChoiceIsCorrect;
				
				Card chosenCard = null;
				Player chosenTarget = null;
				
				do {
					
					userChoiceIsCorrect = true;
				
					// STEP 1 : Choose card to play.	
					chosenCard = this.chooseCardToPlay( super.currentPlayer );
					
					// STEP 2 : Check its type.
					// STEP 2.1 : Hazard card has been chosen.
					if ( chosenCard instanceof HazardCard ) {
						// STEP 2.1.1 : Choose an opponent.
						chosenTarget = this.chooseTarget( currentPlayer );
						userChoiceIsCorrect = ( ( HazardCard ) chosenCard ).isPlayableOn( chosenTarget );
					}
					// STEP 2.2 : Distance card has been chosen.
					else if ( chosenCard instanceof DistanceCard ) {
						chosenTarget = currentPlayer;
						userChoiceIsCorrect = ( ( DistanceCard ) chosenCard ).isPlayableOn( chosenTarget, currentGame.getGoal() );
					}
					// STEP 2.3 : Remedy card has been chosen.
					else if ( chosenCard instanceof RemedyCard ) {
						chosenTarget = currentPlayer;
						userChoiceIsCorrect = ( ( RemedyCard ) chosenCard).isPlayableOn( chosenTarget );
					}
					// STEP 2.4 : Safety card has been chosen.
					else if ( chosenCard instanceof SafetyCard ) {
						chosenTarget = currentPlayer;
						userChoiceIsCorrect = ( ( SafetyCard ) chosenCard).isPlayableOn( chosenTarget );
					}
				} while ( ! userChoiceIsCorrect );

				if ( chosenCard instanceof HazardCard ) {
					( ( HazardCard ) chosenCard ).playOn( currentPlayer, chosenTarget );
				} else if ( chosenCard instanceof DistanceCard ) {
					( ( DistanceCard ) chosenCard ).playOn( chosenTarget );
				} else if ( chosenCard instanceof RemedyCard ) {
					( ( RemedyCard ) chosenCard ).playOn( chosenTarget );
				} else if ( chosenCard instanceof SafetyCard ) {
					( ( SafetyCard ) chosenCard ).playOn( chosenTarget );
				}
			
			} else {
				System.out.println( "No card to play." );
			}
		}
		
		return replay;
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
		String playerList = "| ";
		for ( int i = 0; i < opponents.size() ; i++, playerList += " | " ) {
			playerList += (i+1) + " : " + opponents.get( i ).getAlias();
		}
		
		return playerList;
	}
}
