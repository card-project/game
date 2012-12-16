package models.stacks.player;

import models.cards.Card;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.stacks.game.DiscardStack;



public class BattleStack extends PlayerStack {
	
	// ------------ METHODS ------------ //
	
	private boolean initialGoRollIsPlayed = false;
	
	// ------------ METHODS ------------ //

	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		if ( item instanceof HazardCard ) {
			this.cards.push( item );
		} else if ( ( ! initialGoRollIsPlayed && item.isGoRoll() ) ) {
			initialGoRollIsPlayed = true;
			this.cards.push( item );
		} else {
			throw new IllegalCardTypeException();
		}
	}
	
	public void discardHazards() {
		for ( int i = 0; i < super.cards.size() ; i++ ) {
			if ( super.cards.get( i ) instanceof HazardCard ) {
				try {
					this.shiftTo( DiscardStack.getInstance(), super.cards.get( i ) );
				} catch ( IllegalCardTypeException e ) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void removeAll() {
		this.cards.clear();
	}
	
	// ------------ GETTERS ------------ //
	
	public boolean initialGoRollIsPlayed() {
		return initialGoRollIsPlayed;
	}
}
