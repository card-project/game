package controllers.tui.gameProcessing;

import models.Game;
import models.cards.Card;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import views.tui.TUIGameView;

public class DrawingStepController extends StepController {
	
	public DrawingStepController( TUIGameView t, Game g ) {
		super( t, g );
	}

	public boolean run() {
		this.draw();
		return false;
	}
	
	private void draw() {
		if ( super.currentPlayer instanceof AIPlayer ) {
			( ( AIPlayer ) super.currentPlayer ).draw();
		} else if ( super.currentPlayer instanceof HumanPlayer ) {
			
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
					userChoice = tui.askDrawingStack( currentGame.getDiscardStack().peek().toString() ).trim();
					System.out.println( userChoice );
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
}
