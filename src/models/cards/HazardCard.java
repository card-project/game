package models.cards;

import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.Player;
import models.stacks.player.PlayerStack;

/**
 * Create a hazard card object. Allow a player to attack another player.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class HazardCard extends Card {

	// ------------ ATTRIBUTES ------------ //

	public static final int MAX_STOP = 2;
	public static final int MAX_SPEED_LIMIT = 2;
	public static final int MAX_OUT_OF_GAS = 2;
	public static final int MAX_FLAT_TIRE = 2;
	public static final int MAX_ACCIDENT = 2;

	// ------------ CONSTRUCTORS ------------ //

	protected HazardCard( CardFamily initialFamily, CardType cardType ) {
		super( initialFamily, cardType );
	}
	
	// ------------ METHODS ------------ //
	
	public boolean isPlayableOn( Player opponent ) {
		if ( ! opponent.hasStarted() ) {
			return false;
		} else {
			if ( opponent.isProtectedFrom( this ) ) {
				return false;
			} else {
				if ( opponent.isSlowed() && this.getFamily() == CardFamily.Speed ) {
					return false;
				} else if ( opponent.isAttacked() && this.getFamily() != CardFamily.Speed ) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean playOn( Player p, Player opponent ) throws AvailableCoupFourreException {
		
		if ( opponent.getHandStack().hasSafetyFor( this.getFamily() ) ) {
			SafetyCard c = opponent.getHandStack().getSafetyOf( this.getFamily() );
			throw new AvailableCoupFourreException( c, opponent, this );
		} else {
			PlayerStack destination = ( this.getFamily() == CardFamily.Speed ) ? opponent.getDistanceStack() : opponent.getBattleStack();
			
			try {
				p.getHandStack().shiftTo( destination, this );
			} catch ( IllegalCardTypeException e ) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
}
