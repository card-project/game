package models.stacks.game;

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
	
	// ------------ CONSTRUCTORS ------------ //
	
	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}
	
	private DeckStack() {
		
		/* ADD HAZARDS CARDS */

		for ( int i = 0; i < HazardCard.MAX_ACCIDENT ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Accident ) );
		}

		for ( int i = 0; i < HazardCard.MAX_OUT_OF_GAS ; i++ ) {
			cards.add( CardFactory.createCard( CardType.OutOfGas ) );
		}

		for ( int i = 0; i < HazardCard.MAX_FLAT_TIRE ; i++ ) {
			cards.add( CardFactory.createCard( CardType.FlatTire ) );
		}

		for ( int i = 0; i < HazardCard.MAX_STOP ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Stop ) );
		}

		for ( int i = 0; i < HazardCard.MAX_SPEED_LIMIT ; i++ ) {
			cards.add( CardFactory.createCard( CardType.SpeedLimit ) );
		}

		/* ADD REMEDIES CARDS */

		for ( int i = 0; i < RemedyCard.MAX_REPAIRS; i++ ) {
			cards.add( CardFactory.createCard( CardType.Repairs ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_GASOLINE; i++ ) {
			cards.add( CardFactory.createCard( CardType.Gasoline ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_SPARE_TIRE; i++ ) {
			cards.add( CardFactory.createCard( CardType.SpareTire ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_GO_ROLL; i++ ) {
			cards.add( CardFactory.createCard( CardType.GoRoll ) );
		}

		for ( int i = 0; i < RemedyCard.MAX_END_OF_LIMIT ; i++ ) {
			cards.add( CardFactory.createCard( CardType.EndOfLimit ) );
		}

		/* ADD SAFETIES CARDS */

		cards.add( CardFactory.createCard( CardType.RightOfWay ) );
		cards.add( CardFactory.createCard( CardType.DrivingAce ));
		cards.add( CardFactory.createCard( CardType.ExtraTank ) );
		cards.add( CardFactory.createCard( CardType.PunctureProof ) );

		/* ADD DISTANCES CARDS */

		for ( int i = 0; i < DistanceCard.MAX_25 ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Distance25 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_50 ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Distance50 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_75 ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Distance75 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_100 ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Distance100 ) );
		}

		for ( int i = 0; i < DistanceCard.MAX_200 ; i++ ) {
			cards.add( CardFactory.createCard( CardType.Distance200 ) );
		}
	}

	// ------------ METHODS ------------ //

	public static DeckStack getInstance() {
		return DeckStackHolder.INSTANCE;
	}

	public void shuffle() {
		Collections.shuffle( cards );
	}
}
