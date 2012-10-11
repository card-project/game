package models.cards;

public abstract class Card {
	protected static Integer MAX_INSTANCES = 66;

	public String toString() {
		return this.getClass().getSimpleName();
	}
}
