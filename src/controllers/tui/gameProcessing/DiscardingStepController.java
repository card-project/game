package controllers.tui.gameProcessing;

import models.Game;
import models.cards.Card;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.stacks.player.HandStack;
import views.tui.TUIGameView;

/**
 * @version 1.0
 *
 * TUI Discarding step controller.
 * 
 * Perform the discarding process for each AI and Human players.
 * 
 * @author Simon RENOULT
 */
public class DiscardingStepController extends StepController {

	// ------------ CONSTRUCTORS ------------ //
	
	public DiscardingStepController( TUIGameView t, Game g) {
		super( t, g );
	}

	// ------------ METHODS ------------ //
	
	public boolean run() {
		this.discard();
		
		return false;
	}
	
	private void discard() {
		if ( super.currentPlayer instanceof AIPlayer ) {
			this.performAIDiscardingStep();
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			this.performHumanDiscardingStep();
		}
	}
	
	private void performAIDiscardingStep() {
		Card discardedCard = ( ( AIPlayer ) super.currentPlayer ).discard( );
		tui.inform( "DISCARDED : " + discardedCard + '\n' );
	}
	
	private void performHumanDiscardingStep() {
		
		boolean userChoiceIsCorrect = true;
		int discardCardIndex = 0;
		
		do {
			
			userChoiceIsCorrect = true;
			
			try {
				discardCardIndex = tui.askDiscardingCardChoice( super.currentPlayer.getHand().toString() );
			} catch ( NumberFormatException e ) {
				tui.warn( "Please enter a number." );
				userChoiceIsCorrect = false;
			}
			
			if ( userChoiceIsCorrect ) {
				if ( discardCardIndex < HandStack.MIN_CARD_NB || discardCardIndex > HandStack.MAX_IN_PLAY_CARD ) {
					userChoiceIsCorrect = false;
				}
			}
		} while ( ! userChoiceIsCorrect );
		
		super.currentPlayer.discard( super.currentPlayer.getHand().get( discardCardIndex - 1 ) );
	}
}
