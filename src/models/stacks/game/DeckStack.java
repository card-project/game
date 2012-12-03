package models.stacks.game;

import java.util.Collections;

import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;

/**
 * @author Simon RENOULT
 * @version 1.2.1
 *
 */
public class DeckStack extends GameStack {
	
	// ------------ CONSTRUCTORS ------------ //
	
	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}
	
	private DeckStack() {
		
		/* ADD HAZARDS CARDS */

		addCards( HazardCard.MAX_ACCIDENT, CardType.Accident );
		addCards( HazardCard.MAX_OUT_OF_GAS, CardType.OutOfGas );
		addCards( HazardCard.MAX_STOP, CardType.Stop );
		addCards( HazardCard.MAX_FLAT_TIRE, CardType.FlatTire );
		addCards( HazardCard.MAX_SPEED_LIMIT, CardType.SpeedLimit );

		/* ADD REMEDIES CARDS */

		addCards( RemedyCard.MAX_REPAIRS, CardType.Repairs );
		addCards( RemedyCard.MAX_GASOLINE, CardType.Gasoline );
		addCards( RemedyCard.MAX_SPARE_TIRE, CardType.SpareTire );
		addCards( RemedyCard.MAX_GO_ROLL, CardType.GoRoll );
		addCards( RemedyCard.MAX_END_OF_LIMIT, CardType.EndOfLimit );

		/* ADD SAFETIES CARDS */

		cards.add( CardFactory.createCard( CardType.RightOfWay ) );
		cards.add( CardFactory.createCard( CardType.DrivingAce ));
		cards.add( CardFactory.createCard( CardType.ExtraTank ) );
		cards.add( CardFactory.createCard( CardType.PunctureProof ) );

		/* ADD DISTANCES CARDS */

		addCards( DistanceCard.MAX_25, CardType.Distance25 );
		addCards( DistanceCard.MAX_50, CardType.Distance50 );
		addCards( DistanceCard.MAX_75, CardType.Distance75 );
		addCards( DistanceCard.MAX_100, CardType.Distance100 );
		addCards( DistanceCard.MAX_200, CardType.Distance200 );
	}

	// ------------ METHODS ------------ //

	public static DeckStack getInstance() {
		return DeckStackHolder.INSTANCE;
	}
	
	private void addCards( int maxNumber, CardType type ) {
		for ( int i = 0; i < maxNumber ; i++ ) {
			cards.add( CardFactory.createCard( type ) );
		}
	}
	
	public void shuffle() {
		Collections.shuffle( cards );
	}
}
