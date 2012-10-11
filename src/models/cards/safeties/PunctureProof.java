package models.cards.safeties;

public final class PunctureProof extends SafetiesCard {
	private PunctureProof() {
	}

	private static class PunctureProofHolder {
		private static final PunctureProof INSTANCE = new PunctureProof();
	}

	public static PunctureProof getInstance() {
		return PunctureProofHolder.INSTANCE;
	}
}
