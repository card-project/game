package models.stacks.player;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.CardType;
import models.exceptions.IllegalCardTypeException;
import models.stacks.game.DiscardStack;

import org.junit.Before;
import org.junit.Test;

public class BattleStackTest {

	// ------------ ATTRIBUTES ------------ //

	private BattleStack battleStack = new BattleStack();

	// ------------ METHODS ------------ //

	@Before
	public void initialize() {
		this.battleStack.getCards().clear();
	}

	@Test public void testPush() {
		assertTrue( "Not implemented yet.", false );
	}

	@Test public void testRemoveAll() {
		assertTrue( this.battleStack.getCards().isEmpty() );

		try {
			this.battleStack.push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertFalse( this.battleStack.getCards().isEmpty() );

		this.battleStack.removeAll();

		assertTrue( this.battleStack.getCards().isEmpty() );
	}
	
	
	@Test
	public void testRemoveHazards() {
		assertTrue( this.battleStack.getCards().isEmpty() );

		try {
			this.battleStack.push( CardFactory.createRemedy( CardFamily.GoStop ) );
			this.battleStack.push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		this.battleStack.discardHazards();
		
		assertTrue( this.battleStack.peek().getType() == CardType.GoRoll );
		assertTrue( DiscardStack.getInstance().peek().getType() == CardType.Accident );
	}

	@Test
	public void testInitialGoRollIsPlayed() {
		assertFalse( this.battleStack.initialGoRollIsPlayed() );

		try {
			this.battleStack.push( CardFactory.createRemedy( CardFamily.GoStop ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( this.battleStack.initialGoRollIsPlayed() );
	}
}
