package models.cards.remedies;

import models.cards.hazards.OutOfGas;

public class Gasoline extends RemedyCard {
	public static final Integer MAX_INSTANCES = 4;
	public static final OutOfGas OPPOSITE_CLASS = null;
	private static Integer INSTANCE_COUNTER = 0;

	public Gasoline() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	public String toString() {
		return "Gasoline";
	}
}
