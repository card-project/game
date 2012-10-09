package models.cards.milestones;


public class Milestone100 extends MilestoneCard {
	public static final Integer MAX_INSTANCES = 9;
	private static Integer INSTANCE_COUNTER = 0;

	public Milestone100() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}
