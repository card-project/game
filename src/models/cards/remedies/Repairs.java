package models.cards.remedies;

import models.cards.hazards.Accident;

public class Repairs extends RemedyCard {
	public static final Integer MAX_INSTANCES = 4;
	public static Integer INSTANCE_COUNTER = 0;
	private static final Accident OPPOSITE_CLASS = null;

	public Repairs() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
