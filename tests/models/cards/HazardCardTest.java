package models.cards;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Test;

public class HazardCardTest {

	// ------------ ATTRIBUTES ------------ //
	
	private HazardCard c = (HazardCard) CardFactory.createHazard( CardFamily.StateOfCar );
	
	// ------------ METHODS ------------ //
	
	@Test public void testIsPlayableOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();

		// -- > Case 1 : Not started yet

		assertFalse( c.isPlayableOn( p ) );
		
		// -- > Case 2 : Player has started

		p.getBattleStack().push( CardFactory.createRemedy( CardFamily.GoStop ) );
		assertTrue( c.isPlayableOn( p ) );
		
		// -- > Case 3 : Player is protected
		
		p.getSafetyStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertFalse( c.isPlayableOn( p ) );
		p.getSafetyStack().pop();
		
		// -- > Case 4 : Player is not protected

		assertTrue( c.isPlayableOn( p ) );
		
		// -- > Case 5 : Player is slowed
		
		p.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ) );
		
		c = CardFactory.createHazard( CardFamily.Speed );
		
		assertFalse( c.isPlayableOn( p ) );
		p.getDistanceStack().pop();
		
		// -- > Case 6 : Player is not slowed

		assertTrue( c.isPlayableOn( p ) );
		
		// -- > Case 7 : Player is attacked 
		
		p.getBattleStack().push( CardFactory.createHazard( CardFamily.Tire ) );

		c = CardFactory.createHazard( CardFamily.StateOfCar );

		assertFalse( c.isPlayableOn( p ) );
		p.getBattleStack().pop();
		
		// -- > Case 6 : Player is not attacked

		assertTrue( c.isPlayableOn( p ) );
	}

	@Test public void testPlayOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();

		Throwable caught = null;
		
		Player opponent = new HumanPlayer();
		opponent.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		
		HazardCard hazard = CardFactory.createHazard( CardFamily.StateOfCar );
		try {
			hazard.playOn( p, opponent );
		} catch ( AvailableCoupFourreException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
	}
}
