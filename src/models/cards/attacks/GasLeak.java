package models.cards.attacks;

import java.util.LinkedList;

import models.cards.counters.Refueling;

public class GasLeak extends AttackCard {
	private static final Integer MAX_INSTANCES = 2;
	private static final Refueling OPPOSITE_CLASS = null;
	
	private GasLeak() {
		
	}
	
	public static LinkedList<GasLeak> getInstances() {
		LinkedList<GasLeak> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new GasLeak() );
		}
		
		return instances;
	}
}
