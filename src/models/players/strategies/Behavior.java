package models.players.strategies;

import models.players.AIPlayer;

public abstract class Behavior implements Strategy {

	// -------------- ATTRIBUTES -------------- //
	
	protected AIPlayer owner;

	// -------------- CONSTRUCTORS -------------- //
	
	public Behavior( AIPlayer owner ) {
		super();
		this.owner = owner;
	}
	
	// -------------- METHODS -------------- //
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName().toString();
	}
}
