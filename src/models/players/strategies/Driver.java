package models.players.strategies;

import java.util.LinkedList;

import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.players.AIPlayer;
import models.stacks.game.GameStack;

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
		DistanceCard chosenCard = null;
		boolean speedLimit = false;
		
		if(this.player.getBattleStack().isAttacked())
		{
			LinkedList<Card> listInjuringCards = this.player.getBattleStack().getCards();
			
			for (Card card : listInjuringCards) {
				
				HazardCard injuringCard = (HazardCard) card;
				
				if(injuringCard instanceof SpeedLimit)
				{
					speedLimit = true;
				}
				else
				{
					
				}	
			}
		}
		
		LinkedList<Card> list = this.player.getHandStack().getCards();
		for (Card card : list) {
			if(card instanceof DistanceCard)
			{
				DistanceCard cardChecked = (DistanceCard) card;
				
				if(!speedLimit ||(speedLimit && cardChecked.getRange() <= 50))
				{					
					if(cardChecked.getRange() == 200)
					{
						if(!this.player.getDistanceStack().maxNumberOfDistance200IsReached())
						{
							if(chosenCard.getRange() < cardChecked.getRange())
								chosenCard = cardChecked;
						}
					}
					else
					{
						if(chosenCard.getRange() < cardChecked.getRange())
							chosenCard = cardChecked;
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
