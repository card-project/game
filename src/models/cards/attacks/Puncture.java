package models.cards.attacks;

import java.util.LinkedList;

import models.cards.counters.SpareWheel;


public class Puncture extends AttackCard {
	private static final Integer MAX_INSTANCES = 2;
	private static final SpareWheel OPPOSITE_CLASS = null;
	
	public Puncture() {

	}
	
	public static LinkedList<Puncture> getInstances() {
		LinkedList<Puncture> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new Puncture() );
		}
		
		return instances;
	}
	
}
