package models.cards.milestones;


public class Milestone200 extends MilestoneCard {
	public static final Integer MAX_INSTANCES = 3;
	private static Integer INSTANCE_COUNTER = 0;

	public Milestone200() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}