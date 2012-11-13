package models.cards.hazards;

import models.cards.remedies.GoRoll;

public class Stop extends HazardCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final GoRoll OPPOSITE_CLASS = null;

	public Stop() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	public String toString() {
		return "Stop";
	}
}
