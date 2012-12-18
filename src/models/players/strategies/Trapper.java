package models.players.strategies;

import models.cards.Card;
import models.cards.CardFamily;
import models.players.AIPlayer;

/**
 * {@link AIPlayer} strategy.
 * 
 * Keep his safety for a tricky move (coup fourré).
 * 
 * @author Adrien SAUNIER
 * @author Simon RENOULT
 * @version 0.1
 */
public class Trapper extends Protector {

	// ------------ CONSTRUCTORS ------------ //
	
	public Trapper( AIPlayer player ) {
		super( player );
	}
	
	// ------------ METHODS ------------ //

	@Override
	public Card chooseCardToPlay() {
		Card chosenCard = null;
		
		if ( this.player.isAttacked() ) {
			CardFamily attackingFamily = player.getBattleStack().peek().getFamily();
			chosenCard = this.player.getHandStack().getRemedyOf( attackingFamily );
		} else if ( this.player.isSlowed() ) {
			chosenCard = this.player.getHandStack().getRemedyOf( CardFamily.Speed );
		} 
		
		return chosenCard;
	}
}
