package controller.gameProcessing;

import models.Game;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

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
	
	public DrawingStepController(Game g ) {
		super(g );
	}

	// ------------ METHODS ------------ //
	
	public boolean run() {
		return this.draw();
	}
	
	private boolean draw() {
		
		boolean waitBeforeNextStep = false;

		if ( this.currentGame.getCurrentPlayer() instanceof AIPlayer ) {
			this.performAIPlayingStep();
		} else if ( this.currentGame.getCurrentPlayer() instanceof HumanPlayer ) {
			waitBeforeNextStep = this.performHumanPlayingStep();
		}
		
		return waitBeforeNextStep;
	}
	
	private void performAIPlayingStep() {
		( ( AIPlayer ) this.currentGame.getCurrentPlayer() ).draw();		
	}
	
	
	private boolean performHumanPlayingStep() {
		
		boolean waitBeforeNextStep = false;;
		
		if ( currentGame.getDiscardStack().isEmpty() ) {
			this.performHumanPlayingStep( currentGame.getDeckStack() );
		} else if ( currentGame.getDeckStack().isEmpty() ) {
			this.performHumanPlayingStep(  currentGame.getDiscardStack() );
		} else {
			waitBeforeNextStep = true;
			this.currentGame.getCurrentPlayer().fireChooseDeckToDrawEvent();
		}
		
		return waitBeforeNextStep;
	}
	
	public void performHumanPlayingStep(GameStack stack) {
		if(stack instanceof DeckStack)
			this.currentGame.getCurrentPlayer().draw( currentGame.getDeckStack() );
		else if(stack instanceof DiscardStack)
			this.currentGame.getCurrentPlayer().draw( currentGame.getDiscardStack() );
		else
			this.currentGame.getCurrentPlayer().fireChooseDeckToDrawEvent();
	}
}
