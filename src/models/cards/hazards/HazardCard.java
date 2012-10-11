package models.cards.hazards;

import models.cards.Card;
import models.cards.remedies.RemedyCard;

public abstract class HazardCard extends Card {
	private static final Integer MAX_INSTANCES = 10;

	private RemedyCard opposite;
}
