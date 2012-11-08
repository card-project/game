package models.stacks;

import models.cards.Card;
import models.cards.distances.DistanceCard;

/**
 * @author Simon RENOULT
 * @version 1.0.1
 */
public class DistanceStack extends PlayerStack {

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -8792591903898303572L;
	private static final int MAX_DISTANCE200 = 2;

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
		
		return currentDistance;
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
}
