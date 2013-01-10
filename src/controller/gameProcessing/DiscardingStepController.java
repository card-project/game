package controller.gameProcessing;

import models.Game;
import models.cards.Card;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.AIPlayer;

/**
 * 
 * TUI Discarding step controller.
 * 
 * Perform the discarding process for each AI and Human players.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class DiscardingStepController extends StepController {

	// ------------ CONSTRUCTORS ------------ //

	public DiscardingStepController ( Game g ) {
		super( g );
	}

	// ------------ METHODS ------------ //

	public boolean run() throws AvailableCoupFourreException {
		return false;
	};

	public void performAIDiscardingStep() {
		Card discardedCard = ( ( AIPlayer ) super.getCurrentPlayer() ).discard();
	}

	public void performHumanDiscardingStep( Card chosenCard ) {
		super.getCurrentPlayer().discard( chosenCard );
	}
}
