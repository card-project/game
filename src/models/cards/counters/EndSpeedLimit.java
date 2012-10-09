package models.cards.counters;

import models.cards.attacks.SpeedLimit;

public class EndSpeedLimit extends CounterCard {
	public static final Integer MAX_INSTANCES = 4;
	private static final SpeedLimit OPPOSITE_CLASS = null;
	private static Integer INSTANCE_COUNTER = 0;

	public EndSpeedLimit() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
