package models.stacks;

import models.cards.Card;
import models.cards.distances.Distance200;

public class DistanceStack extends PlayerStack {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8792591903898303572L;

	public Boolean maxNumberOfDistance200IsReached() {
		int distance200Counter = 0;
		for ( Card c : this ) {
			if ( c instanceof Distance200 ) {
				distance200Counter++;
			}
		}
		
		return distance200Counter == 2;
	}
}
