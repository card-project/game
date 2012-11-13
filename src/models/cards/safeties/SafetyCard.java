package models.cards.safeties;

import models.cards.Card;
import models.cards.OppositeCardType;


public abstract class SafetyCard extends Card {
	
	// ------------ ATTRIBUTES ------------ //
	
	private final static Integer MAX_INSTANCES = 4;
	private final static OppositeCardType[] OPPOSITE_CARD_TYPES = {};
	
	// ------------ CONSTRUCTORS ------------ //
	
	// ------------ METHODS ------------ //
	
	public OppositeCardType[] getOppositeCardTypes() {
		return OPPOSITE_CARD_TYPES;
	}	
	
}
