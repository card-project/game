package models.cards;

import models.stacks.player.BattleStack;

/**
 * Create a remedy card object. Allow a player to defend himself from 
 * another player attack once this one is on the {@link BattleStack}.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class RemedyCard extends Card {

	// ------------ ATTRIBUTES ------------ //
	
	public static final int MAX_GO_ROLL = 5;
	public static final int MAX_END_OF_LIMIT = 4;
	public static final int MAX_GASOLINE = 4;
	public static final int MAX_REPAIRS = 4;
	public static final int MAX_SPARE_TIRE = 4;
	
	// ------------ CONSTRUCTORS ------------ //

	protected RemedyCard( CardFamily initialFamily, CardType cardType ) {
		super( initialFamily, cardType );
	}
	
}
