package models.players.strategies;

import java.util.ArrayList;
import java.util.Iterator;

import models.cards.Card;
import models.cards.HazardCard;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.moves.BasicMove;
import models.players.AIPlayer;
import models.players.Player;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

public class Roadhog implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	private AIPlayer player;
	private ArrayList<Player> opponents;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Roadhog(AIPlayer player, ArrayList<Player> opponents) {
		this.player = player;
		this.opponents = opponents;
	}
	 	
	// ------------ METHODS ------------ //
	
	/**
	 * Always draw on the DiscardStack if it's a {@link DiscardStack}.
	 */
	@Override
	public GameStack chooseStackToDraw() {
		
		GameStack stackToDraw = null;
		Card cardDisCard = DiscardStack.getInstance().peek();
		
		if(cardDisCard instanceof HazardCard)
			stackToDraw = DiscardStack.getInstance();
		else
			stackToDraw = DeckStack.getInstance();

		return stackToDraw;
	}

	@Override
	public Card chooseCardToPlay() {
		Card choosenCard = null;
		BasicMove bm = new BasicMove(this.player);
		ArrayList<Player> opponentsPerPriority = new ArrayList<Player>();
		
		for (Player p : this.opponents) {
			for(Card c : this.player.getHandStack().getCards()) {
				
				bm.setCardToPlay(c);
				try {
					if(bm.setTarget(player)) {
						// TODO Choisir la carte
					}
				} catch (IllegalMoveException e) {
					e.printStackTrace();
				}
			}
		}
		
		return choosenCard;
	}

	@Override
	public Card chooseCardToDiscard() {
		
		// TODO Improve this strategy.
			// Choose a card in particular if there is 2 or more copies
			// Choose a card in term of priority (player <=> road already done <=> safeties)

		boolean cardChosen = false;
		Card cardToDiscard = null;
		
		Iterator<Card> handCardsIterator = this.player.getHandStack().getCards().iterator();
		while(handCardsIterator.hasNext() && !cardChosen) {
			
			Card card = handCardsIterator.next();
			
			if(card instanceof HazardCard) {
				HazardCard cardUnderTest = (HazardCard) card;
				
				cardToDiscard = cardUnderTest;
				cardChosen = true;
			}
		}
		
		return cardToDiscard;
	}

}
