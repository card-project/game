package models.cards.milestones;


public class Milestone75 extends MilestoneCard {
	public static final Integer MAX_INSTANCES = 6;
	private static Integer INSTANCE_COUNTER = 0;

	public Milestone75() {
		if (++INSTANCE_COUNTER > MAX_INSTANCES) {
			throw new IllegalStateException( "Too many instances" );
		}
	}
}