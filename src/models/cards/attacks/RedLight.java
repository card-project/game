package models.cards.attacks;

import java.util.LinkedList;

import models.cards.counters.GreenLight;

public class RedLight extends AttackCard {
	private static final Integer MAX_INSTANCES = 2;
	private static final GreenLight OPPOSITE_CLASS = null;
	
	public RedLight() {

	}
	
	public static LinkedList<RedLight> getInstances() {
		LinkedList<RedLight> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new RedLight() );
		}
		
		return instances;
	}
}
