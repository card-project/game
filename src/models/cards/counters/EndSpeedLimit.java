package models.cards.counters;

import java.util.LinkedList;

import models.cards.attacks.SpeedLimit;


public class EndSpeedLimit extends CounterCard {
	private static final Integer MAX_INSTANCES = 4;
	private static final SpeedLimit OPPOSITE_CLASS = null;

	private EndSpeedLimit() {
		
	}
	
	public static LinkedList<EndSpeedLimit> getInstances() {
		LinkedList<EndSpeedLimit> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new EndSpeedLimit() );
		}
		
		return instances;
	}
	
	
}
