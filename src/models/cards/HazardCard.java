package models.cards;

/**
 * Create a hazard card object. Allow a player to attack another player.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class HazardCard extends Card {

	// ------------ ATTRIBUTES ------------ //

	public static final int MAX_STOP = 2;
	public static final int MAX_SPEED_LIMIT = 2;
	public static final int MAX_OUT_OF_GAS = 2;
	public static final int MAX_FLAT_TIRE = 2;
	public static final int MAX_ACCIDENT = 2;

	// ------------ CONSTRUCTORS ------------ //

	public HazardCard( CardFamily initialFamily, CardType cardType ) {
		super( initialFamily, cardType );
	}
}
