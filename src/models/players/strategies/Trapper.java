package models.players.strategies;

import models.cards.Card;
import models.cards.RemedyCard;
import models.players.AIPlayer;

/**
 * The Trapper strategy is pretty similar to the {@link Protector} strategy.
 * The only difference is that it will play his safeties only when someone is attacking him.
 * 
 * @author Adrien SAUNIER
 */
public class Trapper extends Protector {

	public Trapper(AIPlayer player) {
		super(player);
	}

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
		
		return chosenCard;
	}
}
