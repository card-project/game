package models.stacks;

import models.cards.HazardCard;


public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428996656985484083L;
	private boolean initialGoRollIsPlayed = false;
	
	// ------------ METHODS ------------ //

	public boolean isAttacked() {
		return ( this.getFirst() == null ) ? false : this.getFirst() instanceof HazardCard;
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
