package models.moves;

import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.exceptions.moveExceptions.InitialGoRollNotPlayedException;
import models.exceptions.moveExceptions.PlayerIsAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotSlowedException;
import models.exceptions.moveExceptions.PlayerIsSlowedException;
import models.exceptions.moveExceptions.UnsuitableRemedyException;
import models.players.Player;

/**
 * Create an object manipulating a standard move.
 * 
 * It enables the use of a move (attack, defense, distance, safety)
 * in the standard playing stream.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class BasicMove extends Move {

	// ------------ ATTRIBUTES ------------ //

	// ------------ CONSTRUCTORS ------------ //

	public BasicMove( Player moveInitiator ) {
		super( moveInitiator );
	}

	// ------------ METHODS ------------ //

	@Override
	public void realize() {
		if ( cardToPlay instanceof DistanceCard ) {
			target.getDistanceStack().push( cardToPlay );
		} else if ( cardToPlay instanceof HazardCard ) {
			target.getBattleStack().push( cardToPlay );
		} else if ( cardToPlay instanceof RemedyCard ) {
			target.getBattleStack().removeAll();
			if ( cardToPlay.getFamily() == CardFamily.GoStop ) {
				target.getBattleStack().setInitialGoRollIsPlayed( true );
			}
		} else if ( cardToPlay instanceof SafetyCard ) {
			target.getSafetyStack().push( cardToPlay );
			target.getDistanceStack().increaseBy100();

			if ( target.isAttacked() ) {
				if ( cardToPlay.getType() == CardType.RightOfWay ) {
					for ( CardFamily cf : cardToPlay.getFamilies() ) {
						if ( cf == cardToPlay.getFamily() ) {
							target.getBattleStack().removeAll();
						}
					}
				} else {
					if ( cardToPlay.getFamily() == target
							.getBattleStackContent().getFamily() ) {
						target.getBattleStack().removeAll();
					}
				}
			}

		}

		origin.getHandStack().remove( cardToPlay );
	}

	/**
	 * Perform card to target verifications.
	 * 
	 * @param targetPlayer
	 * @return true if card can be played on the target, else false.
	 */
	private boolean cardAndTargetAreCompatible( Player targetPlayer ) {
		if ( targetPlayer.equals( this.origin ) ) {
			if ( this.cardToPlay instanceof RemedyCard
					|| this.cardToPlay instanceof DistanceCard
					|| this.cardToPlay instanceof SafetyCard ) {
				return true;
			}
		} else if ( !targetPlayer.equals( this.origin ) ) {
			if ( this.cardToPlay instanceof HazardCard ) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Perform card to stack verification.
	 * 
	 * -- > Distance : -- > Initial GoRoll is not played : -- > IMPOSSIBLE. -- >
	 * Initial GoRoll is played : -- > Attacked and not protected : -- >
	 * IMPOSSIBLE. -- > Not attacked: -- > Slowed and > 50 -- > IMPOSSIBLE.
	 * 
	 * -- > Hazard : -- > Protected -- > IMPOSSIBLE. -- > Already
	 * attacked/slowed. -- > IMPOSSIBLE.
	 * 
	 * -- > Remedy : -- > Speed -- > GoStop -- > Else
	 * 
	 * -- > Safety :
	 * 
	 * @return false is Move is not allowed.
	 * @throws IllegalMoveException
	 */
	public boolean cardAndPlayerStackAreCompatible( Player targetPlayer )
			throws IllegalMoveException {
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
		if ( !targetPlayer.getBattleStack().initialGoRollIsPlayed() ) {
			throw new InitialGoRollNotPlayedException(
					"Initial GoRoll card has not been played yet." );
		} else {
			if ( targetPlayer.getBattleStack().isAttacked()
					&& ( !targetPlayer.isProtectedFrom( targetPlayer
							.getBattleStackContent() ) ) ) {
				throw new PlayerIsAttackedException( "You are under attack : "
						+ targetPlayer.getBattleStackContent() );
			} else if ( targetPlayer.isSlowed()
					&& ( (DistanceCard) this.cardToPlay ).getRange() > 50 ) {
				throw new PlayerIsSlowedException( "Your speed is limited." );
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
		if ( targetPlayer.isProtectedFrom( (HazardCard) this.cardToPlay ) ) {
			throw new IllegalMoveException(
					"Your opponent is protected from this kind of attack." );
		} else if ( this.cardToPlay.getFamily() == CardFamily.Speed
				&& targetPlayer.isSlowed() ) {
			throw new PlayerIsSlowedException(
					"Your opponent is already slowed." );
		} else if ( this.cardToPlay.getFamily() != CardFamily.Speed
				&& targetPlayer.isAttacked() ) {
			throw new PlayerIsAttackedException(
					"Your opponent is already under attack." );
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
	public boolean performRemedyCardVerification( Player targetPlayer )
			throws IllegalMoveException {
		if ( cardToPlay.getFamily() == CardFamily.Speed ) {
			if ( !targetPlayer.isSlowed() ) {
				throw new PlayerIsNotSlowedException( "You are not slowed." );
			}
		} else {
			if ( cardToPlay.getFamily() != CardFamily.GoStop
					&& !targetPlayer.getBattleStack().initialGoRollIsPlayed() ) {
				if ( !targetPlayer.isAttacked() ) {
					throw new PlayerIsNotAttackedException(
							"You are not under attack." );
				} else {
					if ( cardToPlay.getFamily() != targetPlayer
							.getBattleStackContent().getFamily() ) {
						throw new UnsuitableRemedyException(
								"Not the good kind of remedy." );
					}
				}
			}
		}

		return true;
	}

	// ------------ GETTERS ------------ //

	public Boolean isAnAttack() {
		return this.cardToPlay instanceof HazardCard;
	}

	// ------------ SETTERS ------------ //

	@Override
	public boolean targetIsCompatible( Player targetPlayer )
			throws IllegalMoveException {
		return cardAndTargetAreCompatible( targetPlayer )
				&& cardAndPlayerStackAreCompatible( targetPlayer );
	}
}
