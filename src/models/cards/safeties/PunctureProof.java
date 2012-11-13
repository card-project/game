package models.cards.safeties;


public final class PunctureProof extends SafetyCard {
	private PunctureProof() {
	}

	private static class PunctureProofHolder {
		private static final PunctureProof INSTANCE = new PunctureProof();
	}

	public static PunctureProof getInstance() {
		return PunctureProofHolder.INSTANCE;
	}

	public String toString() {
		return "Puncture Proof";
	}
}
