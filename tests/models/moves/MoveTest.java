package models.moves;

import static org.junit.Assert.*;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardType;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Before;
import org.junit.Test;

public class MoveTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Player p;
	private Move m;
	
	// ------------ METHODS ------------ //
	
	@Before
	public void initialize() {
		p = new Player() {};
		m = new Move(p) {
			
			@Override
			public boolean targetIsCompatible( Player targetPlayer )
					throws IllegalMoveException {
				return true;
			}
			
			@Override
			public boolean realize() throws IllegalCardTypeException {
				return true;
				
			}
		};
	}
	
	@Test
	public void testMove() {
		Throwable caught = null;
		
		try {
			m = new BasicMove( null );
		} catch ( NullPointerException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		caught = null;
		
		try {
			m = new BasicMove( new HumanPlayer() );
		} catch ( NullPointerException e ) {
			caught = e;
		}
		
		assertNull( caught );
		assertNotNull( m.origin );
	}

	@Test
	public void testGetCardToPlay() {
		Card reference = new Card() {};
		m.cardToPlay = reference;

		assertNotNull( m.getCardToPlay() );
		assertEquals( reference, m.getCardToPlay() );
	}

	@Test
	public void testGetSource() {
		Player reference = new Player() {};
		m.origin = reference;

		assertNotNull( m.getSource() );
		assertEquals( reference, m.getSource() );
	}

	@Test
	public void testGetTarget() {
		Player reference = new Player() {};
		m.target = reference;

		assertNotNull( m.getTarget() );
		assertEquals( reference, m.getTarget() );
	}

	@Test
	public void testSetCardToPlay() {
		Throwable caught = null;
		
		try {
			m.setCardToPlay( null );
		} catch ( IllegalAccessError e ) {
			caught = e ;
		}
		
		assertNotNull ( caught );
		caught = null;
		
		Card c = new Card() {};

		try {
			m.setCardToPlay( c );
		} catch ( IllegalAccessError e ) {
			caught = e ;
		}		
		
		assertNull ( caught );
		assertEquals( c, m.cardToPlay );
	}

	@Test
	public void testSetTarget() {
		Throwable caught = null;
		
		m.cardToPlay = null;
		
		try {
			m.setTarget( new Player() {} );
		} catch ( IllegalAccessError e ) {
			caught = e;
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull ( caught );
		assertTrue( caught instanceof IllegalAccessError );
		caught = null;
		
		m.cardToPlay = new Card() {};
		
		try {
			m.setTarget( null );
		} catch ( IllegalAccessError e ) {
			caught = e;
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull ( caught );
		assertTrue( caught instanceof IllegalAccessError );
		caught = null;
		
		m.cardToPlay = new Card() {};
		
		try {
			m.setTarget( m.origin );
		} catch ( IllegalAccessError e ) {
			caught = e;
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		assertEquals( m.origin, m.target );
		
		// The targetIsCompatible method is tested within the inherited classes. 
	}
}
