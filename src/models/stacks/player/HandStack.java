package models.stacks.player;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;


/**
 * @author Simon RENOULT
 * @version 1.0
 */
public class HandStack extends PlayerStack {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final int MAX_IN_PLAY_CARD = 5; 
	public static final int MAX_CARD_NB = 4; 
	public static final int MIN_CARD_NB = 1;
	
	// ------------ METHODS ------------ //
	
	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		super.push( item );
	}
	
	public boolean hasRemedyFor( CardFamily f ) {
		for ( Card c : this.cards ) {
			if ( c instanceof RemedyCard ) {
				if ( ( (RemedyCard) c ).getFamily() == f ) {
					return true;
				}
			}
		}

		return false;
	}
	
	public boolean containsSafety() {
		for ( Card c : super.cards ) {
			if ( c instanceof SafetyCard ) {
				return true;
			}
		}
		
		return false;
	}

	public boolean containsHazard() {
		for ( Card c : super.cards ) {
			if ( c instanceof HazardCard ) {
				return true;
			}
		}
		
		return false;
	}

	public boolean containsRemedy() {
		for ( Card c : super.cards ) {
			if ( c instanceof RemedyCard ) {
				return true;
			}
		}
		
		return false;
	}

	public boolean containsDistance() {
		for ( Card c : super.cards ) {
			if ( c instanceof DistanceCard ) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsSlowDistanceCard() {
		for ( Card c : super.cards ) {
			if ( ( ( DistanceCard ) c ).getRange() <= 50 ) {
				return true;
			}
		}

		return false;
	}
}
