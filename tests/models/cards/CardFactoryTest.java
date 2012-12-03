package models.cards;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class CardFactoryTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Card c;
	
	// ------------ METHODS ------------ //
	
	@Test
	public void testCardFactory() {
		testDistanceCard( CardType.Distance25, 25 );
		testDistanceCard( CardType.Distance50, 50 );
		testDistanceCard( CardType.Distance75, 75 );
		testDistanceCard( CardType.Distance100, 100 );
		testDistanceCard( CardType.Distance200, 200 );

		testHazardCard( CardType.Accident, CardFamily.StateOfCar );
		testHazardCard( CardType.FlatTire, CardFamily.Tire );
		testHazardCard( CardType.SpeedLimit, CardFamily.Speed );
		testHazardCard( CardType.Stop, CardFamily.GoStop );
		testHazardCard( CardType.OutOfGas, CardFamily.Gas );

		testRemedyCard( CardType.Repairs, CardFamily.StateOfCar );
		testRemedyCard( CardType.SpareTire, CardFamily.Tire );
		testRemedyCard( CardType.EndOfLimit, CardFamily.Speed );
		testRemedyCard( CardType.GoRoll, CardFamily.GoStop );
		testRemedyCard( CardType.Gasoline, CardFamily.Gas );
		
		testSafetyCard( CardType.DrivingAce, CardFamily.StateOfCar );
		testSafetyCard( CardType.ExtraTank, CardFamily.Gas );
		testSafetyCard( CardType.PunctureProof, CardFamily.Tire );
		testSafetyCard( CardType.RightOfWay, CardFamily.Speed, CardFamily.GoStop );

	}
	
	private void testDistanceCard( CardType ct, int rangeValue ) {
		c = CardFactory.createCard( ct );
		assertTrue( c instanceof DistanceCard );
		assertTrue( ( ( DistanceCard ) c ).getRange() == rangeValue ); 
		assertTrue( c.getType() == ct ); 
		assertTrue(  c.getFamilies() == null );
	}
	
	private void testHazardCard( CardType ct, CardFamily cf ) {
		c = CardFactory.createCard( ct );
		assertTrue( c instanceof HazardCard );
		assertTrue( c.getType() == ct ); 
		assertTrue(  c.getFamily() == cf );
	}
	
	private void testRemedyCard ( CardType ct, CardFamily cf ) {
		c = CardFactory.createCard( ct );
		assertTrue( c instanceof RemedyCard );
		assertTrue( c.getType() == ct ); 
		assertTrue(  c.getFamily() == cf );
	}
	
	private void testSafetyCard ( CardType ct, CardFamily cf ) {
		c = CardFactory.createCard( ct );
		assertTrue( c instanceof SafetyCard );
		assertTrue( c.getType() == ct ); 
		assertTrue(  c.getFamily() == cf );
	}
	
	private void testSafetyCard ( CardType ct, CardFamily cf1, CardFamily cf2 ) {
		c = CardFactory.createCard( ct );
		assertTrue( c instanceof SafetyCard );
		assertTrue( c.getType() == ct ); 
		for ( CardFamily cf : c.getFamilies() ) {
			assertTrue( cf == cf1 || cf == cf2 );
		}
	}


}
