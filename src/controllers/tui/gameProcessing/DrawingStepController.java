package controllers.tui.gameProcessing;

import models.Game;
import models.cards.Card;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import views.tui.TUIGameView;

/**
 * @version 1.0.1
 *
 * TUI Drawing step controller.
 * 
 * Perform the drawing process for each AI and Human players.
 * 
 * @author Simon RENOULT
 */
public class DrawingStepController extends StepController {
	
	// ------------ CONSTRUCTORS ------------ //
	
	public DrawingStepController( TUIGameView t, Game g ) {
		super( t, g );
	}

	// ------------ METHODS ------------ //
	
	public boolean run() {
		this.draw();
		
		return false;
	}
	
	private void draw() {
		if ( super.currentPlayer instanceof AIPlayer ) {
			this.performAIPlayingStep();
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			this.performHumanPlayingStep();
		}
	}
	
	private void performAIPlayingStep() {

		( ( AIPlayer ) super.currentPlayer ).draw();

	}
	
	
	private void performHumanPlayingStep() {
		
		Card drawnCrad = null;
		
		if ( currentGame.getDiscardStack().isEmpty() ) {
			tui.inform( "[AUTOMATIC CHOICE] " );
			drawnCrad = super.currentPlayer.draw( currentGame.getDeckStack() );
		} else if ( currentGame.getDeckStack().isEmpty() ) {
			tui.inform( "[AUTOMATIC CHOICE] " );
			drawnCrad = super.currentPlayer.draw( currentGame.getDiscardStack() );
		} else {
		
			boolean userChoiceIsCorrect = true;
			String userChoice = "";
			
			do {

				tui.inform( "HAND : " + this.currentPlayer.getHandStack() );
				
				userChoiceIsCorrect = true;
				userChoice = tui.askDrawingStack( currentGame.getDiscardStack().peek().toString() );
				
				if ( userChoice.startsWith( "D" ) ) {
					drawnCrad = super.currentPlayer.draw( currentGame.getDeckStack() );
				} else if ( userChoice.startsWith( "d" ) ) {
					drawnCrad = super.currentPlayer.draw( currentGame.getDiscardStack() );
				} else {
					tui.warn( "Try again." );
					userChoiceIsCorrect = false;
				}
			} while ( ! userChoiceIsCorrect );
		}
		
		tui.inform( "DRAWN : " + drawnCrad + '\n' );
	}
}
