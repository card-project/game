package models.cards.counters;

import java.util.LinkedList;

import models.cards.attacks.RedLight;

public class GreenLight extends CounterCard {
	private static final Integer MAX_INSTANCES = 5;
	private static final RedLight OPPOSITE_CLASS = null;

	private GreenLight() {
		// TODO Auto-generated constructor stub
	}
	
	public static LinkedList<GreenLight> getInstances() {
		LinkedList<GreenLight> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new GreenLight() );
		}
		
		return instances;
	}
}
