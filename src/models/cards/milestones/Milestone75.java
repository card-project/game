package models.cards.milestones;

import java.util.LinkedList;

public class Milestone75 extends MilestoneCard {
	private static final Integer MAX_INSTANCES = 6;

	private Milestone75() {

	}

	public static LinkedList<Milestone75> getInstances() {
		LinkedList<Milestone75> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES; i++) {
			instances.add( new Milestone75() );
		}

		return instances;
	}
}