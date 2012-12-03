package models.stacks.player;

import models.cards.Card;
import models.cards.CardType;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.exceptions.IllegalCardTypeException;



public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	private boolean initialGoRollIsPlayed = false;
	
	// ------------ METHODS ------------ //

	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		if ( ! ( item instanceof RemedyCard ) && ! ( item instanceof HazardCard ) ) {
			throw new IllegalCardTypeException();
		} else {
			if ( ! initialGoRollIsPlayed && item.getType() == CardType.GoRoll ) {
				initialGoRollIsPlayed = true;
			}
			
			this.cards.push( item );
		}
	}
	
	public boolean isAttacked() {
		boolean containsHazard = false;
		
		for ( Card c : super.cards ) {
			if ( c instanceof HazardCard ) {
				containsHazard = true;
			}
		}
		
		return containsHazard;
	}
	
	public void removeHazards() {
		for ( int i = 0; i < super.cards.size() ; i++ ) {
			if ( super.cards.get( i ) instanceof HazardCard ) {
				super.cards.remove( i );
			}
		}
	}

	public void removeAll() {
		this.cards.clear();
	}
	
	// ------------ GETTERS ------------ //
	
	public boolean initialGoRollIsPlayed() {
		return initialGoRollIsPlayed;
	}

}
