package models.stacks.player;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.MaxNumberOfDistance200IsReached;
import models.stacks.game.DiscardStack;

/**
 * @author Simon RENOULT
 * @version 1.0.1
 */
public class DistanceStack extends PlayerStack {

	// ------------ CONSTANTS ------------ //
	
	// ------------ ATTRIBUTES ------------ //
	
	private static int MAX_DISTANCE200 = 2;
	private int BONUS_100_CPT = 0;
	private int BONUS_300_CPT = 0;
	
	// ------------ METHODS ------------ //

	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		if ( item instanceof DistanceCard ) {
			if ( ( ( DistanceCard ) item ).getRange() == 200 && maxNumberOfDistance200IsReached() ) {
				throw new MaxNumberOfDistance200IsReached();
			}
		}

		super.cards.push( item );
	}
	
	/**
	 * Get the amount of driven kilometers.
	 * 
	 * @return the amount of driven kilometers.
	 */
	public int getTraveledDistance() {
		int currentDistance = 0;
		for( Card c : super.cards ) {
			if ( c instanceof DistanceCard ) {
				currentDistance += ( (DistanceCard ) c ).getRange();
			}
		}
		
		return currentDistance + 100 * BONUS_100_CPT + 300 * BONUS_300_CPT;
	}
	
	/**
	 * Check whether the max number of Distance 200 is reached.
	 * 
	 * @return <em>True</em> if the limit is reached. <em>False</em> instead.
	 */
	public Boolean maxNumberOfDistance200IsReached() {
		int distances200Counter = 0;
		for ( Card c : super.cards ) {
			if ( c.getType() == CardType.Distance200 ) {
				distances200Counter++;
			}
		}
		
		return distances200Counter == MAX_DISTANCE200;
	}

	public boolean isSlowed() {
		return exists( CardType.SpeedLimit );
	}

	public void increaseBy100() {
		BONUS_100_CPT++;
	}

	public void increaseBy300() {
		BONUS_300_CPT++;
	}
	
	public int getBonus100() {
		return this.BONUS_100_CPT;
	}
	
	public int getBonus300() {
		return this.BONUS_300_CPT;
	}
	
	public void resetBonuses() {
		BONUS_100_CPT = 0;
		BONUS_300_CPT = 0;
	}
	
	public void discardHazards() {
		for ( int i = 0; i < super.cards.size() ; i++ ) {
			if ( super.cards.get( i ) instanceof HazardCard ) {
				try {
					shiftTo( DiscardStack.getInstance(), super.cards.get( i ) );
				} catch ( IllegalCardTypeException e ) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
