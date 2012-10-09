package models.cards.milestones;

import java.util.LinkedList;

public class Milestone100 extends MilestoneCard {
	private static final Integer MAX_INSTANCES = 9;
	
	private Milestone100() {
		
	}
	
	public static LinkedList<Milestone100> getInstances() {
		LinkedList<Milestone100> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new Milestone100() );
		}
		
		return instances;
	}
}
