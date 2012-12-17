package models.cards;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.exceptions.IllegalCardTypeException;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Test;

public class SafetyCardTest {
	
	// ------------ ATTRIBUTES ------------ //
	
	SafetyCard c = CardFactory.createSafety( CardFamily.StateOfCar );
	
	// ------------ METHODS ------------ //
	
	@Test public void testIsPlayableOn() {
		Player p = new HumanPlayer();
		
		assertTrue( c.isPlayableOn( p ) );
	}
	
	@Test public void testPlayOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		p.getHandStack().push( c );
		
		assertTrue( c.playOn( p, 0 ) );
		assertFalse( p.getHandStack().exists( CardType.DrivingAce ) );
		assertTrue( p.getSafetyStack().exists( CardType.DrivingAce ) );
	}
}
