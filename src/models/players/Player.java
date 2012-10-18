package models.players;

import models.Game;
import models.cards.Card;
import models.cards.distances.DistanceCard;
import models.cards.hazards.HazardCard;
import models.cards.hazards.SpeedLimit;
import models.cards.remedies.RemedyCard;
import models.cards.safeties.SafetyCard;
import models.exceptions.DiscardChoiceOutOfBoundsException;
import models.exceptions.PlayedCardIndexOutOfBoundsException;
import models.moves.BasicMove;
import models.moves.Move;
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

	public Card pickCardToPlay( int cardIndex ) throws PlayedCardIndexOutOfBoundsException {
		if ( cardIndex < Game.MIN_HAND_CARDS || cardIndex > Game.MAX_INGAME_HAND_CARDS ) {
			throw new PlayedCardIndexOutOfBoundsException( "Choice must be between 1 and 5. " );
		} else {
			return this.handStack.get( cardIndex );
		}
	}
	
	public Card draw( GameStack drawStackChosen ) {
		return Stack.transferTopCard( drawStackChosen, this.handStack );
	}

	public void play( Move m ) {
		if ( m instanceof BasicMove ) {
			BasicMove bm = (BasicMove) m;
			if ( bm.getCardToPlay() instanceof HazardCard ) {
				if ( bm.getCardToPlay() instanceof SpeedLimit ) {
					bm.getTarget().getDistanceStack().addFirst( bm.getCardToPlay() );
				} else {
					bm.getTarget().getBattleStack().addFirst( bm.getCardToPlay() );
				}
			} else if ( bm.getCardToPlay() instanceof RemedyCard ) {
				bm.getSource().getBattleStack().addFirst( bm.getCardToPlay() );
			} else if ( bm.getCardToPlay() instanceof DistanceCard ) {
				bm.getSource().getDistanceStack().addFirst( bm.getCardToPlay() );
			} else if ( bm.getCardToPlay() instanceof SafetyCard ) {
				bm.getSource().getSafetyStack().addFirst( bm.getCardToPlay() );
			}
		}
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

	
	public BattleStack getBattleStack() {
		return battleStack;
	}

	public void setBattleStack( BattleStack battleStack ) {
		this.battleStack = battleStack;
	}

	public DistanceStack getDistanceStack() {
		return distanceStack;
	}

	public void setDistanceStack( DistanceStack distanceStack ) {
		this.distanceStack = distanceStack;
	}

	public void setHandStack( HandStack handStack ) {
		this.handStack = handStack;
	}

	public SafetyStack getSafetyStack() {
		return safetyStack;
	}

	public void setSafetyStack( SafetyStack safetyStack ) {
		this.safetyStack = safetyStack;
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
		;
	}
}
