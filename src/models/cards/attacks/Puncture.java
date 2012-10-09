package models.cards.attacks;

import models.cards.counters.SpareWheel;

public class Puncture extends AttackCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 2;
	private static final SpareWheel OPPOSITE_CLASS = null;

	public Puncture() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
