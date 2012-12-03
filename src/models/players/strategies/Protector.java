package models.players.strategies;

import java.util.Iterator;
import java.util.LinkedList;

import models.cards.Card;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.players.AIPlayer;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;

public class Protector implements Strategy {

	// ------------ ATTRIBUTES ------------ //
	
	protected AIPlayer player = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Protector(AIPlayer player) {
		this.player = player;
	}
	
	// ------------ METHODS ------------ //

	/**
	 * @return {@link GameStack}
	 */
	@Override
	public GameStack chooseStackToDraw() {

		GameStack stackToDraw = null;
		Card cardDisCard = DiscardStack.getInstance().peek();
		boolean keepAnalysing = true;
		
		if(cardDisCard instanceof RemedyCard)
		{
			// If the player is attacked and there is the right remedy on the discard stack
			if(this.player.isAttacked() && cardDisCard.getType() == this.player.getBattleStack().peek().getType())
				stackToDraw = DiscardStack.getInstance();
			else
			{
				// Otherwise, we check if we already have an instance of the type of card	
				Iterator<Card> iteratorHandStack = this.player.getHandStack().getCards().iterator();
				
				while (iteratorHandStack.hasNext() && keepAnalysing) {
					Card testedCard = iteratorHandStack.next();
					
					if(testedCard.getType() == cardDisCard.getType())
						keepAnalysing = false;
				}
				
				if(keepAnalysing)
					stackToDraw = DiscardStack.getInstance();
				else
					stackToDraw = DeckStack.getInstance();
			}

		}
		else
			stackToDraw = DeckStack.getInstance();

		return stackToDraw;
	}

	/**
	 * Choose a card and return it.
	 * 		If the player is attacked and he has as an appropriate remedy, we play that one.
	 * 		If not, we play a {@link SafetyCard}
	 */
	@Override
	public Card chooseCardToPlay() {

		Card chosenCard = null;
		
		if(this.player.isAttacked())
		{
			for (Card card : this.player.getHandStack().getCards()) {
				if(this.player.getBattleStack().peek().getType() == card.getType())
					chosenCard = (RemedyCard) card;
			}
		}
		else
		{
			for (Card card : this.player.getHandStack().getCards()) {
				if(card instanceof SafetyCard)
					chosenCard = (SafetyCard) card;
			}
		}
		
		return chosenCard;
	}

	@Override
	public Card chooseCardToDiscard() {
		
		boolean cardChosen = false;
		Card cardToDiscard = null;
		
		Iterator<Card> handCardsIterator = this.player.getHandStack().getCards().iterator();
		LinkedList<Card> safeties =  this.player.getSafetyStack().getCards();
		
		while(handCardsIterator.hasNext() && !cardChosen)
		{
			Card card = handCardsIterator.next();
			
			if((card instanceof RemedyCard) && (safeties.size() > 0))
			{
				RemedyCard cardUnderTest = (RemedyCard) card;
				
				for (Card safety : safeties) {
					
					// If we find a safety card that we can't play (already protected thanks to the Safeties)
					// we discard it.
					if(cardUnderTest.getFamily() == safety.getFamily()) {
						cardToDiscard = cardUnderTest;
						cardChosen = true;
					}
				}
				
			}
		}
		
		return cardToDiscard;
	}

}
