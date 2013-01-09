package controller.gameProcessing;

import models.Game;
import models.cards.Card;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.AIPlayer;
import models.players.Player;

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
	
	public PlayingStepController(Game g ) {
		super(g);
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
		if ( this.currentGame.getCurrentPlayer() instanceof AIPlayer ) {
			replay = this.performAIPlayingStep();
		}
		
		return replay;
	}
	
	public boolean performAIPlayingStep() throws AvailableCoupFourreException {
		return ( ( AIPlayer ) this.currentGame.getCurrentPlayer() ).play( );
	}
	
	public boolean currentPlayerCanPlay() {
		return this.currentGame.getCurrentPlayer().canPlay( super.currentGame.getOpponents( this.currentGame.getCurrentPlayer() ), super.currentGame.getGoal() );
	}
	
	public boolean performHumanPlayingStep(Card chosenCard, Player chosenTarget) throws AvailableCoupFourreException {
		return this.currentGame.getCurrentPlayer().play( chosenCard, chosenTarget, super.currentGame.getGoal() );
	}
	
//	public boolean performHumanPlayingStep(Card chosenCard) throws AvailableCoupFourreException {
//		
//		boolean replay = false;
//		
//		if ( this.currentGame.getCurrentPlayer().canPlay( super.currentGame.getOpponents( this.currentGame.getCurrentPlayer() ), super.currentGame.getGoal() ) ) {
//
//			boolean userChoiceIsCorrect;
//			
//			Player chosenTarget = null;
//			
//			do {
//				
//				userChoiceIsCorrect = true;
//			
//				chosenCard = this.chooseCardToPlay( this.currentGame.getCurrentPlayer() );
//				
//				if ( chosenCard instanceof HazardCard ) {
//					chosenTarget = this.chooseTarget( this.currentGame.getCurrentPlayer(), (HazardCard) chosenCard );
//					userChoiceIsCorrect = ( ( HazardCard ) chosenCard ).isPlayableOn( chosenTarget );
//				} else {
//					chosenTarget = this.currentGame.getCurrentPlayer();
//					if ( chosenCard instanceof DistanceCard ) {
//						userChoiceIsCorrect = ( ( DistanceCard ) chosenCard ).isPlayableOn( chosenTarget, super.currentGame.getGoal() );
//					} else if ( chosenCard instanceof RemedyCard ) {
//						userChoiceIsCorrect = ( ( RemedyCard ) chosenCard).isPlayableOn( chosenTarget );
//					} else if ( chosenCard instanceof SafetyCard ) {
//						userChoiceIsCorrect = ( ( SafetyCard ) chosenCard).isPlayableOn( chosenTarget );
//					}
//				}
//				
//				if( ! userChoiceIsCorrect ) {
//					// TODO
//					//tui.warn( "Unauthorized choice. Try again." );
//				}
//				
//			} while ( ! userChoiceIsCorrect );
//
//			replay = this.currentGame.getCurrentPlayer().play( chosenCard, chosenTarget, super.currentGame.getGoal() );
//		
//		} else {
//			// TODO
//			//tui.warn( "No card to play." );
//			replay = false;
//		}
//		
//		return replay;
//	}
	
}
