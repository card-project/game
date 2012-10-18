package models.cards.distances;



public class Distance100 extends DistanceCard {
	public static final Integer MAX_INSTANCES = 9;
	private static Integer INSTANCE_COUNTER = 0;

	public Distance100() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
