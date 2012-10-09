package models.cards.counters;

import java.util.LinkedList;

import models.cards.attacks.GasLeak;

public class Refueling extends CounterCard {
	private static final Integer MAX_INSTANCES = 4;
	private static final GasLeak OPPOSITE_CLASS = null;

	private Refueling() {
		
	}
	
	public static LinkedList<Refueling> getInstances() {
		LinkedList<Refueling> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new Refueling() );
		}
		
		return instances;
	}
}
