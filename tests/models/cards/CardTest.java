package models.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CardTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Card c = CardFactory.createCard( CardType.Accident );
	
	// ------------ METHODS ------------ //
	
	@Test
	public void testCard() {
		CardFamily refFamily = CardFamily.Gas;
		CardType refType = CardType.OutOfGas;
		Card reference = new Card( refFamily, refType ) {};

		assertEquals( refFamily, reference.families.get( 0 ) );
		assertEquals( refType, reference.type );
	}

	@Test
	public void testAddFamily() {
		CardFamily reference = CardFamily.Gas; 
		c.addFamily( reference );
		
		for ( CardFamily cf : c.families ) {
			if ( cf == reference ) {
				assertTrue( true );
			}
		}
	}

	@Test
	public void testGetFamily() {
		assertNotNull( c.getFamily() );
	}

	@Test
	public void testGetFamilies() {
		assertNotNull( c.getFamilies() );
	}

	@Test
	public void testGetType() {
		assertNotNull( c.getType() );
	}

}
