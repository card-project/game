package models.cards.specials;


public final class PunctureProof {
	private static final PunctureProof SINGLETON = new PunctureProof();

	private PunctureProof() {
		if ( SINGLETON != null ) {
			throw new IllegalStateException( "Already instantiated" );
		}
	}
	
	public static PunctureProof getInstance() {
		return SINGLETON;
	}
}
