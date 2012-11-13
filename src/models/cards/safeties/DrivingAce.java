package models.cards.safeties;

import models.cards.OppositeCardType;


public class DrivingAce extends SafetyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	private final static OppositeCardType[] OPPOSITE_CARD_TYPES = {OppositeCardType.StateOfCar};
	
	// ------------ CONSTRUCTORS ------------ //
	
	private DrivingAce() {
	}

	private static class AceDriverHolder {
		private static final DrivingAce INSTANCE = new DrivingAce();
	}

	public static DrivingAce getInstance() {
		return AceDriverHolder.INSTANCE;
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Driving Ace";
	}
}
