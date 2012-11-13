package models.cards.remedies;

import models.cards.OppositeCardType;

public class GoRoll extends RemedyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final Integer MAX_INSTANCES = 5;
	private static Integer INSTANCE_COUNTER = 0;
	public static final OppositeCardType OPPOSITE_CLASS = OppositeCardType.GoStop;

	// ------------ CONSTRUCTORS ------------ //
	
	public GoRoll() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Go/Roll";
	}
}
