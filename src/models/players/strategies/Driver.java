package models.players.strategies;

import models.cards.Card;
import models.cards.DistanceCard;
import models.players.AIPlayer;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

/**
 * @version 0.1
 * 
 * AI player strategy.
 * 
 * Draw a distance card as soon as possible.
 * Play the maximum distance card available.
 * Discard the minimum distance.
 * 
 * @author Adrien SAUNIER
 * @author Simon RENOULT
 */
public class Driver implements Strategy {

	// ------------ ATTRIBUTES ------------ //

	private AIPlayer player;

	// ------------ CONSTRUCTORS ------------ //

	/**
	 * @param player
	 */
	public Driver( AIPlayer player ) {
		this.player = player;
	}

	// ------------ METHODS ------------ //

	/**
	 * Draw a {@link DistanceCard} as soon as possible.
	 * 
	 * @see models.players.strategies.Strategy#chooseStackToDraw()
	 * @return the {@link GameStack} to draw on.
	 */
	@Override
	public GameStack chooseStackToDraw() {
		return ( DiscardStack.getInstance().peek() instanceof DistanceCard )
			? DiscardStack.getInstance()
			: DeckStack.getInstance();
	}

	/**
	 * Priorities :
	 * 1 - GoRoll if player has no started yet.
	 * 3 - Maximum available distance.
	 * 
	 * @see models.players.strategies.Strategy#chooseCardToPlay()
	 */
	@Override
	public Card chooseCardToPlay() {
		return ( ! this.player.hasStarted() ) 
			? this.player.getHandStack().chooseGoRoll()
			: this.player.getHandStack().chooseMaxDistance();
	}
	
	/**
	 * Discard the minimum distance card.
	 * 
	 * @see models.players.strategies.Strategy#chooseCardToDiscard()
	 */
	@Override
	public Card chooseCardToDiscard() {
		return this.player.getHandStack().chooseMinDistance();
	}
	
	// ------------ GETTERS ------------ //

	public AIPlayer getPlayer() {
		return player;
	}
}
