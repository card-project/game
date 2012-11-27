package models.stacks.player;



public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	private boolean initialGoRollIsPlayed = false;
	
	// ------------ METHODS ------------ //

	public boolean isAttacked() {
		return ! isEmpty();
	}

	public void removeAll() {
		this.cards.clear();
	}
	
	// ------------ GETTERS ------------ //
	
	public boolean initialGoRollIsPlayed() {
		return initialGoRollIsPlayed;
	}
	
	// ------------ SETTERS ------------ //
	
	public void setInitialGoRollIsPlayed( boolean initialGoRollIsPlayed ) {
		this.initialGoRollIsPlayed = initialGoRollIsPlayed;
	}

}
