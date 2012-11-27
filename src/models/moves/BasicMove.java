package models.moves;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.moveExceptions.PlayerIsAttackedException;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.exceptions.moveExceptions.InitialGoRollNotPlayedException;
import models.exceptions.moveExceptions.PlayerIsNotAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotSlowedException;
import models.exceptions.moveExceptions.PlayerIsSlowedException;
import models.exceptions.moveExceptions.UnsuitableRemedyException;
import models.players.Player;

/**
 * @author Simon RENOULT
 * @version 0.2
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

	public void realize() {
		if ( cardToPlay instanceof DistanceCard ) {
			target.getDistanceStack().push( cardToPlay );
		} else if ( cardToPlay instanceof HazardCard) {
			target.getBattleStack().push( cardToPlay );
		} else if ( cardToPlay instanceof RemedyCard ) {
			target.getBattleStack().removeAll();
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
					if ( cardToPlay.getFamily() == target.getBattleStackContent().getFamily() ) {
						target.getBattleStack().removeAll();
					}
				}
			}
			
		}
 		
		source.getHandStack().remove( cardToPlay );
	}
	
	private boolean cardAndTargetAreCompatible( Player targetPlayer ) {
		if ( targetPlayer.equals( this.source ) ) {
			if ( this.cardToPlay instanceof RemedyCard 
					|| this.cardToPlay instanceof DistanceCard 
					|| this.cardToPlay instanceof SafetyCard  ) {
				return true;
			}
		} else if ( ! targetPlayer.equals( this.source ) ){
			if ( this.cardToPlay instanceof HazardCard ) {
				return true;
			}
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
	public boolean cardAndPlayerStackAreCompatible() throws IllegalMoveException {
		if ( this.cardToPlay instanceof DistanceCard ) {
			this.performDistanceCardVerification();
		} else if ( this.cardToPlay instanceof HazardCard ) {
			this.performHazardCardVerification();
		} else if ( this.cardToPlay instanceof RemedyCard ) { 
			this.performRemedyCardVerification();
		}	else if ( this.cardToPlay instanceof SafetyCard ) {
			return true;
		} 
		
		return true;
	}

	public boolean performDistanceCardVerification() throws IllegalMoveException {
		if ( ! this.target.getBattleStack().initialGoRollIsPlayed() ) {
			throw new InitialGoRollNotPlayedException( "Initial GoRoll card has not been played yet." ); 
		} else {
			if ( ! this.target.isProtectedFrom( this.target.getBattleStackContent() )
					&& this.target.getBattleStack().isAttacked() ) {
				throw new PlayerIsAttackedException( "You are under attack : " + this.target.getBattleStackContent() );
			} else if ( this.target.isSlowed() && ( ( DistanceCard ) this.cardToPlay ).getRange() > 50 ) {
				throw new PlayerIsSlowedException( "Your speed is limited." );
			}
		}
		
		return true;
	}

	public boolean performHazardCardVerification() throws IllegalMoveException {
		if ( this.target.isProtectedFrom( ( HazardCard ) this.cardToPlay ) ) {
			throw new IllegalMoveException( "Your opponent is protected from this kind of attack." );
		} else if ( this.cardToPlay.getFamily() == CardFamily.Speed 
				&& this.target.isSlowed() ) {
			throw new PlayerIsSlowedException( "Your opponent is already slowed." );
		} else if ( this.cardToPlay.getFamily() != CardFamily.Speed 
				&& this.target.isAttacked() ) {
			throw new PlayerIsAttackedException( "Your opponent is already under attack." );
		}
		
		return true;
	}
	
	public boolean performRemedyCardVerification() throws IllegalMoveException {
		if ( cardToPlay.getFamily() == CardFamily.Speed ) {
			if ( ! target.isSlowed() ) {
				throw new PlayerIsNotSlowedException( "You are not slowed." );
			}
		} else {
			if ( ! target.isAttacked() ) {
				if ( cardToPlay.getFamily() != CardFamily.GoStop && ! target.getBattleStack().initialGoRollIsPlayed() ) {
					throw new PlayerIsNotAttackedException( "You are not under attack." );
				}
			} else {
				if ( cardToPlay.getFamily() != target.getBattleStackContent().getFamily() ) {
					throw new UnsuitableRemedyException( "Not the good kind of remedy." );
				}
			}
		}
		
		return true;
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
	public void setCardToPlay( Card c ) throws IllegalAccessError {
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
	public boolean setTarget( Player targetPlayer ) throws IllegalMoveException, IllegalAccessError {
		if ( this.cardToPlay == null ) {
			throw new IllegalAccessError( "No card selected." );
		} else {
			if ( this.cardAndTargetAreCompatible( targetPlayer ) ) {
				this.target = targetPlayer;
				return this.cardAndPlayerStackAreCompatible();
			} else {
				throw new IllegalMoveException( "Target must be an opponent." );
			}
		}
	}
}
