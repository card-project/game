package controllers.tui.gameProcessing;

import models.Game;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.stacks.player.HandStack;
import views.tui.TUIGameView;

public class DiscardingStepController extends StepController {

	public DiscardingStepController( TUIGameView t, Game g) {
		super( t, g );
	}

	public void run() {
		this.discard();
	}
	
	private void discard() {
		if ( super.currentPlayer instanceof AIPlayer ) {
			( ( AIPlayer ) super.currentPlayer ).discard( currentGame.getDiscardStack() );
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			
			boolean userChoiceIsCorrect = true;
			int discardCardIndex = 0;
			
			do {
				try {
					discardCardIndex = tui.askDiscardingCardChoice( super.currentPlayer.getHandStack().toString() );
				} catch ( NumberFormatException e ) {
					tui.warn( "Please enter a number." );
					userChoiceIsCorrect = false;
				}
				
				if ( discardCardIndex < HandStack.MIN_CARD_NB || discardCardIndex > HandStack.MAX_IN_PLAY_CARD ) {
					tui.warn( "Please enter a number between " + HandStack.MIN_CARD_NB +
							" and " + HandStack.MAX_IN_PLAY_CARD );
					userChoiceIsCorrect = false;
				}
				
			} while ( ! userChoiceIsCorrect );
			
			super.currentPlayer.discard( discardCardIndex - 1, currentGame.getDiscardStack() );
		}
	}
}
