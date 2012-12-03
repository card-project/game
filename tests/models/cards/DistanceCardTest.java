package models.cards;

import static org.junit.Assert.*;

import org.junit.Test;

public class DistanceCardTest {

	// ------------ ATTRIBUTES ------------ //
	
	private DistanceCard c = (DistanceCard) CardFactory.createCard( CardType.Distance25 );
	
	// ------------ METHODS ------------ //
	
	@Test
	public void testGetRange() {
		assertEquals( c.getRange(), 25 );
	}
	
	public void testSetRange() {
		assertNotEquals( c.getRange(), 50 );
		c.setRange( 50 );
		assertEquals( c.getRange(), 50 );
	}
}
