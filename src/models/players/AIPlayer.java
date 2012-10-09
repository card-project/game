package models.players;

import models.players.strategies.Strategy;

public class AIPlayer extends Player {
	private Integer level;
	private Strategy strategy;
	
	public void setLevel( Integer level ) {
		this.level = level;
	}
	
	@Override
	public String toString ( ) {
		return this.alias + " (" + this.level + ").";
	}

}
