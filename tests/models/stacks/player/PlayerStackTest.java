package models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardType;
import models.exceptions.IllegalCardTypeException;

import org.junit.Test;

public class PlayerStackTest {

	// ------------ ATTRIBUTES ------------ //

	private PlayerStack playerStack = new HandStack();

	// ------------ METHODS ------------ //

	@Test
	public void testExists() {
		CardType reference = CardType.Accident;
		try {
			playerStack.push( CardFactory.createCard( reference ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( playerStack.exists( reference ) );
	}

	@Test
	public void testShiftTo() {
		BattleStack destinationStack = new BattleStack();
		Card chosenCard = CardFactory.createCard( CardType.Accident );

		assertNull( destinationStack.peek() );

		try {
			this.playerStack.shiftTo( destinationStack, chosenCard );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertNull( playerStack.peek() );
		assertEquals( chosenCard, destinationStack.peek() );
	}

}
