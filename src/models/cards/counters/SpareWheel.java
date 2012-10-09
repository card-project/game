package models.cards.counters;

public class SpareWheel extends CounterCard {
	private static Integer INSTANCE_COUNTER = 0;
	private static final Integer MAX_INSTANCES = 4;
	private static final SpareWheel OPPOSITE_CLASS = null;

	public SpareWheel() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}
