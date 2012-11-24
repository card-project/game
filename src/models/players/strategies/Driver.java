package models.players.strategies;

import java.util.LinkedList;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.exceptions.IllegalMoveException;
import models.moves.BasicMove;
import models.players.AIPlayer;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card chooseCardToPlay() {
		
		Card chosenCard = null;
		boolean speedLimit = false;
		boolean initialGoRoll = this.player.getBattleStack().initialGoRollIsPlayed();
		
		BasicMove move = new BasicMove(this.player);
		
		LinkedList<DistanceCard> suitableCards = new LinkedList<DistanceCard>();
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
						move.performRemedyCardVerification();
					} catch (IllegalMoveException e) {
						
						// TODO Check the kind of IllegalMoveException
						chosenCard = null;
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
		//TODO
		return null;
	}

}
