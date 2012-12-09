package models.cards;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.exceptions.IllegalCardTypeException;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Test;

public class HazardCardTest {

	// ------------ ATTRIBUTES ------------ //
	
	private HazardCard c = (HazardCard) CardFactory.createCard( CardType.Accident );
	
	// ------------ METHODS ------------ //
	
	@Test public void testIsPlayableOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();

		// -- > Case 1 : Not started yet

		assertFalse( c.isPlayableOn( p ) );
		
		
		// -- > Case 2 : Player has started

		p.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		assertTrue( c.isPlayableOn( p ) );
		
		// -- > Case 3 : Player is protected
		
		p.getSafetyStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		assertFalse( c.isPlayableOn( p ) );
		p.getSafetyStack().pop();
		
		// -- > Case 4 : Player is not protected

		assertTrue( c.isPlayableOn( p ) );
		
		// -- > Case 5 : Player is slowed
		
		p.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		
		c = (HazardCard) CardFactory.createCard( CardType.SpeedLimit );
		
		assertFalse( c.isPlayableOn( p ) );
		p.getDistanceStack().pop();
		
		// -- > Case 6 : Player is not slowed

		assertTrue( c.isPlayableOn( p ) );
		
		// -- > Case 7 : Player is attacked 
		
		p.getBattleStack().push( CardFactory.createCard( CardType.FlatTire ) );

		c = (HazardCard) CardFactory.createCard( CardType.Accident );

		assertFalse( c.isPlayableOn( p ) );
		p.getBattleStack().pop();
		
		// -- > Case 6 : Player is not attacked

		assertTrue( c.isPlayableOn( p ) );
	}

	@Test public void testPlayOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		p.getHandStack().push( c );
		
		assertFalse( p.getHandStack().exists( CardType.Distance25 ) );
		assertTrue( p.getDistanceStack().exists( CardType.Distance25 ) );
	}
}
