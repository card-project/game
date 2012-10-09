package models.cards.counters;

import models.cards.attacks.Accident;

public class Reparation extends CounterCard {
	public static final Integer MAX_INSTANCES = 4;
	private static Integer INSTANCE_COUNTER = 0;
	private static final Accident OPPOSITE_CLASS = null;

	public Reparation() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
