package models.cards;

import models.Game;
import models.exceptions.IllegalCardTypeException;
import models.players.Player;
import models.stacks.player.BattleStack;

/**
 * Create a remedy card object. Allow a player to defend himself from 
 * another player attack once this one is on the {@link BattleStack}.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class RemedyCard extends Card {

	// ------------ CONSTANTS ------------ //
	
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

	/**
	 * Whether the current {@link HazardCard} is playable on the chosen opponent.
	 * 
	 * @param p {@link Player} chosen as a target.
	 * @param distanceGoal {@link Game} distance goal.
	 * @return Whether the current {@link DistanceCard} is playable on the chosen target.
	 */
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
	
	/**
	 * Play the current {@link RemedyCard} on the chosen {@link Player} and take it
	 * off from his hand. Determine also whether this move implies the player to replay.
	 * 
	 * @param p {@link Player} who plays the {@link RemedyCard}.
	 * @return Whether the {@link Player} replay after this {@link Card} has been played. 
	 */
	public boolean playOn( Player p ) {
		if ( this.getFamily() == CardFamily.Speed ) {
			p.getDistanceStack().discardHazards();
			p.discard( this );
		} else {
			if ( ! p.hasStarted() ) {
				if ( this.getFamily() == CardFamily.GoStop ) {
					try {
						p.getHandStack().shiftTo( p.getBattleStack(), this );
					} catch ( IllegalCardTypeException e ) {
						e.printStackTrace();
					}
				}
			} else {
				p.getBattleStack().discardHazards();
				p.discard( this );
			}
		}
		
		return false;
	}
	
}
