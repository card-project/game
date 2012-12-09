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
	
	public boolean play( Player p ) {
		try {
			p.getDistanceStack().push( this );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		return true;
	}
}
