package models.cards.counters;

import models.cards.Card;
import models.cards.attacks.AttackCard;

public abstract class CounterCard extends Card {
	private final static Integer MAX_INSTANCES = 21;

	private AttackCard opposite;
}
