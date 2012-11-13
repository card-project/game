package models.cards.hazards;

import models.cards.OppositeCardType;

public class Stop extends HazardCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	
	private final OppositeCardType OPPOSITE_CARD_TYPE = OppositeCardType.GoStop;

	// ------------ CONSTRUCTORS ------------ //
	
	public Stop() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	// ------------ METHODS ------------ //

	public String toString() {
		return "Stop";
	}
	
	// ------------ GETTERS ------------ //
	
	public OppositeCardType getOppositeCardType() {
		return OPPOSITE_CARD_TYPE;
	}
}
