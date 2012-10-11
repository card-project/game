package models.cards.hazards;

import models.cards.remedies.EndOfLimit;

public class SpeedLimit extends HazardCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final EndOfLimit OPPOSITE_CLASS = null;

	public SpeedLimit() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}
