package models.cards.hazards;

import models.cards.remedies.Gasoline;

public class OutOfGas extends HazardCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final Gasoline OPPOSITE_CLASS = null;

	public OutOfGas() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
