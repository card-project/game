package models.cards.attacks;

import models.cards.counters.EndSpeedLimit;

public class SpeedLimit extends AttackCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final EndSpeedLimit OPPOSITE_CLASS = null;

	public SpeedLimit() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}
