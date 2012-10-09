package models.cards.specials;


public class AceDriver extends SpecialCard {
	private static final AceDriver SINGLETON = new AceDriver();

	private AceDriver() {
		if ( SINGLETON != null ) {
			throw new IllegalStateException( "Already instantiated" );
		}
	}
	
	public static AceDriver getInstance() {
		return SINGLETON;
	}
}
