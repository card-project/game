package models.cards.distance;


public class Distance50 extends DistanceCard {
	public static final Integer MAX_INSTANCES = 6;
	private static Integer INSTANCE_COUNTER = 0;

	public Distance50() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}