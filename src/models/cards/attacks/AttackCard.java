package models.cards.attacks;

import models.cards.Card;
import models.cards.counters.CounterCard;

public abstract class AttackCard extends Card {
	private static final Integer MAX_INSTANCES = 10;

	private CounterCard opposite;
}
