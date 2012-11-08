package models.stacks;

import java.util.Collections;

import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.distances.DistanceCard;
import models.cards.hazards.Accident;
import models.cards.hazards.FlatTire;
import models.cards.hazards.OutOfGas;
import models.cards.hazards.SpeedLimit;
import models.cards.hazards.Stop;
import models.cards.remedies.EndOfLimit;
import models.cards.remedies.Gasoline;
import models.cards.remedies.GoRoll;
import models.cards.remedies.Repairs;
import models.cards.remedies.SpareTire;

/**
 * @author Simon RENOULT
 * @version 1.1
 *
 */
public class DeckStack extends GameStack {

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = 6591146537976403648L;

	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}
	
	// ------------ CONSTRUCTORS ------------ //
	
	private DeckStack() {
		
		/* ADD HAZARDS CARDS */

		for ( int i = 0; i < Accident.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Accident ) );
		}

		for ( int i = 0; i < OutOfGas.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.OutOfGas ) );
		}

		for ( int i = 0; i < FlatTire.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.FlatTire ) );
		}

		for ( int i = 0; i < Stop.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Stop ) );
		}

		for ( int i = 0; i < SpeedLimit.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.SpeedLimit ) );
		}

		/* ADD REMEDIES CARDS */

		for ( int i = 0; i < Repairs.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Repairs ) );
		}

		for ( int i = 0; i < Gasoline.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Gasoline ) );
		}

		for ( int i = 0; i < SpareTire.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.SpareTire ) );
		}

		for ( int i = 0; i < GoRoll.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.GoRoll ) );
		}

		for ( int i = 0; i < EndOfLimit.MAX_INSTANCES; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.EndOfLimit ) );
		}

		/* ADD SAFETIES CARDS */

		super.cards.add( CardFactory.createCard( CardType.RightOfWay ) );
		super.cards.add( CardFactory.createCard( CardType.DrivingAce ));
		super.cards.add( CardFactory.createCard( CardType.ExtraTank ) );
		super.cards.add( CardFactory.createCard( CardType.PunctureProof ) );

		/* ADD DISTANCES CARDS */

		for ( int i = 0; i < DistanceCard.MAX_25 ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Distance25 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_50 ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Distance50 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_75 ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Distance75 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_100 ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Distance100 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_200 ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Distance200 ) );
		}
	}

	// ------------ METHODS ------------ //

	public static DeckStack getInstance() {
		return DeckStackHolder.INSTANCE;
	}

	public void shuffle() {
		Collections.shuffle( super.cards );
	}
}
