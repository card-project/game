package models.stacks;

import models.cards.Card;
import models.cards.distances.DistanceCard;

/**
 * @author Simon RENOULT
 * @version 1.0
 */
public class DistanceStack extends PlayerStack {

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -8792591903898303572L;

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
	
	public Boolean maxNumberOfDistance200IsReached() {
		int distance200Counter = 0;
		for ( Card c : super.cards ) {
			if ( c instanceof DistanceCard ) {
				if ( ( ( DistanceCard ) c ).getRange() == 200 )
				distance200Counter++;
			}
		}
		
		return distance200Counter == 2;
	}
}
