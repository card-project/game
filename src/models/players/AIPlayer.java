package models.players;

import java.util.ArrayList;

import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.strategies.Brain;
import models.players.strategies.Trapper;

/**
 * @version 0.1.1
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

	public Card draw() {
		return super.draw( brain.chooseStackToDraw() );
	}
	
	// TODO END ME AND YOU WILL BE HAPPY !!
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
	
	public void discard() {
		super.discard( brain.chooseCardToDiscard() );
	}
	
	@Override
	public String toString() {
		return this.alias;
	}
	
	// ------------ GETTERS ------------ //
	
	public ArrayList<Player> getOpponents() {
		return opponents;
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
