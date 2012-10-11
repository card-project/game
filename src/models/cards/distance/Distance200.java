package models.cards.distance;


public class Distance200 extends DistanceCard {
	public static final Integer MAX_INSTANCES = 3;
	private static Integer INSTANCE_COUNTER = 0;

	public Distance200() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}