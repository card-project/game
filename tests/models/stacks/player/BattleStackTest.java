package models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
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

	@Test
	public void testPush() {
		this.push( CardType.Accident, true );
		this.push( CardType.GoRoll, true );
		
		this.push( CardType.GoRoll, false );
		this.push( CardType.Repairs, false );
		this.push( CardType.Distance200, false );
		this.push( CardType.Distance25, false );
		this.push( CardType.DrivingAce, false );
	}

	private void push( CardType type, boolean expected ) {
		Throwable caught = null;

		Card c = CardFactory.createCard( type );

		try {
			this.battleStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			caught = e;
		}

		assertEquals( caught == null, expected );
	}

	@Test
	public void testIsAttacked() {
		assertFalse( this.battleStack.isAttacked() );

		try {
			this.battleStack.push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( this.battleStack.isAttacked() );
	}

	@Test
	public void testRemoveAll() {
		assertTrue( this.battleStack.getCards().isEmpty() );

		try {
			this.battleStack.push( CardFactory.createCard( CardType.Accident ) );
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
			this.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
			this.battleStack.push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			this.battleStack.discardHazards();
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( this.battleStack.peek().getType() == CardType.GoRoll );
		assertTrue( DiscardStack.getInstance().peek().getType() == CardType.Accident );
	}

	@Test
	public void testInitialGoRollIsPlayed() {
		assertFalse( this.battleStack.initialGoRollIsPlayed() );

		try {
			this.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( this.battleStack.initialGoRollIsPlayed() );
	}
}
