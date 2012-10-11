package models.cards.hazards;

import models.cards.remedies.SpareTire;

public class FlatTire extends HazardCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final SpareTire OPPOSITE_CLASS = null;

	public FlatTire() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
