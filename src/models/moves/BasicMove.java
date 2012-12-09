package models.moves;

import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.exceptions.moveExceptions.InitialGoRollNotPlayedException;
import models.exceptions.moveExceptions.MaxNumberOfDistance200IsReached;
import models.exceptions.moveExceptions.PlayerIsAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotSlowedException;
import models.exceptions.moveExceptions.PlayerIsProtectedException;
import models.exceptions.moveExceptions.PlayerIsSlowedException;
import models.exceptions.moveExceptions.UnsuitableRemedyException;
import models.players.Player;

/**
 * Create an object manipulating a standard move.
 * 
 * It enables the use of a move (attack/hazard, defense/remedy, distance, safety)
 * in the standard playing stream.
 * 
 * @author Simon RENOULT
 * @version 1.1.2
 */
public class BasicMove extends Move {

	// ------------ ATTRIBUTES ------------ //

	// ------------ CONSTRUCTORS ------------ //

	public BasicMove( Player moveInitiator ) {
		super( moveInitiator );
	}
	
	// ------------ METHODS ------------ //

	@Override
	public boolean realize() throws IllegalCardTypeException {
		if ( this.origin == null || this.target == null || this.cardToPlay == null ) {
			throw new IllegalAccessError();
		} else {
			if ( cardToPlay instanceof DistanceCard ) {
				return realizeDistance();
			} else if ( cardToPlay instanceof HazardCard ) {
				return realizeHazard();
			} else if ( cardToPlay instanceof RemedyCard ) {
				return realizeRemedy();
			} else if ( cardToPlay instanceof SafetyCard ) {
				return realizeSafety();
			}
		}
		
		return false;
	}

	private boolean realizeDistance() throws IllegalCardTypeException {
		target.getHandStack().shiftTo( target.getDistanceStack(), cardToPlay );

		return false;
	}
	
	private boolean realizeHazard() throws IllegalCardTypeException {
		if ( cardToPlay.getType() == CardType.SpeedLimit ) {
			origin.getHandStack().shiftTo( target.getDistanceStack(), cardToPlay );
		} else {
			origin.getHandStack().shiftTo( target.getBattleStack(), cardToPlay );					
		}
		
		return false;
	}
	
	private boolean realizeRemedy() throws IllegalCardTypeException {
		if ( cardToPlay.getType() == CardType.EndOfLimit ) {
			target.getDistanceStack().discardHazards();
			origin.discard( cardToPlay );
		} else {
			if ( cardToPlay.getType() == CardType.GoRoll && ! target.hasStarted() ) {
				target.getHandStack().shiftTo( target.getBattleStack(), cardToPlay );
			} else {
				target.getBattleStack().discardHazards();
				target.discard( cardToPlay );
			}
		}
		
		return false;
	}
	
	private boolean realizeSafety() throws IllegalCardTypeException {
		target.getHandStack().shiftTo( target.getSafetyStack(), cardToPlay );
		target.getDistanceStack().increaseBy100();

		if ( target.isAttacked() ) {
			for ( CardFamily cf : cardToPlay.getFamilies() ) {
				if ( target.isAttacked() && cf == target.getBattleStack().peek().getFamily() ) {
					target.getBattleStack().discardHazards();
				}
			}
		}
		
		if ( target.isSlowed() && cardToPlay.getType() == CardType.RightOfWay ) {
			target.getDistanceStack().discardHazards();
		}
		
		return true;
	}
	
	/**
	 * Perform card to target verifications.
	 * 
	 * @param targetPlayer
	 * @return true if card can be played on the target, else false.
	 */
	private boolean cardAndTargetAreCompatible( Player targetPlayer ) {
		if ( targetPlayer == this.origin ) {
			return this.cardToPlay instanceof RemedyCard || this.cardToPlay instanceof DistanceCard	|| this.cardToPlay instanceof SafetyCard;
		} else if ( targetPlayer != this.origin ) {
			return this.cardToPlay instanceof HazardCard;
		}

		return false;
	}

	/**
	 * Perform card to stack verification.
	 * 
	 * -- > Distance : 
	 *   -- > Initial GoRoll is not played : 
	 *     -- > IMPOSSIBLE.
	 *   -- > Initial GoRoll is played : 
	 *     -- > Attacked and not protected : 
	 *       -- > IMPOSSIBLE. 
	 *     -- > Not attacked:
	 *       -- > Slowed and > 50 
	 *         -- > IMPOSSIBLE.
	 * 
	 * -- > Hazard : 
	 *   -- > Protected 
	 *     -- > IMPOSSIBLE. 
	 *   -- > Already attacked/slowed. 
	 *     -- > IMPOSSIBLE.
	 * 
	 * -- > Remedy : 
	 *   -- > Speed 
	 *   -- > GoStop 
	 *   -- > Else
	 * 
	 * -- > Safety :
	 * 
	 * @return false is Move is not allowed.
	 * @throws IllegalMoveException
	 */
	public boolean cardAndPlayerStackAreCompatible( Player targetPlayer ) throws IllegalMoveException {
		if ( this.cardToPlay instanceof DistanceCard ) {
			this.performDistanceCardVerification( targetPlayer );
		} else if ( this.cardToPlay instanceof HazardCard ) {
			this.performHazardCardVerification( targetPlayer );
		} else if ( this.cardToPlay instanceof RemedyCard ) {
			this.performRemedyCardVerification( targetPlayer );
		} else if ( this.cardToPlay instanceof SafetyCard ) {
			return true;
		}

		return true;
	}

	/**
	 * Perform the distance verifications.
	 * 
	 * @param targetPlayer
	 * 
	 * @return true if no error has been raised.
	 * @throws IllegalMoveException
	 */
	public boolean performDistanceCardVerification( Player targetPlayer )
			throws IllegalMoveException {
		if ( !targetPlayer.hasStarted() ) {
			throw new InitialGoRollNotPlayedException();
		} else {
			if ( targetPlayer.isAttacked() ) {
				throw new PlayerIsAttackedException( targetPlayer.getBattleStack().peek() );
			} else if ( targetPlayer.isSlowed() && ( (DistanceCard) this.cardToPlay ).getRange() > 50 ) {
				throw new PlayerIsSlowedException();
			} else if ( targetPlayer.getDistanceStack().maxNumberOfDistance200IsReached() ) {
				throw new MaxNumberOfDistance200IsReached();
			}
		}

		return true;
	}

	/**
	 * Perform the hazard verifications.
	 * 
	 * @param targetPlayer
	 * 
	 * @return true if no error has been raised.
	 * @throws IllegalMoveException
	 */
	public boolean performHazardCardVerification( Player targetPlayer )
			throws IllegalMoveException {
		if ( targetPlayer.hasStarted() ) {
			if ( targetPlayer.isProtectedFrom( (HazardCard) this.cardToPlay ) ) {
				throw new PlayerIsProtectedException( );
			} else if ( this.cardToPlay.getFamily() == CardFamily.Speed && targetPlayer.isSlowed() ) {
				throw new PlayerIsSlowedException();
			} else if ( this.cardToPlay.getFamily() != CardFamily.Speed && targetPlayer.isAttacked() ) {
				throw new PlayerIsAttackedException(this.cardToPlay);
			}
		} else {
			throw new IllegalMoveException( "Your opponent has not started to play yet." );
		}
		

		return true;
	}

	/**
	 * Perform the remedy verifications.
	 * 
	 * @param targetPlayer
	 * 
	 * @return true if no error has been raised.
	 * @throws IllegalMoveException
	 */
	public boolean performRemedyCardVerification( Player targetPlayer ) throws IllegalMoveException {
		if ( cardToPlay.getFamily() == CardFamily.Speed ) {
			if ( !targetPlayer.isSlowed() ) {
				throw new PlayerIsNotSlowedException( "You are not slowed." );
			}
		} else {
			if ( ! targetPlayer.hasStarted() && cardToPlay.getType() == CardType.GoRoll ) {
				return true;
			} else {
				if ( !targetPlayer.isAttacked() ) {
					throw new PlayerIsNotAttackedException("You are not under attack." );
				} else {
					if ( cardToPlay.getFamily() != targetPlayer.getBattleStack().peek().getFamily() ) {
						throw new UnsuitableRemedyException("Not the good kind of remedy." );
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean targetIsCompatible( Player targetPlayer ) throws IllegalMoveException {
		return cardAndTargetAreCompatible( targetPlayer ) && cardAndPlayerStackAreCompatible( targetPlayer );
	}
	
	public Boolean isAnAttack() {
		return this.cardToPlay instanceof HazardCard;
	}

}
