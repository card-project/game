package models.stacks;

import models.cards.Card;


public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7428996656985484083L;
	private boolean firstGoRollIsPlaced;
	
	// ------------ METHODS ------------ //
	
	public Card get() {
		return super.cards.getFirst();
	}
	
	// ------------ GETTERS ------------ //
	
	public boolean isFirstGoRollIsPlaced() {
		return firstGoRollIsPlaced;
	}
	
	// ------------ SETTERS ------------ //
	
	public void setFirstGoRollIsPlaced( boolean firstGoRollIsPlaced ) {
		this.firstGoRollIsPlaced = firstGoRollIsPlaced;
	}
}
