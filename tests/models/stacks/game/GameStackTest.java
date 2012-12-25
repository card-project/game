package models.stacks.game;

import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.stacks.player.HandStack;

import org.junit.Test;

public class GameStackTest {

	// ------------ CONSTANTS ------------ //

	// ------------ ATTRIBUTES ------------ //

	private DeckStack deckStack = DeckStack.getInstance();

	// ------------ METHODS ------------ //

	@Test
	public void testShiftTo() {
		Card topCard = this.deckStack.peek();
		HandStack destinationStack = new HandStack();

		this.deckStack.shiftTopCardTo( destinationStack );

		assertTrue( topCard != this.deckStack.peek() );
		assertTrue( topCard == destinationStack.peek() );
	}
}
