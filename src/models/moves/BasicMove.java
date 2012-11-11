package models.moves;

import models.cards.Card;
import models.cards.distances.DistanceCard;
import models.cards.hazards.HazardCard;
import models.cards.remedies.RemedyCard;
import models.cards.safeties.SafetyCard;
import models.exceptions.IllegalMoveException;
import models.players.Player;

/**
 * @author G4llic4
 *
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
		if ( c instanceof HazardCard ) {
			this.cardToPlay = c;			
		} else if ( c instanceof RemedyCard ) {
			if ( source.getBattleStack().get() )
		} else if ( c instanceof DistanceCard ) {
			
		} else if ( c instanceof SafetyCard ) {
			
		}
	}
	
	public void setTarget( Player player ) {
		this.target = player;
	}
	
	public void setSource( Player source ) {
		this.source = source;
	}
}
