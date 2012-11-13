package models.cards.remedies;

import models.cards.OppositeCardType;

public class Repairs extends RemedyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final Integer MAX_INSTANCES = 4;
	public static Integer INSTANCE_COUNTER = 0;
	private static final OppositeCardType OPPOSITE_CLASS = OppositeCardType.StateOfCar;

	// ------------ CONSTRUCTORS ------------ //
	
	public Repairs() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Repairs";
	}
}
