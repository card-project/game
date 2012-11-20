package models.cards;

/**
 * @author Simon RENOULT
 * @version 1.0
 *
 */
public class RemedyCard extends Card {

	// ------------ ATTRIBUTES ------------ //
	
	public static final int MAX_GO_ROLL = 5;
	public static final int MAX_END_OF_LIMIT = 4;
	public static final int MAX_GASOLINE = 4;
	public static final int MAX_REPAIRS = 4;
	public static final int MAX_SPARE_TIRE = 4;
	
	// ------------ CONSTRUCTORS ------------ //

	public RemedyCard( CardFamily initialFamily, CardType cardType ) {
		super( initialFamily, cardType );
	}
	
	// ------------ METHODS ------------ //
	
}
