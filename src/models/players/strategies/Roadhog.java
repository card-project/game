package models.players.strategies;

import java.util.ArrayList;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.players.AIPlayer;
import models.players.Player;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

/**
 * @version 0.1
 * 
 * {@link AIPlayer} strategy.
 * 
 * Draw a hazard as soon as possible.
 * Play a hazard as soon as possible.
 * Discard a duplicate hazard.
 * 
 * @author Adrien Saunier
 * @author Simon RENOULT
 */
public class Roadhog implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	protected AIPlayer player;
	private ArrayList<Player> opponents;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Roadhog(AIPlayer player, ArrayList<Player> opponents) {
		this.player = player;
		this.opponents = opponents;
	}
	 	
	// ------------ METHODS ------------ //
	
	/**
	 * Always draw on the DiscardStack if it's a {@link HazardCard}.
	 */
	@Override
	public GameStack chooseStackToDraw() {
		if ( ! DiscardStack.getInstance().isEmpty() ) {
			if ( DiscardStack.getInstance().peek() instanceof HazardCard ) {
				return DiscardStack.getInstance();
			}
		}
		
		return null;
	}

	@Override
	public Card chooseCardToPlay() {
		return (Card) lookFor( true );
	}

	public Player chooseTargetToAttack() {
		return (Player) lookFor( false );
	}

	private Object lookFor( boolean lookForACard ) {
		Object choice = null;
		for ( Card handCard : this.player.getHandStack().getCards() ) {
			if ( handCard instanceof HazardCard) {
				for ( Player p : opponents ) {
					if ( p.hasStarted() && ! p.isProtectedFrom( ( HazardCard ) handCard ) ) {
						if ( handCard.getFamily() == CardFamily.Speed && ! p.isSlowed() ) {
							choice = lookForACard ? handCard : p;
						} else if ( ! p.isAttacked() ) {
							choice = lookForACard ? handCard : p;
						}
					}
				}
			}
		}
		return choice;
	}
	
	/**
	 * Priorities :
	 * 1 : Protected types
	 * 2 : Duplicate
	 */
	@Override
	public Card chooseCardToDiscard() {
		Card cardToDiscard = null;

		if ( opponents.size() == 1 ) {
			for ( Player p : opponents ) {
				for ( Card safety : p.getSafetyStack().getCards() ) {
					for ( Card handCard : this.player.getHandStack().getCards() ) {
						if ( ! ( handCard instanceof DistanceCard ) ) {
							if ( safety.getFamily() == handCard.getFamily() ) {
								cardToDiscard = handCard;
							}
						}
					}
				}
			}	
		}
		
		if ( cardToDiscard == null ) {
			Card tmp = null;
			for ( Card handCard : this.player.getHandStack().getCards() ) {
				if ( handCard instanceof HazardCard ) {
					if ( tmp == null ) {
						tmp = handCard;
					} else {
						if ( tmp.getFamily() == handCard.getFamily() ) {
							cardToDiscard = tmp;
						}
					}
				}
			}
		}

		return cardToDiscard;
	}

}
