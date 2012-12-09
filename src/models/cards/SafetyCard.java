package models.cards;

import models.exceptions.IllegalCardTypeException;
import models.players.Player;

/**
 * @author Simon RENOULT
 * @version 1.0
 */
public class SafetyCard extends Card {

	// ------------ CONSTRUCTORS ------------ //

	protected SafetyCard( CardFamily initialFamily, CardType cardType ) {
		super( initialFamily, cardType );
	}
	
	// ------------ METHODS ------------ //
	
	public boolean isPlayableOn( Player p ) {
		return true;
	}
	
	public boolean playOn( Player p ) {
		
		// Move the safety to the right stack and remove it from the player's hand
		try {
			p.getHandStack().shiftTo( p.getSafetyStack(), this );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// As the card is a safety, increase the traveled distance by 100
		p.getDistanceStack().increaseBy100();
		
		// Remove the hazard if the safety's family corresponds
		if ( p.isAttacked() ) {
			for( CardFamily cf : this.getFamilies() ) {
				if ( cf == p.getBattleStack().peek().getFamily() ) {
					p.getBattleStack().discardHazards();
				}
			}
		}
		
		if ( p.isSlowed() && this.getType() == CardType.RightOfWay ) {
			p.getDistanceStack().discardHazards();
		}
		
		return true;
	}
}
