package models.players.strategies;

import models.cards.Card;
import models.players.AIPlayer;

/**
 * @version 0.1
 * 
 * {@link AIPlayer} strategy.
 * 
 * Keep his safety for a tricky move (coup fourré).
 * 
 * @author Adrien SAUNIER
 * @author Simon RENOULT
 */
public class Trapper extends Protector {

	public Trapper( AIPlayer player ) {
		super( player );
	}

	@Override
	public Card chooseCardToPlay() {
		Card chosenCard = null;
		
		if ( this.player.isAttacked() ) {
			if ( ( chosenCard = this.player.getHandStack().getCorrespondentSafety( this.player.getBattleStack() ) ) == null ) {
				chosenCard = this.player.getHandStack().getCorrespondentRemedy( this.player.getBattleStack() );
			}
		} else if ( this.player.isSlowed() ) {
			if ( ( chosenCard = this.player.getHandStack().getSlowSafety() ) == null ) {
				chosenCard = this.player.getHandStack().getSlowRemedy();
			}
		} 
		
		return chosenCard;
	}
}
