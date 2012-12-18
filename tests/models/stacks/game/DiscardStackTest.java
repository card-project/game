package models.stacks.game;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DiscardStackTest {

	// ------------ CONSTANTS ------------ //
	
	// ------------ ATTRIBUTES ------------ //
	
	private DeckStack deckStack = DeckStack.getInstance();
	
	// ------------ METHODS ------------ //
	
	@Test
	public void testInstance() {
		assertEquals( this.deckStack, DeckStack.getInstance() );
	}
}
