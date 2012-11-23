package models.stacks;

import java.util.Collections;

import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;

/**
 * @author Simon RENOULT
 * @version 1.2
 *
 */
public class DeckStack extends GameStack {

	// ------------ ATTRIBUTES ------------ //
	
	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}
	
	// ------------ CONSTRUCTORS ------------ //
	
	private DeckStack() {
		
		/* ADD HAZARDS CARDS */

		for ( int i = 0; i < HazardCard.MAX_ACCIDENT ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Accident ) );
		}

		for ( int i = 0; i < HazardCard.MAX_OUT_OF_GAS ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.OutOfGas ) );
		}

		for ( int i = 0; i < HazardCard.MAX_FLAT_TIRE ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.FlatTire ) );
		}

		for ( int i = 0; i < HazardCard.MAX_STOP ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Stop ) );
		}

		for ( int i = 0; i < HazardCard.MAX_SPEED_LIMIT ; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.SpeedLimit ) );
		}

		/* ADD REMEDIES CARDS */

		for ( int i = 0; i < RemedyCard.MAX_REPAIRS; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Repairs ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_GASOLINE; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.Gasoline ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_SPARE_TIRE; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.SpareTire ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_GO_ROLL; i++ ) {
			super.cards.add( CardFactory.createCard( CardType.GoRoll ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_END_OF_LIMIT ; i++ ) {
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
