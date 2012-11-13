package models.cards.hazards;

import models.cards.remedies.Repairs;

public class Accident extends HazardCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final Repairs OPPOSITE_CLASS = null;

	public Accident() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	public String toString() {
		return "Accident";
	}
}
