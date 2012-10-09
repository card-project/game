package models.cards.attacks;

import java.util.LinkedList;

import models.cards.counters.EndSpeedLimit;

public class SpeedLimit extends AttackCard {
	private static final Integer MAX_INSTANCES = 2;
	private static final EndSpeedLimit OPPOSITE_CLASS = null;

	public SpeedLimit() {

	}
	
	public static LinkedList<SpeedLimit> getInstances() {
		LinkedList<SpeedLimit> instances = new LinkedList<>();
		for (int i = 0; i < MAX_INSTANCES ; i++) {
			instances.add( new SpeedLimit() );
		}
		
		return instances;
	}
}
