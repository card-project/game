package models.moves;

import models.cards.Card;
import models.cards.distances.DistanceCard;
import models.cards.hazards.HazardCard;
import models.cards.remedies.RemedyCard;
import models.cards.safeties.SafetyCard;
import models.exceptions.IllegalMoveException;
import models.players.Player;

/**
 * @author Simon RENOULT
 * @version 0.1
 */
public class BasicMove extends Move {
	
	// ------------ ATTRIBUTES ------------ //
	
	private Card cardToPlay;
	private Player source;
	private Player target;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public BasicMove( Player origin ) {
		this.source = origin;
	}
	
	// ------------ METHODS ------------ //

	private boolean cardAndTargetAreCompatible( Player target ) {
		if ( target.equals( this.source ) ) {
			if ( this.cardToPlay instanceof RemedyCard 
					|| this.cardToPlay instanceof DistanceCard 
					|| this.cardToPlay instanceof SafetyCard  ) {
				return true;
			}
		} else if ( ! target.equals( this.source ) ){
			if ( this.cardToPlay instanceof HazardCard ) {
				return true;
			}
		}
		
		return false;
	}
	
	// ------------ GETTERS ------------ //
	
	public Card getCardToPlay() {
		return cardToPlay;
	}
	
	public Player getSource() {
		return source;
	}

	public Boolean isAnAttack() {
		return this.cardToPlay instanceof HazardCard;
	}

	public Player getTarget() {
		return target;
	}

	// ------------ SETTERS ------------ //
	
	/**
	 * @param c
	 * @throws IllegalMoveException
	 */
	public void setCardToPlay( Card c ) {
		if ( c == null ) {
			throw new IllegalAccessError( "Card must be defined." );
		} else {
			this.cardToPlay = c;			
		}
	}
	
	/**
	 * Define the target of the chosen card.
	 * 
	 * @param targetPlayer Target chosen for the selected card.
	 * @throws IllegalMoveException Target cannot be chosen with this card.
	 * @throws IllegalAccessError No card previously selected.
	 */
	public void setTarget( Player targetPlayer ) throws IllegalMoveException, IllegalAccessError {
		if ( this.cardToPlay == null ) {
			throw new IllegalAccessError( "No card selected." );
		} else {
			if ( this.cardAndTargetAreCompatible( targetPlayer ) ) {
				this.target = targetPlayer;
			} else {
				throw new IllegalMoveException( "Target must be an opponent." );
			}
		}
	}
}
