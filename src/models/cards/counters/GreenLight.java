package models.cards.counters;

import models.cards.attacks.RedLight;

public class GreenLight extends CounterCard {
	public static final Integer MAX_INSTANCES = 5;
	private static final RedLight OPPOSITE_CLASS = null;
	private static Integer INSTANCE_COUNTER = 0;

	public GreenLight() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
