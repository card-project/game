package models.cards.milestones;

import java.util.LinkedList;

public class Milestone25 extends MilestoneCard {
	private static final Integer MAX_INSTANCES = 6;

	private Milestone25() {

	}

	public static LinkedList<Milestone25> getInstances() {
		LinkedList<Milestone25> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES; i++) {
			instances.add( new Milestone25() );
		}

		return instances;
	}
}