package models.cards.attacks;

import java.util.LinkedList;

import models.cards.counters.Reparation;

public class Accident extends AttackCard {
	private static final Integer MAX_INSTANCES = 2;
	private static final Reparation OPPOSITE_CLASS = null;
	
	private Accident() {

	}
	
	public static LinkedList<Accident> getInstances() {
		LinkedList<Accident> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new Accident() );
		}
		
		return instances;
	}
}
