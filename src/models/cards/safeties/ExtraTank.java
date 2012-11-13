package models.cards.safeties;

import models.cards.OppositeCardType;


public class ExtraTank extends SafetyCard {
	
	// ------------ ATTRIBUTES ------------ //
	
	private final static OppositeCardType[] OPPOSITE_CARD_TYPES= {OppositeCardType.Gas};
	
	// ------------ CONSTRUCTORS ------------ //
	
	private ExtraTank() {
	}

	private static class CisternHolder {
		private static final ExtraTank INSTANCE = new ExtraTank();
	}
	
	// ------------ METHODS ------------ //

	public static ExtraTank getInstance() {
		return CisternHolder.INSTANCE;
	}
	
	public String toString() {
		return "Extra Tank";
	}
}
