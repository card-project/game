package models.cards.remedies;

import models.cards.hazards.Stop;

public class GoRoll extends RemedyCard {
	public static final Integer MAX_INSTANCES = 5;
	private static final Stop OPPOSITE_CLASS = null;
	private static Integer INSTANCE_COUNTER = 0;

	public GoRoll() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
