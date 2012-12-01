package models.moves;

import models.cards.Card;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.players.Player;


/**
 * Create an object that allow the manipulation of a {@link Card}
 * from a origin {@link Player} to his target (can be himself).
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public abstract class Move {

	// ------------ ATTRIBUTES ------------ //

	protected Player origin;
	protected Player target;
	protected Card cardToPlay;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Move( Player moveInitiator ) {
		if ( moveInitiator == null ) {
			throw new NullPointerException();
		} else {
			this.origin = moveInitiator;
		}
	}
	
	// ------------ METHODS ------------ //
	
	/**
	 * Executes the move using the origin player and the card he chosses
	 * to play on the target.
	 * @throws IllegalCardTypeException 
	 */
	public abstract void realize() throws IllegalCardTypeException;
	
	// ------------ GETTERS ------------ //
	
	public Card getCardToPlay() {
		return cardToPlay;
	}
	
	public Player getSource() {
		return origin;
	}

	public Player getTarget() {
		return target;
	}
	
	// ------------ SETTERS ------------ //
	
	/**
	 * @param c
	 * @throws IllegalMoveException
	 */
	public void setCardToPlay( Card c ) throws IllegalAccessError {
		if ( c == null ) {
			throw new IllegalAccessError( "Card must be defined." );
		} else {
			this.cardToPlay = c;			
		}
	}
	
	/**
	 * Define the target of the chosen card <br />
	 * => "Methods" design pattern.
	 *
	 * @param targetPlayer Target chosen for the selected card.
	 * @throws IllegalMoveException Target cannot be chosen with this card.
	 * @throws IllegalAccessError No card previously selected.
	 */
	final public boolean setTarget( Player targetPlayer ) throws IllegalMoveException, IllegalAccessError {
		if ( this.cardToPlay == null ) {
			throw new IllegalAccessError( "No card selected." );
		} else if ( targetPlayer == null ) {
			throw new IllegalAccessError( "Target player must be defined." );
		} else {
			if ( targetIsCompatible( targetPlayer ) ) {
				this.target = targetPlayer;
				return true;
			} else {
				throw new IllegalMoveException( "Target is incompatible with this card." );
			}
		}
	}

	/**
	 * Perform a verification on target player. As the verification content
	 * depends on the type of move, the method is set as abstract and is used
	 * through a final method : {@link Move#setTarget(Player)}.
	 * 
	 * @param targetPlayer
	 * @return True if the target is compatible , else false.
	 * @throws IllegalMoveException
	 */
	public abstract boolean targetIsCompatible ( Player targetPlayer ) throws IllegalMoveException;
	
}
