package models.players;

import java.util.ArrayList;

import models.cards.Card;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;
import models.stacks.player.BattleStack;
import models.stacks.player.DistanceStack;
import models.stacks.player.HandStack;
import models.stacks.player.SafetyStack;

/**
 * @author Simon RENOULT
 * @version 1.2.4
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
		drawStackChosen.shiftTopCardTo( this.handStack );
		return handStack.peek();
	}

	public void discard( Card cardToDiscard ) {
		try {
			this.handStack.shiftTo( DiscardStack.getInstance(), cardToDiscard );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
	}

	public boolean canPlay( ArrayList<Player> opponents, Integer distanceGoal ) {
		
		boolean canPlay = false;
		
		for( Card handCard : handStack.getCards() ) {
			if ( ! canPlay ) {
				if ( handCard instanceof DistanceCard ) {
					canPlay = ( ( DistanceCard ) handCard ).isPlayableOn( this, distanceGoal );
				} else if ( handCard instanceof RemedyCard ) {
					canPlay = ( ( RemedyCard ) handCard ).isPlayableOn( this );
				} else if ( handCard instanceof HazardCard ) {
					for( Player opponent : opponents ) {
						if ( ! canPlay ) {
							canPlay = ( ( HazardCard ) handCard ).isPlayableOn( opponent );
						}
					}
				} else if ( handCard instanceof SafetyCard ) {
					canPlay = ( ( SafetyCard ) handCard ).isPlayableOn( this );
				}
			}
		}
		
		return canPlay;
	}

	public boolean isProtectedFrom( HazardCard hc ) {
		for( Card c : safetyStack.getCards() ) {
			for( CardFamily cf : c.getFamilies() ) {
				if ( cf == hc.getFamily() ) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean isAttacked() {
		for ( int i = 0; i < battleStack.size() ; i++) {
			if ( battleStack.get( i ) instanceof HazardCard ) {
				return true;
			}
		}
		
		return false;
	}

	public boolean isSlowed() {
		for ( int i = 0; i < distanceStack.size() ; i++) {
			if ( distanceStack.get( i ).getType() == CardType.SpeedLimit ) {
				return true;
			}
		}
		
		return false;
	}

	public boolean hasStarted() {
		return battleStack.initialGoRollIsPlayed();
	}
	
	public int getTravelledDistance() {
		return this.distanceStack.getTraveledDistance();
	}
	
	@Override
	public String toString() {
		return this.getAlias() + '\n'
				+ "HAND : " + handStack + '\n'
				+ "DISTANCESTACK : " + distanceStack + '\n'
				+ "BATTLESTACK : " + battleStack + '\n'
				+ "SAFETYSTACK : " + safetyStack ;
	}
	
	// ------------ GETTERS ------------ //

	public BattleStack getBattleStack() {
		return this.battleStack;
	}

	public DistanceStack getDistanceStack() {
		return distanceStack;
	}

	public HandStack getHandStack() {
		return this.handStack;
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

	// ------------ SETTERS ------------ //

	public void setAlias( String string ) {
		this.alias = string;
	}

}
