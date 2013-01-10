package models.stacks.game;

import java.util.Collections;

import models.Game;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;

/**
 * Structure initially containing the whole {@link Game} {@link Card} objects.
 * Use a Singleton design pattern to force the class uniqueness.
 * 
 * @author Simon RENOULT
 * @version 1.2.1
 * @see DeckStackTest
 */
public class DeckStack extends GameStack {

	// ------------ CONSTRUCTORS ------------ //

	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}

	private DeckStack () {

		addCard( CardFactory.createDistance( 25 ), Card.getMax( DistanceCard.class, 25 ) );
		addCard( CardFactory.createDistance( 50 ), Card.getMax( DistanceCard.class, 50 ) );
		addCard( CardFactory.createDistance( 75 ), Card.getMax( DistanceCard.class, 75 ) );
		addCard( CardFactory.createDistance( 100 ), Card.getMax( DistanceCard.class, 100 ) );
		addCard( CardFactory.createDistance( 200 ), Card.getMax( DistanceCard.class, 200 ) );

		addCard( CardFactory.createSafety( CardFamily.GoStop ), Card.getMax( SafetyCard.class, CardFamily.GoStop ) );
		addCard( CardFactory.createSafety( CardFamily.Gas ), Card.getMax( SafetyCard.class, CardFamily.Gas ) );
		addCard( CardFactory.createSafety( CardFamily.StateOfCar ),
				Card.getMax( SafetyCard.class, CardFamily.StateOfCar ) );
		addCard( CardFactory.createSafety( CardFamily.Tire ), Card.getMax( SafetyCard.class, CardFamily.Tire ) );

		addCard( CardFactory.createHazard( CardFamily.Gas ), Card.getMax( HazardCard.class, CardFamily.Gas ) );
		addCard( CardFactory.createHazard( CardFamily.GoStop ), Card.getMax( HazardCard.class, CardFamily.GoStop ) );
		addCard( CardFactory.createHazard( CardFamily.Speed ), Card.getMax( HazardCard.class, CardFamily.Speed ) );
		addCard( CardFactory.createHazard( CardFamily.StateOfCar ),
				Card.getMax( HazardCard.class, CardFamily.StateOfCar ) );
		addCard( CardFactory.createHazard( CardFamily.Tire ), Card.getMax( HazardCard.class, CardFamily.Tire ) );

		addCard( CardFactory.createRemedy( CardFamily.Gas ), Card.getMax( RemedyCard.class, CardFamily.Gas ) );
		addCard( CardFactory.createRemedy( CardFamily.GoStop ), Card.getMax( RemedyCard.class, CardFamily.GoStop ) );
		addCard( CardFactory.createRemedy( CardFamily.Speed ), Card.getMax( RemedyCard.class, CardFamily.Speed ) );
		addCard( CardFactory.createRemedy( CardFamily.StateOfCar ),
				Card.getMax( RemedyCard.class, CardFamily.StateOfCar ) );
		addCard( CardFactory.createRemedy( CardFamily.Tire ), Card.getMax( RemedyCard.class, CardFamily.Tire ) );
	}

	// ------------ METHODS ------------ //

	private void addCard( Card c, int number ) {
		for ( int i = 0 ; i < number ; i++ ) {
			super.cards.add( c );
		}
	}

	public static DeckStack getInstance() {
		return DeckStackHolder.INSTANCE;
	}

	public void shuffle() {
		Collections.shuffle( cards );
	}
}
