package models.stacks.player;

import models.cards.Card;
import models.cards.CardType;
import models.cards.DistanceCard;

/**
 * @author Simon RENOULT
 * @version 1.0.1
 */
public class DistanceStack extends PlayerStack {

	// ------------ ATTRIBUTES ------------ //
	
	private static int MAX_DISTANCE200 = 2;
	private static int BONUS_100_CPT = 0;
	private static int BONUS_300_CPT = 0;
	
	// ------------ METHODS ------------ //
	
	/**
	 * Get the amount of driven kilometers.
	 * 
	 * @return the amount of driven kilometers.
	 */
	public int getTravelledDistance() {
		int currentDistance = 0;
		for( Card c : super.cards ) {
			currentDistance += ( (DistanceCard ) c ).getRange();
		}
		
		return currentDistance + 100 * BONUS_100_CPT + 300 * BONUS_300_CPT;
	}
	
	/**
	 * Check whether the max number of Distance 200 is reached.
	 * 
	 * @return <em>True</em> if the limit is reached. <em>False</em> instead.
	 */
	public Boolean maxNumberOfDistance200IsReached() {
		int distance200Counter = 0;
		for ( Card c : super.cards ) {
			if ( c instanceof DistanceCard ) {
				if ( ( ( DistanceCard ) c ).getRange() == 200 )
				distance200Counter++;
			}
		}
		
		return distance200Counter == MAX_DISTANCE200;
	}

	public boolean isSlowed() {
		return exists( CardType.SpeedLimit );
	}
	
	public void addFirst( DistanceCard c ) {
		if ( c.getRange() == 200 ) {
			if ( this.maxNumberOfDistance200IsReached() ) {
				throw new IllegalAccessError( "Maximum number of 200 Distances is reached." );
			} else {
				MAX_DISTANCE200--;
				this.cards.addFirst( c );
			}
		} else {
			this.cards.addFirst( c );
		}
	}

	public void increaseBy100() {
		BONUS_100_CPT++;
	}

	public void increaseBy300() {
		BONUS_300_CPT++;
	}
}
