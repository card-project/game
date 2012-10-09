package models.cards.counters;

import java.util.LinkedList;

import models.cards.attacks.Accident;

public class Reparation extends CounterCard {
	private static final Integer MAX_INSTANCES = 4;
	private static final Accident OPPOSITE_CLASS = null;

	private Reparation() {
		
	}
	
	public static LinkedList<Reparation> getInstances() {
		LinkedList<Reparation> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new Reparation() );
		}
		
		return instances;
	}
}
