package models.cards.remedies;

import models.cards.OppositeCardType;

public class Gasoline extends RemedyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final Integer MAX_INSTANCES = 4;
	private static Integer INSTANCE_COUNTER = 0;
	public static final OppositeCardType OPPOSITE_CLASS = OppositeCardType.Gas;

	// ------------ CONSTRUCTORS ------------ //
	
	public Gasoline() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Gasoline";
	}
}
