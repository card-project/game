package models.cards.hazards;

import models.cards.Card;
import models.cards.OppositeCardType;

public abstract class HazardCard extends Card {
	
	// ------------ ATTRIBUTES ------------ //
	
	private static final Integer MAX_INSTANCES = 10;
	
	private final OppositeCardType OPPOSITE_CARD_TYPE = null; 
	
	// ------------ METHODS ------------ //
	
	// ------------ GETTERS ------------ //
	
	public OppositeCardType getOppositeCardType() {
		return OPPOSITE_CARD_TYPE;
	}
}
