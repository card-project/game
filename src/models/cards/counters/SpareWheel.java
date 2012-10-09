package models.cards.counters;

import java.util.LinkedList;

public class SpareWheel extends CounterCard {
	private static final Integer MAX_INSTANCES = 4;
	private static final SpareWheel OPPOSITE_CLASS = null;

	private SpareWheel() {
		
	}
	
	public static LinkedList<SpareWheel> getInstances() {
		LinkedList<SpareWheel> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new SpareWheel() );
		}
		
		return instances;
	}
}
