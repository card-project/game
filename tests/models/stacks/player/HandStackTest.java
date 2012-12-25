package models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;

import org.junit.Before;
import org.junit.Test;

public class HandStackTest {

	// ------------ ATTRIBUTES ------------ //

	private HandStack handStack = new HandStack();

	// ------------ METHODS ------------ //

	@Before public void init() {
		handStack.removeAll();
	}

	@Test public void testHasRemedyFor() {
		Card c = CardFactory.createRemedy( CardFamily.StateOfCar );

		try {
			this.handStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( handStack.hasRemedyFor( c.getFamily() ) );

		HazardCard hc = CardFactory.createHazard( CardFamily.StateOfCar );

		assertTrue( handStack.hasRemedyFor( hc.getFamily() ) );
	}
	
	@Test public void testChooseMaxDistance() throws IllegalCardTypeException {
		handStack.push( CardFactory.createDistance( 50 ) );
		handStack.push( CardFactory.createDistance( 200 ) );
		handStack.push( CardFactory.createDistance( 25 ) );
		handStack.push( CardFactory.createDistance( 75 ) );
		handStack.push( CardFactory.createDistance( 100 ) );

		assertTrue( handStack.chooseMaxDistance( false ) instanceof DistanceCard );
		assertEquals( handStack.chooseMaxDistance( false ).getRange(), 200 );

		assertTrue( handStack.chooseMaxDistance( true ) instanceof DistanceCard );
		assertEquals( handStack.chooseMaxDistance( true ).getRange(), 50 );
		
	}

}
