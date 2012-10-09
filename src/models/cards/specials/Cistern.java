package models.cards.specials;


public class Cistern extends SpecialCard {
	private static final Cistern SINGLETON = new Cistern();

	private Cistern() {
		if ( SINGLETON != null ) {
			throw new IllegalStateException( "Already instantiated" );
		}
	}
	
	public Cistern getInstance() {
		return SINGLETON;
	}
}
