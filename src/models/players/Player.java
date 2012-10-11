package models.players;

import models.Game;
import models.cards.Card;
import models.cards.distance.DistanceCard;
import models.exceptions.DiscardChoiceOutOfBoundsException;
import models.stacks.BattleStack;
import models.stacks.DiscardStack;
import models.stacks.DistanceStack;
import models.stacks.GameStack;
import models.stacks.HandStack;
import models.stacks.SafetyStack;
import models.stacks.Stack;

public abstract class Player {
	protected String alias;
	protected Integer score;
	protected Integer kilometers;
	protected BattleStack battleStack;
	protected DistanceStack distanceStack;
	protected SafetyStack safetyStack;
	protected HandStack handStack;

	public Player() {
		this.handStack = new HandStack();
		this.battleStack = new BattleStack();
		this.safetyStack = new SafetyStack();
		this.distanceStack = new DistanceStack();
	}

	public DistanceCard playCard() {
		// TODO
		return null;
	}

	public Card draw( GameStack drawStackChosen ) {
		return Stack.transferTopCard( drawStackChosen, this.handStack );
	}

	public void discard( Card cardToDiscard, DiscardStack discardStack ) {
		Stack.transferCard( cardToDiscard, this.handStack, discardStack );
	}

	public void discard( Integer cardToDiscardIndex, DiscardStack discardStack )
			throws DiscardChoiceOutOfBoundsException {
		if ( cardToDiscardIndex < Game.MIN_HAND_CARDS + 1
				|| cardToDiscardIndex > Game.MAX_HAND_CARDS + 1 ) {
			throw new DiscardChoiceOutOfBoundsException(
					"Choice must be included between 1 and 5." );
		} else {
			discard( this.handStack.get( cardToDiscardIndex ), discardStack );
		}
	}

	public void setAlias( String string ) {
		this.alias = string;
	}

	public Integer getKilometers() {
		return this.kilometers;
	}

	public String getAlias() {
		return this.alias;
	}

	public HandStack getHandStack() {
		return this.handStack;
	}

	public String toString() {
		return this.alias
				+ " - "
				+ ( this.kilometers == null ? "0" : this.kilometers )
				+ "km "
				+ '\n'
				+ "HAND : "
				+ this.handStack
				+ ( !safetyStack.isEmpty() ? '\n' + "SPECIAL : " + safetyStack
						: "" )
				+ ( !battleStack.isEmpty() ? '\n' + "BATTLE: " + battleStack
						: "" )
		// TODO 2 Milestone200 examplaries
		;
	}
}
