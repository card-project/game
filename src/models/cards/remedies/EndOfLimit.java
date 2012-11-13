package models.cards.remedies;

import models.cards.hazards.SpeedLimit;

public class EndOfLimit extends RemedyCard {
	public static final Integer MAX_INSTANCES = 4;
	public static final SpeedLimit OPPOSITE_CLASS = null;
	private static Integer INSTANCE_COUNTER = 0;

	public EndOfLimit() {
		if ( ++INSTANCE_COUNTER > MAX_INSTANCES ) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
	
	public String toString() {
		return "End Of Limit";
	}
}
