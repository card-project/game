package models.players;

import java.util.ArrayList;

import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.moveExceptions.AvailableCoupFourreException;

/**
 * Virtual player.
 * 
 * An AI player owns a brain that chooses, depending on priorities, the
 * {@link Card} and {@link Player} to target.
 * 
 * @author Simon RENOULT
 * @author Adrien SAUNIER
 * @version 1.1.1
 */
public class AIPlayer extends Player {

	// ------------ ATTRIBUTES ------------ //

	private Brain brain;

	// ------------ CONSTRUCTORS ------------ //

	public AIPlayer ( int bib ) {
		super( bib );
	}

	// ------------ METHODS ------------ //

	/**
	 * Draw a new {@link Card} on the stack chosen by its {@link Brain}
	 * attribute.
	 * 
	 * @return The drawn {@link Card}.
	 */
	public Card draw() {
		return super.draw( brain.chooseStackToDraw() );
	}

	/**
	 * Play the {@link Card} depending on its class. The choice is realized by
	 * the {@link Brain} attribute. Return whether the played card implies to
	 * replay.
	 * 
	 * @return Return whether the played card implies to replay.
	 */
	public boolean play() throws AvailableCoupFourreException {
		Card chosenCard = brain.chooseCardToPlay();
		System.out.println( "ChosenCard " + chosenCard );
		boolean replay = false;

		if ( chosenCard instanceof DistanceCard ) {
			replay = ( ( DistanceCard ) chosenCard ).playOn( this );
		} else if ( chosenCard instanceof RemedyCard ) {
			replay = ( ( RemedyCard ) chosenCard ).playOn( this );
		} else if ( chosenCard instanceof SafetyCard ) {
			replay = ( ( SafetyCard ) chosenCard ).playOn( this, this.brain.getDistanceGoal() );
		} else if ( chosenCard instanceof HazardCard ) {
			Player target = brain.chooseTargetToAttack();
			try {
				replay = ( ( HazardCard ) chosenCard ).playOn( this, target );
			} catch ( AvailableCoupFourreException e ) {
			}
		}

		return replay;
	}

	/**
	 * Discard the {@link Card} chosen by the {@link Brain} attribute.
	 * 
	 * @return The drawn {@link Card}.
	 */
	public Card discard() {
		Card chosenCard = brain.chooseCardToDiscard();
		super.discard( chosenCard );

		return chosenCard;
	}

	// ------------ GETTERS ------------ //

	public Brain getBrain() {
		return brain;
	}

	// ------------ SETTERS ------------ //

	public void makeMeClever( ArrayList<Player> opponents, int distanceGoal ) {
		this.brain = new Brain( this, opponents, distanceGoal );
	}
}
