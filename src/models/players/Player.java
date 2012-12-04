package models.players;

import java.util.ArrayList;

import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.DiscardChoiceOutOfBoundsException;
import models.exceptions.IllegalCardTypeException;
import models.moves.BasicMove;
import models.stacks.game.DiscardStack;
import models.stacks.game.GameStack;
import models.stacks.player.BattleStack;
import models.stacks.player.DistanceStack;
import models.stacks.player.HandStack;
import models.stacks.player.SafetyStack;

/**
 * @author Simon RENOULT
 * @version 1.1.3
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

	public void discard( Card cardToDiscard ) throws IllegalCardTypeException {
		this.handStack.shiftTo( DiscardStack.getInstance(), cardToDiscard );
	}

	public void discard( Integer discardingCardIndex ) throws IllegalCardTypeException, DiscardChoiceOutOfBoundsException {
		if ( discardingCardIndex < HandStack.MIN_CARD_NB - 1 || discardingCardIndex > HandStack.MAX_IN_PLAY_CARD - 1 ) {
			throw new DiscardChoiceOutOfBoundsException( "Please enter a number between " + HandStack.MIN_CARD_NB +
					" and " + HandStack.MAX_IN_PLAY_CARD );
		} else {
			discard( this.handStack.get( discardingCardIndex ) );
		}
	}

	public String toString() {
		return this.alias
				+ " - "
				+ this.distanceStack.getTravelledDistance()
				+ "km "
				+ '\n'
				+ "HAND : "
				+ this.handStack
				+ ( !safetyStack.isEmpty() ? '\n' + "SPECIAL : " + safetyStack
						: "" )
				+ ( !battleStack.isEmpty() ? '\n' + "BATTLE: " + battleStack
						: "" );
	}

	public boolean isProtectedFrom( HazardCard hc ) {
		for ( int i = 0; i < safetyStack.size() ; i++) {
			for ( CardFamily cf : safetyStack.get( i ).getFamilies() ) {
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

	/**
	 * TODO end me.
	 * 
	 * -- > Safety -- > OK -- > Hazard -- > SpeedLimit -- > DistanceStack is not
	 * slowed -- > OK -- > Other -- > BattleStack is not attacked -- > OK -- >
	 * Distance -- > Initial GoRoll played -- > Not attacked -- > Not slowed --
	 * > OK -- > Slowed && value <= 50 -- > OK -- > Remedy -- > Attacked/Slowed
	 * && Good family -- > OK -- > Initial GoRoll not played && GoRoll -- > OK
	 * 
	 * @return
	 */
	public boolean canPlay( ArrayList<Player> opponents ) {
		if ( handStack.containsSafety() ) {
			return true;
		}

		if ( handStack.containsHazard() ) {
			for ( Player p : opponents ) {
				if ( p.getBattleStack().initialGoRollIsPlayed() ) {
					if ( p.isAttacked() && ! p.isSlowed() && handStack.exists( CardType.SpeedLimit )) {
						return true;
					} else if ( p.isSlowed() && ! p.isAttacked() ) {
						for ( Card c : p.handStack.getCards() ) {
							if ( c instanceof HazardCard && c.getType() != CardType.SpeedLimit ) {
								return true;
							}
						}
					} else if ( ! p.isAttacked() && ! p.isSlowed() ) {
						return true;
					}
				}
			}

		}

		if ( handStack.containsDistance() ) {
			if ( battleStack.initialGoRollIsPlayed() ) {
				if ( !this.isAttacked() ) {
					if ( !this.isSlowed() ) {
						return true;
					} else {
						if ( handStack.containsSlowDistanceCard() ) {
							return true;
						}
					}
				}
			}
		}

		if ( handStack.containsRemedy() ) {
			if ( this.isAttacked() && handStack.hasRemedyFor( getBattleStackContent().getFamily() ) ) {
				return true;
			} else if ( this.isSlowed() && handStack.exists( CardType.GoRoll ) ) {
				return true;
			} else if ( !battleStack.initialGoRollIsPlayed() ) {
				if ( handStack.exists( CardType.GoRoll ) ) {
					return true;
				}
			}
		}

		return false;
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
		return this.battleStack;
	}

	public Card getBattleStackContent() {
		return this.getBattleStack().peek();
	}

	// ------------ SETTERS ------------ //

	public void setAlias( String string ) {
		this.alias = string;
	}
}
