package models.stacks.player;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.DistanceCard;
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
	
	public Card chooseMaxDistance() {
		Card chosenCard = null;
		
		for( Card handCard : this.getCards() ) {
			if ( handCard instanceof DistanceCard ) {
				DistanceCard currentDistanceCard = (DistanceCard) handCard;
				
				if ( chosenCard == null ) {
					chosenCard = currentDistanceCard;
				} else if ( currentDistanceCard.getRange() > ( ( DistanceCard ) chosenCard ).getRange() ) {
					chosenCard = currentDistanceCard;
				}
			}
		}
		
		return chosenCard;
	}
	
	public Card chooseMinDistance() {
		Card chosenCard = null;
		
		for( Card handCard : this.cards ) {
			if ( handCard instanceof DistanceCard ) {
				DistanceCard currentDistanceCard = (DistanceCard) handCard;
				
				if ( chosenCard == null ) {
					chosenCard = currentDistanceCard;
				} else if ( currentDistanceCard.getRange() < ( ( DistanceCard ) chosenCard ).getRange() ) {
					chosenCard = currentDistanceCard;
				}
			}
		}
		
		return chosenCard;
	}
	
	public Card chooseGoRoll() {
		
		for( Card handCard : this.cards ) {
			if( handCard instanceof RemedyCard ) {
				if( handCard.getFamily() == CardFamily.GoStop ) {
					return handCard;
				}
			}
		}
		
		return null;
	}
	
	public SafetyCard getSafetyOf ( CardFamily searchedFamily ) {
		for( Card c : this.cards ) {
			if ( c instanceof SafetyCard ) {
				for ( CardFamily cf : c.getFamilies() ) {
					if ( cf == searchedFamily ) {
						return (SafetyCard) c;
					}
				}
			}
		}
		
		return null;
	}
	
	public Card getRemedyOf ( CardFamily searchedFamily ) {
		for( Card c : this.cards ) {
			if ( c instanceof SafetyCard ) {
				for ( CardFamily cf : c.getFamilies() ) {
					if ( cf == searchedFamily ) {
						return c;
					}
				}
			}
		}
		
		return null;
	}
	
	public Card getSlowRemedy() {
		return getRemedyOf( CardFamily.Speed );
	}
	
	public Card getCorrespondentRemedy( BattleStack b ) {
		return getRemedyOf( b.peek().getFamily() );
	}
	
	public Card getSlowSafety() {
		return getSafetyOf( CardFamily.Speed );
	}
	
	public Card getCorrespondentSafety( BattleStack b ) {
		return getSafetyOf( b.peek().getFamily() );
	}

	public boolean hasSafetyFor( CardFamily family ) {
		for ( Card c : this.cards ) {
			if ( c instanceof SafetyCard ) {
				for ( CardFamily cf : c.getFamilies() ) {
					if ( cf == family ) {
						return true;
					}
				}
			}
		}

		return false;
}
}
