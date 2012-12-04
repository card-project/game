package controllers.tui.gameProcessing;

import models.Game;
import models.exceptions.DiscardChoiceOutOfBoundsException;
import models.exceptions.IllegalCardTypeException;
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
			try {
				( ( AIPlayer ) super.currentPlayer ).discard( );
			} catch ( IllegalCardTypeException e ) {
				e.printStackTrace();
			}
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			
			boolean userChoiceIsCorrect = true;
			int discardCardIndex = 0;
			
			do {
				userChoiceIsCorrect = true;
				
				try {
					discardCardIndex = tui.askDiscardingCardChoice( super.currentPlayer.getHandStack().toString() );
				} catch ( NumberFormatException e ) {
					tui.warn( "Please enter a number." );
					userChoiceIsCorrect = false;
				}
				
				if ( userChoiceIsCorrect ) {
					try {
						super.currentPlayer.discard( discardCardIndex - 1 );
					} catch ( IllegalCardTypeException e ) {
						tui.warn( e.getMessage() );
						userChoiceIsCorrect = false;
					} catch ( DiscardChoiceOutOfBoundsException e ) {
						tui.warn( e.getMessage() );
						userChoiceIsCorrect = false;
					}
				}
			} while ( ! userChoiceIsCorrect );
		}
	}
}
