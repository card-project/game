package models.stacks;

import java.util.Collections;

import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.distances.Distance100;
import models.cards.distances.Distance200;
import models.cards.distances.Distance25;
import models.cards.distances.Distance50;
import models.cards.distances.Distance75;
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

public class DeckStack extends GameStack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6591146537976403648L;

	private DeckStack() {

		/* ADD HAZARDS CARDS */

		for ( int i = 0; i < Accident.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Accident ) );
		}

		for ( int i = 0; i < OutOfGas.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.OutOfGas ) );
		}

		for ( int i = 0; i < FlatTire.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.FlatTire ) );
		}

		for ( int i = 0; i < Stop.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Stop ) );
		}

		for ( int i = 0; i < SpeedLimit.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.SpeedLimit ) );
		}

		/* ADD REMEDIES CARDS */

		for ( int i = 0; i < Repairs.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Repairs ) );
		}

		for ( int i = 0; i < Gasoline.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Gasoline ) );
		}

		for ( int i = 0; i < SpareTire.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.SpareTire ) );
		}

		for ( int i = 0; i < GoRoll.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.GoRoll ) );
		}

		for ( int i = 0; i < EndOfLimit.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.EndOfLimit ) );
		}

		/* ADD SAFETIES CARDS */

		this.add( CardFactory.createCard( CardType.RightOfWay ) );
		this.add( CardFactory.createCard( CardType.DrivingAce ));
		this.add( CardFactory.createCard( CardType.ExtraTank ) );
		this.add( CardFactory.createCard( CardType.PunctureProof ) );

		/* ADD DISTANCES CARDS */

		for ( int i = 0; i < Distance25.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Distance25 ) );
		}

		for ( int i = 0; i < Distance50.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Distance50 ) );
		}

		for ( int i = 0; i < Distance75.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Distance75 ) );
		}

		for ( int i = 0; i < Distance100.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Distance100 ) );
		}

		for ( int i = 0; i < Distance200.MAX_INSTANCES; i++ ) {
			this.add( CardFactory.createCard( CardType.Distance200 ) );
		}
		
		Collections.shuffle( this );
	}

	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}

	public static DeckStack getInstance() {
		return DeckStackHolder.INSTANCE;
	}
}
