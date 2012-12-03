package models.stacks.game;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DeckStackTest {

	// ------------ CONSTANTS ------------ //
	
	private static final int DECK_CARD_NB = 65;
	
	// ------------ ATTRIBUTES ------------ //
	
	private DeckStack deckStack = DeckStack.getInstance();
	
	// ------------ METHODS ------------ //
	
	@Test
	public void testInstance() {
		assertEquals( this.deckStack, DeckStack.getInstance() );
	}
	
	@Test
	public void testSize() {
		assertEquals( DECK_CARD_NB, this.deckStack.size() );
	}
}
