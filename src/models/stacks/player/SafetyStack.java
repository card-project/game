package models.stacks.player;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.HazardCard;

public class SafetyStack extends PlayerStack {

	// ------------ ATTRIBUTES ------------ //

	// ------------ METHODS ------------ //

	public boolean performProtectionVerificationOn( HazardCard hc ) {

		for ( Card c : cards ) {
			for ( CardFamily cf : c.getFamilies() ) {
				if ( hc.getFamilies().get( 0 ) == cf ) {
					return true;
				}
			}
		}

		return false;
	}

}
