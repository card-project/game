package models.stacks;

import models.cards.Card;
import models.cards.OppositeCardType;
import models.cards.hazards.HazardCard;
import models.cards.safeties.SafetyCard;


public class SafetyStack extends PlayerStack {
	
	// ------------ ATTRIBUTES ------------ //
		
	// ------------ METHODS ------------ //
	
	public boolean isProtectedFrom( HazardCard hc ) {
		for ( Card c : super.cards ) {
			SafetyCard sc = ( SafetyCard ) c;
			for ( OppositeCardType oct : sc.getOppositeCardTypes() ) {
				if ( hc.getOppositeCardType() == oct ) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
