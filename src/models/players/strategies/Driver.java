package models.players.strategies;

import java.util.Iterator;
import java.util.LinkedList;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.RemedyCard;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.moves.BasicMove;
import models.players.AIPlayer;
import models.stacks.DeckStack;
import models.stacks.DiscardStack;
import models.stacks.GameStack;

public class Driver implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	private AIPlayer player;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Driver(AIPlayer player) {
		this.player = player;
	}
	
	// ------------ METHODS ------------ //
	
	@Override
	public GameStack chooseStackToDraw() {
		
		GameStack stackToDraw = null;
		Card cardDisCard = DiscardStack.getInstance().getFirst();
		
		if(cardDisCard instanceof DistanceCard)
			stackToDraw = DiscardStack.getInstance();
		else
			stackToDraw = DeckStack.getInstance();

		return stackToDraw;
	}

	@Override
	public Card chooseCardToPlay() {
		
		Card chosenCard = null;
		boolean initialGoRoll = this.player.getBattleStack().initialGoRollIsPlayed();
		
		BasicMove move = new BasicMove(this.player);
		LinkedList<Card> list = this.player.getHandStack().getCards();
		
		for (Card card : list)
		{	
			if(initialGoRoll == false)
			{
				if(card instanceof RemedyCard)
				{
					RemedyCard cardChecked = (RemedyCard) card;
					
					if(initialGoRoll == false && cardChecked.getType() == CardType.GoRoll)
						chosenCard = cardChecked;
					
					move.setCardToPlay(chosenCard);
					
					try {
						move.setTarget(this.player);
					} catch (IllegalMoveException e) {
						// TODO Check the kind of IllegalMoveException
						e.printStackTrace();
					} catch (IllegalAccessError e) {
						e.printStackTrace();
					}
				}
			}
			else
			{
				if(card instanceof DistanceCard)
				{
					DistanceCard cardChecked = (DistanceCard) card;
					
					try {
						move.setCardToPlay(cardChecked);
						move.setTarget(this.player);
						
						if(move.performDistanceCardVerification())
							if(cardChecked.getRange() > ((DistanceCard)chosenCard).getRange())
								chosenCard = cardChecked;
						
					} catch (IllegalMoveException e) { 
						e.printStackTrace();
					} catch (IllegalAccessError e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		
		return chosenCard;
	}

	@Override
	public Card chooseCardToDiscard() {
		
		boolean cardChosen = false;
		
		Card cardToDiscard = null;
		BasicMove bm = new BasicMove(this.player);
		
		Iterator<Card> handCardsIterator = this.player.getHandStack().getCards().iterator();
		
		while(handCardsIterator.hasNext() && !cardChosen)
		{
			Card card = handCardsIterator.next();
			
			if(card instanceof DistanceCard)
			{
				DistanceCard cardUnderTest = (DistanceCard) card;
				
				bm.setCardToPlay(cardUnderTest);
				try {
					
					// We check if the card is possible to play
					if(bm.setTarget(player))
					{
						if(cardUnderTest.getRange() < ((DistanceCard)cardToDiscard).getRange())
							cardToDiscard = cardUnderTest;
					}
				} catch (IllegalMoveException e) {
					
					// If a distance card can't be played, we can discard it. 
					cardToDiscard = cardUnderTest;
					cardChosen = true;
				}
			}
		}
		
		return cardToDiscard;
	}

}
