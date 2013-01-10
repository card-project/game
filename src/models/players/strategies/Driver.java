package models.players.strategies;

import models.Game;
import models.cards.Card;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.cards.SafetyCard;
import models.players.AIPlayer;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

/**
 * 
 * AI player strategy.
 * 
 * Draw a distance card as soon as possible. Play the maximum distance card
 * available. Discard the minimum distance.
 * 
 * @author Adrien SAUNIER
 * @author Simon RENOULT
 * @version 1.1
 * @see DriverTest
 */
public class Driver extends Behavior {

	// ------------ ATTRIBUTES ------------ //

	private int goal;

	// ------------ CONSTRUCTORS ------------ //

	/**
	 * @param player
	 *            Owner of the driver strategy
	 * @param distanceGoal
	 *            {@link Game} distance goal.
	 */
	public Driver ( AIPlayer player, int distanceGoal ) {
		super( player );
		this.goal = distanceGoal;
	}

	// ------------ METHODS ------------ //

	/**
	 * Draw a {@link DistanceCard} as soon as possible.
	 * 
	 * @see models.game.players.strategies.Strategy#chooseStackToDraw()
	 * @return the {@link GameStack} to draw on.
	 */
	@Override
	public GameStack chooseStackToDraw() {
		GameStack chosenStack = null;
		Card discardedCard = DiscardStack.getInstance().peek();

		if ( discardedCard != null ) {
			if ( discardedCard instanceof DistanceCard ) {
				if ( ( ( DistanceCard ) discardedCard ).isPlayableOn( owner, goal ) ) {
					chosenStack = DiscardStack.getInstance();
				}
			} else if ( !owner.hasStarted() && discardedCard.isGoRoll() ) {
				chosenStack = DiscardStack.getInstance();
			}
		}

		return chosenStack;
	}

	/**
	 * Priorities : 1 - GoRoll if player has no started yet. 2 - Safety 3 -
	 * EndOfLimit if more than 500 distance exists. 4 - Maximum available
	 * distance.
	 * 
	 * @see models.game.players.strategies.Strategy#chooseCardToPlay()
	 */
	@Override
	public Card chooseCardToPlay() {
		Card chosenCard = null;

		if ( !owner.hasStarted() ) {
			chosenCard = owner.getHand().getGoRoll();
		} else if ( !owner.isAttacked() && owner.getHand().containsDistance() ) {
			for ( Card handCard : owner.getHand() ) {
				if ( chosenCard == null ) {
					if ( handCard instanceof SafetyCard ) {
						chosenCard = handCard;
					} else if ( handCard instanceof DistanceCard ) {
						if ( owner.isSlowed() && owner.getHand().hasRemedyFor( CardFamily.Speed )
								&& owner.getHand().getMaxDistance( false ).getRange() >= 75 ) {
							owner.getHand().getRemedyOf( CardFamily.Speed );
						} else if ( owner.canFinish( goal ) ) {
							chosenCard = owner.getHand().getFinishableDistance( owner.getTraveledDistance(), goal );
						} else {
							chosenCard = owner.getHand().getMaxDistance( owner.isSlowed() );
						}
					}
				}
			}
		}

		return chosenCard;
	}

	/**
	 * Discard the minimum distance card.
	 * 
	 * @see models.game.players.strategies.Strategy#chooseCardToDiscard()
	 */
	@Override
	public Card chooseCardToDiscard() {
		return owner.getHand().getMinDistance();
	}

}
