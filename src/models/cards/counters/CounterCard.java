package models.cards.counters;

import models.cards.Card;
import models.cards.attacks.AttackCard;

public abstract class CounterCard extends Card {
	public final static Integer MAX_INSTANCES = 21;

	private AttackCard opposite;
}
