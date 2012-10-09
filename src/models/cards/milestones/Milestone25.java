package models.cards.milestones;


public class Milestone25 extends MilestoneCard {
	public static final Integer MAX_INSTANCES = 6;
	private static Integer INSTANCE_COUNTER = 0;

	public Milestone25() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}

}