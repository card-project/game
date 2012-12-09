package models.cards;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import models.exceptions.IllegalCardTypeException;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Test;

public class DistanceCardTest {

	// ------------ CONSTANTS ------------ //
	
	private static final int GOAL = 700;
	
	// ------------ ATTRIBUTES ------------ //
	
	private DistanceCard c = (DistanceCard) CardFactory.createCard( CardType.Distance25 );
	
	// ------------ METHODS ------------ //
	
	@Test public void testGetRange() {
		assertEquals( c.getRange(), 25 );
	}
	
	@Test public void testSetRange() {
		assertNotEquals( c.getRange(), 50 );
		c.setRange( 50 );
		assertEquals( c.getRange(), 50 );
	}
	
	@Test public void testIsPlayableOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		
		// -- > Case 1 : Not started yet
		
		assertFalse( c.isPlayableOn( p, GOAL ) );
		
		// -- > Setting context : Add initial GoRoll
		
		p.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		
		// -- > Case 2 : Player has started
		
		assertTrue( c.isPlayableOn( p, GOAL ) );
		
		// -- > Setting context : Attacked player
		
		p.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		
		// -- > Case 3 : Attacked player
		
		assertFalse( c.isPlayableOn( p, GOAL ) );

		// -- > Setting context : Slowed player
		
		p.getBattleStack().pop();
		p.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		
		// -- > Case 4 : Slowed player
		
		c.setRange( 200 );
		assertFalse( c.isPlayableOn( p, GOAL ) );
		
		c.setRange( 50 );
		assertTrue( c.isPlayableOn( p, GOAL ) );
		
		// -- > Reseting context
		
		p.getDistanceStack().pop();
		
		// -- > Setting context : Maximum 200

		c.setRange( 200 );
		p.getDistanceStack().push( CardFactory.createCard( CardType.Distance200 ) );
			
		// -- > Case 5 : Maximum 200 not reached
	
		assertTrue( c.isPlayableOn( p, GOAL ) );
		
		// -- > Setting context : Maximum 200

		p.getDistanceStack().push( CardFactory.createCard( CardType.Distance200 ) );
			
		// -- > Case 5 : Maximum 200 not reached
	
		assertFalse( c.isPlayableOn( p, GOAL ) );
	}

	@Test public void testPlayOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		p.getHandStack().push( c );
		
		assertFalse( c.playOn( p ) );
		assertFalse( p.getHandStack().exists( CardType.Distance25 ) );
		assertTrue( p.getDistanceStack().exists( CardType.Distance25 ) );
		assertTrue( p.getDistanceStack().getTraveledDistance() == 25 );
	}
}
