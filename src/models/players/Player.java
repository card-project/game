package models.players;

import models.cards.Card;
import models.cards.distances.DistanceCard;
import models.cards.hazards.HazardCard;
import models.cards.hazards.SpeedLimit;
import models.cards.remedies.RemedyCard;
import models.cards.safeties.SafetyCard;
import models.moves.BasicMove;
import models.moves.Move;
import models.moves.TrickyMove;
import models.stacks.BattleStack;
import models.stacks.DiscardStack;
import models.stacks.DistanceStack;
import models.stacks.GameStack;
import models.stacks.HandStack;
import models.stacks.SafetyStack;
import models.stacks.Stack;

/**
 * @author Simon RENOULT
 * @version 1.0
 */
public abstract class Player {

	// ------------ ATTRIBUTES ------------ //
	
	protected String alias;
	protected Integer score;
	protected Integer kilometers;
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
	
	public Card pickCardToPlay( int cardIndex ) {
		return this.handStack.get( cardIndex );
	}
	
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
				+ ( this.kilometers == null ? "0" : this.kilometers ) + "km " + '\n'
				+ "HAND : "	+ this.handStack
				+ ( !safetyStack.isEmpty() ? '\n' + "SPECIAL : " + safetyStack : "" )
				+ ( !battleStack.isEmpty() ? '\n' + "BATTLE: " + battleStack : "" );
	}
	
	// ------------ GETTERS ------------ //

	public Integer getKilometers() {
		return this.kilometers;
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
