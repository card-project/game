package models.stacks.player;

import models.cards.Card;


/**
 * @author Simon RENOULT
 * @version 1.0
 */
public class HandStack extends PlayerStack {
	
	// ------------ ATTRIBUTES ------------ //
	
	public static final int MAX_IN_PLAY_CARD = 5; 
	public static final int MAX_CARD_NB = 4; 
	public static final int MIN_CARD_NB = 1;
	
	// ------------ METHODS ------------ //

	public void remove( Card c ) {
		this.cards.remove( c );
	}
}
