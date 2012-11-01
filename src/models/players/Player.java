package models.players;

import models.cards.Card;
import models.moves.Move;
import models.stacks.BattleStack;
import models.stacks.DiscardStack;
import models.stacks.DistanceStack;
import models.stacks.GameStack;
import models.stacks.HandStack;
import models.stacks.SafetyStack;

/**
 * @author Simon RENOULT
 * @version 1.1
 */
public abstract class Player {

	// ------------ ATTRIBUTES ------------ //
	
	protected String alias;
	protected BattleStack battleStack;
	protected DistanceStack distanceStack;
	protected SafetyStack safetyStack;
	protected HandStack handStack;

	// ------------ CONSTRUCTORS ------------ //
	
	public Player() {
		this.handStack = new HandStack();
		this.battleStack = new BattleStack();
		this.safetyStack = new SafetyStack();
		this.distanceStack = new DistanceStack();
	}

	// ------------ METHODS ------------ //
		
	public Card draw( GameStack drawStackChosen ) {
		return drawStackChosen.shiftTopCard( this.handStack );
	}

	public void play( Move m ) {

	}
	
	public void discard( Card cardToDiscard, DiscardStack discardStack ) {
		this.handStack.shift( cardToDiscard, discardStack );
	}
	
	public void discard( Integer cardToDiscardIndex, DiscardStack discardStack ) {
		discard( this.handStack.get( cardToDiscardIndex ), discardStack );
	}


	public String toString() {
		return this.alias +  " - "
				+ this.distanceStack.getDistance() + "km " + '\n'
				+ "HAND : "	+ this.handStack
				+ ( !safetyStack.isEmpty() ? '\n' + "SPECIAL : " + safetyStack : "" )
				+ ( !battleStack.isEmpty() ? '\n' + "BATTLE: " + battleStack : "" );
	}
	
	// ------------ GETTERS ------------ //

	public DistanceStack getDistanceStack() {
		return distanceStack;
	}
	
	public SafetyStack getSafetyStack() {
		return safetyStack;
	}

	public void setBattleStack( BattleStack battleStack ) {
		this.battleStack = battleStack;
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public HandStack getHandStack() {
		return this.handStack;
	}

	public BattleStack getBattleStack() {
		return battleStack;
	}
	
	// ------------ SETTERS ------------ //
	
	public void setAlias( String string ) {
		this.alias = string;
	}
}
