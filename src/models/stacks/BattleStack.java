package models.stacks;

import models.cards.hazards.HazardCard;


public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428996656985484083L;
	private boolean initialGoRollIsPlayed;
	
	// ------------ METHODS ------------ //

	public boolean isAttacked() {
		return this.getFirst() instanceof HazardCard;
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
