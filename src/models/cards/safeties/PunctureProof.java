package models.cards.safeties;

import models.cards.OppositeCardType;


public final class PunctureProof extends SafetyCard {
	
	// ------------ ATTRIBUTES ------------ //

	private final static OppositeCardType[] OPPOSITE_CARD_TYPES = {OppositeCardType.Tire};

	// ------------ CONSTRUCTORS ------------ //
	
	private PunctureProof() {
	}

	private static class PunctureProofHolder {
		private static final PunctureProof INSTANCE = new PunctureProof();
	}

	// ------------ METHODS ------------ //
	
	public static PunctureProof getInstance() {
		return PunctureProofHolder.INSTANCE;
	}

	public String toString() {
		return "Puncture Proof";
	}
}
