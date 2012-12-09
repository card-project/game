package models.players.strategies;

import java.util.Iterator;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.moves.BasicMove;
import models.players.AIPlayer;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

/**
 * @version 0.1
 * 
 * AI player strategy.
 * 
 * 
 * 
 * @author Adrien Saunier
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

		GameStack stackToDraw = null;

		if ( DiscardStack.getInstance().peek() instanceof DistanceCard ) {
			stackToDraw = DiscardStack.getInstance();
		} else {
			stackToDraw = DeckStack.getInstance();
		}

		return stackToDraw;
	}

	/**
	 * Priorities :
	 * 1 - GoRoll if player has no started yet.
	 * 2 - Maximum available distance.
	 * 
	 * @see models.players.strategies.Strategy#chooseCardToPlay()
	 */
	@Override
	public Card chooseCardToPlay() {

		Card chosenCard = null;
		boolean initialGoRollIsPlayed = this.player.hasStarted();

		for ( Card handCard : this.player.getHandStack().getCards() ) {
			if ( ! initialGoRollIsPlayed && handCard.getType() == CardType.GoRoll ) {
					chosenCard = handCard;
			} else {
				if ( handCard instanceof DistanceCard ) {
					DistanceCard currentDistanceCard = (DistanceCard) handCard;

					if ( chosenCard == null ) {
						chosenCard = currentDistanceCard;
					} else if ( currentDistanceCard.getRange() > ( ( DistanceCard ) chosenCard ).getRange() ) {
						chosenCard = currentDistanceCard;
					}
				}
			}
		}

		return chosenCard;
	}

	/**
	 * @see models.players.strategies.Strategy#chooseCardToDiscard()
	 */
	@Override
	public Card chooseCardToDiscard() {

		boolean cardIsChosen = false;
		Card cardToDiscard = null;
		BasicMove bm = new BasicMove( this.player );

		Iterator<Card> handCardsIterator = this.player.getHandStack().getCards().iterator();

		while ( handCardsIterator.hasNext() && ! cardIsChosen ) {
			Card card = handCardsIterator.next();

			if ( card instanceof DistanceCard ) {
				DistanceCard cardUnderTest = (DistanceCard) card;

				bm.setCardToPlay( cardUnderTest );
				try {

					// We check if the card is playable
					if ( bm.setTarget( this.player ) ) {
						if ( cardUnderTest.getRange() < ( (DistanceCard) cardToDiscard )
								.getRange() )
							cardToDiscard = cardUnderTest;
					}
				} catch ( IllegalMoveException e ) {

					// If a distance card can't be played, we can discard it.
					cardToDiscard = cardUnderTest;
					cardIsChosen = true;
				}
			}
		}

		return cardToDiscard;
	}
}
