package models.cards.remedies;

import models.cards.Card;
import models.cards.hazards.HazardCard;

public abstract class RemedyCard extends Card {
	public final static Integer MAX_INSTANCES = 21;

	private HazardCard opposite;
}
