package models.cards.counters;

import models.cards.attacks.GasLeak;

public class Refueling extends CounterCard {
	public static final Integer MAX_INSTANCES = 4;
	private static final GasLeak OPPOSITE_CLASS = null;
	private static Integer INSTANCE_COUNTER = 0;

	public Refueling() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
