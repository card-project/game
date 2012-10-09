package models.cards.milestones;

import java.util.LinkedList;

public class Milestone50 extends MilestoneCard {
	private static final Integer MAX_INSTANCES = 6;
	
	private Milestone50() {
		
	}
	
	public static LinkedList<Milestone50> getInstances() {
		LinkedList<Milestone50> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new Milestone50() );
		}
		
		return instances;
	}
}