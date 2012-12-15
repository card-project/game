package controllers.tui.gameProcessing;

import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.player.HandStack;
import views.tui.TUIGameView;

/**
 * @version 1.0
 *
 * TUI Playing step controller.
 * 
 * Perform the playing process for each AI and Human players.
 * 
 * @author Simon RENOULT
 */
public class PlayingStepController extends StepController {

	// ------------ CONSTRUCTORS ------------ //
	
	public PlayingStepController( TUIGameView t, Game g ) {
		super( t, g );
	}

	// ------------ METHODS ------------ //
	
	@Override
	public boolean run() throws AvailableCoupFourreException {
		return this.play();
	}

	/**
	 * Playing card method.
	 * Allow a player to play a card. 
	 * 
	 * @return True if the player can replay.
	 * @throws AvailableCoupFourreException 
	 */
	private boolean play( ) throws AvailableCoupFourreException {
		boolean replay = false;
		if ( super.currentPlayer instanceof AIPlayer ) {
			return this.performAIPlayingStep();
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			return this.performHumanPlayingStep();
		}
		
		return replay;
	}
	
	private boolean performAIPlayingStep() {
		return ( ( AIPlayer ) super.currentPlayer ).play( );
	}
	
	private boolean performHumanPlayingStep() throws AvailableCoupFourreException {
		
		boolean replay = false;
		
		if ( currentPlayer.canPlay( super.currentGame.getOpponents( super.currentPlayer ), super.currentGame.getGoal() ) ) {

			boolean userChoiceIsCorrect;
			
			Card chosenCard = null;
			Player chosenTarget = null;
			
			do {
				
				userChoiceIsCorrect = true;
			
				chosenCard = this.chooseCardToPlay( super.currentPlayer );
				
				if ( chosenCard instanceof HazardCard ) {
					chosenTarget = this.chooseTarget( super.currentPlayer, (HazardCard) chosenCard );
					userChoiceIsCorrect = ( ( HazardCard ) chosenCard ).isPlayableOn( chosenTarget );
				} else {
					chosenTarget = super.currentPlayer;
					if ( chosenCard instanceof DistanceCard ) {
						userChoiceIsCorrect = ( ( DistanceCard ) chosenCard ).isPlayableOn( chosenTarget, super.currentGame.getGoal() );
					} else if ( chosenCard instanceof RemedyCard ) {
						userChoiceIsCorrect = ( ( RemedyCard ) chosenCard).isPlayableOn( chosenTarget );
					} else if ( chosenCard instanceof SafetyCard ) {
						userChoiceIsCorrect = ( ( SafetyCard ) chosenCard).isPlayableOn( chosenTarget );
					}
				}
				
				if( ! userChoiceIsCorrect ) {
					tui.warn( "Unauthorized choice. Try again." );
				}
				
			} while ( ! userChoiceIsCorrect );

			replay = this.currentPlayer.play( chosenCard, chosenTarget, super.currentGame.getGoal() );
		
		} else {
			tui.warn( "No card to play." );
			replay = false;
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
	 * @param p The current {@link Player} (in order to determine who are the opponents).
	 * @return The targeted {@link Player}.
	 */
	private Player chooseTarget( Player p, HazardCard c ) {

		boolean userChoiceIsCorrect = true;
		int opponentIndex = 0;
		ArrayList<Player> opponents = this.currentGame.getAttackableOpponents( p, c );
		
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
	
	private String getOpponentsAlias( ArrayList<Player> opponents ) {
		String playerList = "| ";
		for ( int i = 0; i < opponents.size() ; i++, playerList += " | " ) {
			playerList += (i+1) + " : " + opponents.get( i ).getAlias();
		}
		
		return playerList;
	}
}
