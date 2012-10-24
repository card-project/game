package models.moves;

import models.cards.Card;
import models.cards.hazards.HazardCard;
import models.exceptions.IllegalMoveException;
import models.players.Player;

/**
 * @author G4llic4
 *
 */
public class BasicMove extends Move {
	private Card cardToPlay;
	private Player source;
	private Player target;
	
	public BasicMove( Player origin ) {
		this.source = origin;
	}
	
	public Card getCardToPlay() {
		return cardToPlay;
	}
	
	/**
	 * @param c
	 * @throws IllegalMoveException
	 */
	public void setCardToPlay( Card c ) {
		this.cardToPlay = c;
	}
	
	public Player getTarget() {
		return target;
	}

	public void setTarget( Player player ) {
		this.target = player;
	}

	public Player getSource() {
		return source;
	}

	public void setSource( Player source ) {
		this.source = source;
	}

	public Boolean isAnAttack() {
		return this.cardToPlay instanceof HazardCard;
	}

	public void attack( Player target ) throws IllegalMoveException {
		
	}

	public void play() {
		
		
	}
}
