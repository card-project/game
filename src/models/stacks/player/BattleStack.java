package models.stacks.player;

import models.cards.HazardCard;


public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	private boolean initialGoRollIsPlayed = false;
	
	// ------------ METHODS ------------ //

	public boolean isAttacked() {
		return ( super.cards.getFirst() == null ) ? false : super.cards.getFirst() instanceof HazardCard;
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
