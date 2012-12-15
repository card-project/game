package models.players.strategies;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.players.AIPlayer;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

/**
 * @version 0.1
 * 
 * AI player strategy.
 * 
 * Draw a safety/remedy as soon as possible.
 * Play a safety/remedy as soon as possible.
 * Discard hazards.
 * 
 * @author Adrien SAUNIER
 * @author Simon RENOULT
 */
public class Protector implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	protected AIPlayer player = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Protector(AIPlayer p) {
		this.player = p;
	}
	
	// ------------ METHODS ------------ //

	/**
	 * Priorities :
	 * 1 - GoRoll if not started
	 * 2 - Correspondent remedy/safety if attacked 
	 * 3 - New remedy 
	 * 
	 * @return The chosen {@link GameStack} to draw on.
	 */
	@Override
	public GameStack chooseStackToDraw() {
		GameStack chosenStack = null;

		if ( ! DiscardStack.getInstance().isEmpty() ) {
			Card discardedCard = DiscardStack.getInstance().peek();
			
			if ( discardedCard != null ) {
				if ( ! this.player.hasStarted() && discardedCard.isGoRoll() ) {
					chosenStack = DiscardStack.getInstance();
				} else if ( this.player.isAttacked() ) {
					if ( discardedCard.counteract( this.player.getBattleStack().peek().getFamily() ) ) {
						chosenStack = DiscardStack.getInstance();
					}
				} else if ( this.player.isSlowed() ) {
					if ( discardedCard.counteract( CardFamily.Speed ) ) {
						chosenStack = DiscardStack.getInstance();
					}
				} else if ( discardedCard instanceof RemedyCard ) {
					chosenStack = DiscardStack.getInstance();
				}
			}
		}
		
		return chosenStack;
	}

	/**
	 * Priorities :
	 * 1 : player is attacked, play the correspondent remedy/safety
	 * 2 : player is not attacked, play a safety
	 *
	 * @return The chosen {@link Card} to play.
	 */
	@Override
	public Card chooseCardToPlay() {
		Card chosenCard = null;
		
		if ( this.player.isAttacked() ) {
			CardFamily attackingFamily = player.getBattleStack().peek().getFamily();
			if ( ( chosenCard = this.player.getHandStack().getSafetyOf( attackingFamily ) ) == null ) {
				chosenCard = this.player.getHandStack().getRemedyOf( attackingFamily );
			}
		} else if ( this.player.isSlowed() ) {
			if ( ( chosenCard = this.player.getHandStack().getSafetyOf( CardFamily.Speed ) ) == null ) {
				chosenCard = this.player.getHandStack().getRemedyOf( CardFamily.Speed );
			}
		} else {
			for ( Card c : this.player.getHandStack().getCards() ) {
				if ( c instanceof SafetyCard ) {
					chosenCard = c;
				}
			}
		}
		
		return chosenCard;
	}

	/**
	 * Priorities :
	 * 1 : Remedy having the same family than a played safety.
	 * 2 : Duplicate remedy.
	 * 
	 * @return The chosen {@link Card} to discard.
	 */
	@Override
	public Card chooseCardToDiscard() {
		Card cardToDiscard = null;
		
		for ( Card handCard: this.player.getHandStack().getCards() ) {
			if ( handCard instanceof RemedyCard ) {
				for ( Card safety : this.player.getSafetyStack().getCards() ) {
					for ( CardFamily cf : safety.getFamilies() ) {
						if ( handCard.getFamily() == cf ) {
							cardToDiscard = handCard;
						}
					}
				}
			}
		}
		
		if ( cardToDiscard == null ) {
			Card tmp = null;
			for( Card handCard : this.player.getHandStack().getCards() ) {
				if ( handCard instanceof RemedyCard ) {
					if ( tmp == null ) {
						tmp = handCard;
					} else {
						if ( tmp.getFamily() == handCard.getFamily() ) {
							cardToDiscard = handCard;
						}
					}
				}
			}
		}
		
		return cardToDiscard;
	}

}
