package models.cards.remedies;

public class SpareTire extends RemedyCard {
	private static Integer INSTANCE_COUNTER = 0;
	public static final Integer MAX_INSTANCES = 4;
	public static final SpareTire OPPOSITE_CLASS = null;

	public SpareTire() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}
