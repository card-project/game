package models.stacks.player;

import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;

import org.junit.Before;
import org.junit.Test;

public class HandStackTest {

	// ------------ ATTRIBUTES ------------ //

	private HandStack handStack = new HandStack();

	// ------------ METHODS ------------ //

	@Before
	public void initialize() {
		handStack.getCards().clear();
	}

	@Test
	public void testHasRemedyFor() {
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

}
