package models.cards.attacks;

import models.cards.counters.Refueling;

public class GasLeak extends AttackCard {
	private static Integer INSTANCE_COUNTER = 0;
	private static final Integer MAX_INSTANCES = 2;
	private static final Refueling OPPOSITE_CLASS = null;

	public GasLeak() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
