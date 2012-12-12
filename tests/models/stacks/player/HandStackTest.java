package models.stacks.player;

import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardType;
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
		CardType refType = CardType.Repairs;
		Card c = CardFactory.createCard( refType );

		try {
			this.handStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( handStack.hasRemedyFor( c.getFamily() ) );

		HazardCard hc = (HazardCard) CardFactory.createCard( CardType.Accident );

		assertTrue( handStack.hasRemedyFor( hc.getFamily() ) );
	}

}
