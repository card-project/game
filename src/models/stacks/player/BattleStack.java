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
		return ! super.cards.isEmpty();
	}

	public void removeAll() {
		this.cards.clear();
	}
	
	// ------------ GETTERS ------------ //
	
	public boolean initialGoRollIsPlayed() {
		return initialGoRollIsPlayed;
	}

}
