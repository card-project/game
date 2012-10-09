package models.cards.specials;


public final class PunctureProof {
	private PunctureProof() { }
	
	private static class PunctureProofHolder {
		private static final PunctureProof INSTANCE= new PunctureProof();
	}
	
	public static PunctureProof getInstance() {
		return PunctureProofHolder.INSTANCE;
	}
}
