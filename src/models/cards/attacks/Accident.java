package models.cards.attacks;

import models.cards.counters.Reparation;

public class Accident extends AttackCard {
	private static Integer INSTANCE_COUNTER = 0;
	private static final Integer MAX_INSTANCES = 2;
	private static final Reparation OPPOSITE_CLASS = null;

	public Accident() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
