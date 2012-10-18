package models.cards;

public abstract class Card {
	protected static Integer MAX_INSTANCES = 66;
	private int a= 0;
	// FIXME BEGIN
	
	// Remove if disagree : delete "leaf" classes. 
	// 1 - Instantiation is made through CardFactory and CardType.
	// 2 - Instances limitation is delegated to CardFactory.
	// 3 - Keep Hazard, Remedy, Distance and Safety types 
	// (mostly to know the opposite type between Hazard/Remedy/Safety 
	// and have a value for Distance card).
	
	// FIXME END
	
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
