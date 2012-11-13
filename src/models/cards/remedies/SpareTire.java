package models.cards.remedies;

import models.cards.OppositeCardType;


public class SpareTire extends RemedyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 4;
	public static final OppositeCardType OPPOSITE_CLASS = OppositeCardType.Tire;

	// ------------ CONSTRUCTORS ------------ //
	
	public SpareTire() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Spare Tire";
	}
}
