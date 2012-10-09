package models.cards.attacks;

import models.cards.counters.GreenLight;

public class RedLight extends AttackCard {
	private static Integer INSTANCE_COUNTER = 0;
	private static final Integer MAX_INSTANCES = 2;
	private static final GreenLight OPPOSITE_CLASS = null;

	public RedLight() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
