package models.cards.milestones;

import java.util.LinkedList;

public class Milestone200 extends MilestoneCard {
	private static final Integer MAX_INSTANCES = 3;

	private Milestone200() {

	}

	public static LinkedList<Milestone200> getInstances() {
		LinkedList<Milestone200> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES; i++) {
			instances.add( new Milestone200() );
		}

		return instances;
	}
}