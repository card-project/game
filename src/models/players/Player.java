package models.players;

import models.cards.Card;
import models.stacks.BattleStack;
import models.stacks.HandStack;
import models.stacks.MilestoneStack;
import models.stacks.SpecialStack;

public abstract class Player {
	protected String alias;
	protected Integer score;
	protected Integer kilometers;
	protected BattleStack battleStack;
	protected MilestoneStack milestoneStack;
	protected SpecialStack specialStack;
	protected HandStack handStack;

	public Player() {
		handStack = new HandStack();
		battleStack = new BattleStack();
		specialStack = new SpecialStack();
		milestoneStack = new MilestoneStack();
	}

	public Card playCard() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAlias( String string ) {
		alias = string;
	}

	public String getAlias() {
		return alias;
	}

	public void draw( Card c ) {
		this.handStack.add( c );
	}

	public HandStack getHandStack() {
		return this.handStack;
	}
}
