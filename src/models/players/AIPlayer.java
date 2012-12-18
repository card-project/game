package models.players;

import java.util.ArrayList;

import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.strategies.Brain;

/**
 * @version 1.1.1
 * 
 * Virtual player.
 * 
 * @author Simon RENOULT
 * @author Adrien SAUNIER
 */
public class AIPlayer extends Player {

	// ------------ ATTRIBUTES ------------ //

	private Brain brain;
	private ArrayList<Player> opponents;
	private Integer distanceGoal;
	
	// ------------ CONSTRUCTORS ------------ //

	public AIPlayer() {

	}

	// ------------ METHODS ------------ //

	/**
	 * Draw a new {@link Card} on the stack chosen by its {@link Brain} attribute.
	 * @return The drawn {@link Card}.
	 */
	public Card draw() {
		return super.draw( brain.chooseStackToDraw() );
	}
	
	/**
	 * Play the {@link Card} depending on its class. The choice is realized
	 * by the {@link Brain} attribute. Return whether the played card
	 * implies to replay.
	 * @return Return whether the played card implies to replay.
	 */
	public boolean play() {
		Card chosenCard = brain.chooseCardToPlay();
		boolean replay = false;
		
		if ( chosenCard instanceof DistanceCard ) {
			replay = ( ( DistanceCard ) chosenCard ).playOn( this );
		} else if ( chosenCard instanceof RemedyCard ) {
			replay = ( ( RemedyCard ) chosenCard ).playOn( this );
		} else if ( chosenCard instanceof SafetyCard ) {
			replay = ( ( SafetyCard ) chosenCard ).playOn( this, this.distanceGoal );
		} else if ( chosenCard instanceof HazardCard ) {
			Player target = brain.chooseTargetToAttack(); 
			try {
				replay = ( ( HazardCard ) chosenCard ).playOn( this, target );
			} catch ( AvailableCoupFourreException e ) {
				e.printStackTrace();
			}
		}
		
		return replay;
	}

	/**
	 * Discard the {@link Card} chosen by the {@link Brain} attribute. 
	 * @return The drawn {@link Card}.
	 */	
	public Card discard() {
		Card chosenCard = brain.chooseCardToDiscard();
		super.discard( chosenCard );
		
		return chosenCard;
	}
	
	@Override
	public String toString() {
		return this.alias;
	}
	
	// ------------ GETTERS ------------ //
	
	public ArrayList<Player> getOpponents() {
		return opponents;
	}
	
	public Brain getBrain() {
		return brain;
	}
	
	// ------------ SETTERS ------------ //
	
	public void setOpponents( ArrayList<Player> opponents ) {
		this.opponents = opponents;
		setBrain( new Brain( this, opponents ) );
	}

	public void setBrain( Brain brain ) {
		this.brain = brain;
	}

	public void setDistanceGoal( Integer goal ) {
		this.distanceGoal = goal;
		this.brain.setDistanceGoal( this.distanceGoal );
	}
}
