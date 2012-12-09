package models.cards;

import models.exceptions.IllegalCardTypeException;
import models.players.Player;
import models.stacks.player.BattleStack;
import models.stacks.player.PlayerStack;

/**
 * Create a remedy card object. Allow a player to defend himself from 
 * another player attack once this one is on the {@link BattleStack}.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class RemedyCard extends Card {

	// ------------ ATTRIBUTES ------------ //
	
	public static final int MAX_GO_ROLL = 5;
	public static final int MAX_END_OF_LIMIT = 4;
	public static final int MAX_GASOLINE = 4;
	public static final int MAX_REPAIRS = 4;
	public static final int MAX_SPARE_TIRE = 4;
	
	// ------------ CONSTRUCTORS ------------ //

	protected RemedyCard( CardFamily initialFamily, CardType cardType ) {
		super( initialFamily, cardType );
	}
	
	// ------------ METHODS ------------ //
	
	public boolean isPlayableOn ( Player p ) {
		if ( ! p.hasStarted() && this.getFamily() == CardFamily.GoStop ) {
			return true;
		} else if ( ! p.isSlowed() && this.getFamily() == CardFamily.Speed ) {
			return false;
		} else if ( ! p.isAttacked() && this.getFamily() != CardFamily.Speed ) {
			return false;
		} else if ( p.isAttacked() ) {
			if ( this.getFamily() != p.getBattleStack().peek().getFamily() ) {
				return false;
			}
		}
		
		return true;
	}

	public boolean play( Player p ) {
		
		if ( this.getFamily() == CardFamily.Speed ) {
			p.getDistanceStack().discardHazards();
			p.discard( this );
		} else {
			if ( ! p.hasStarted() && this.getFamily() == CardFamily.GoStop ) {
				try {
					p.getHandStack().shiftTo( p.getDistanceStack(), this );
				} catch ( IllegalCardTypeException e ) {
					e.printStackTrace();
				}
			} else {
				p.getBattleStack().discardHazards();
				p.discard( this );
			}
		}
		
		return false;
	}
	
}
