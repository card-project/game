package models.cards.remedies;

import models.cards.OppositeCardType;

public class EndOfLimit extends RemedyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final Integer MAX_INSTANCES = 4;
	private static Integer INSTANCE_COUNTER = 0;
	public static final OppositeCardType OPPOSITE_CLASS = OppositeCardType.Speed;

	// ------------ CONSTRUCTORS ------------ //
	
	public EndOfLimit() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "End Of Limit";
	}
}
