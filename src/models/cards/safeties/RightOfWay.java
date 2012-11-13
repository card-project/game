package models.cards.safeties;

import models.cards.OppositeCardType;


public class RightOfWay extends SafetyCard {

	// ------------ ATTRIBUTES ------------ //

	private final static OppositeCardType[] OPPOSITE_CARD_TYPES = {OppositeCardType.GoStop, OppositeCardType.Speed};

	// ------------ CONSTRUCTORS ------------ //
	
	private RightOfWay() {
	}

	private static class EmergencyVehicleHolder {
		private static final RightOfWay INSTANCE = new RightOfWay();
	}

	public static RightOfWay getInstance() {
		return EmergencyVehicleHolder.INSTANCE;
	}
	
	// ------------ METHODS ------------ //
	
	public String toString() {
		return "Right Of Way";
	}
}
