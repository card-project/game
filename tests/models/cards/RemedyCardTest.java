package models.cards;

import static org.junit.Assert.*;
import models.exceptions.IllegalCardTypeException;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DiscardStack;

import org.junit.Test;

public class RemedyCardTest {

	// ------------ ATTRIBUTES ------------ //
	
	private RemedyCard c = CardFactory.createRemedy( CardFamily.GoStop );
	
	// ------------ METHODS ------------ //
	
	@Test public void testIsPlayableOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		
		// -- > Case 1 : Player has not started yet
		
		assertTrue( c.isPlayableOn( p ) );
		
		c = CardFactory.createRemedy( CardFamily.StateOfCar );
		
		assertFalse( c.isPlayableOn( p ) );

		// -- > Case 2 : Player is not slowed
		
		c = CardFactory.createRemedy( CardFamily.Speed );
		
		assertFalse( c.isPlayableOn( p ) );

		// -- > Case 3 : Player is slowed
		
		p.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ) );
		
		c = CardFactory.createRemedy( CardFamily.Speed );
		
		assertTrue( c.isPlayableOn( p ) );
		
		p.getDistanceStack().pop();
		
		// -- > Case 4 : Player is not attacked
		
		c = CardFactory.createRemedy( CardFamily.StateOfCar );
		
		assertFalse( c.isPlayableOn( p ) );
		
		// -- > Case 5 : Player is attacked
		
		p.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		
		c = CardFactory.createRemedy( CardFamily.StateOfCar );
		
		assertTrue( c.isPlayableOn( p ) );
		
		p.getBattleStack().pop();
		
		// -- > Case 6 : Player is attacked and wrong remedy
		
		p.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		
		c = CardFactory.createRemedy( CardFamily.Tire );
		
		assertFalse( c.isPlayableOn( p ) );
		
	}

	@Test public void testPlayOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		RemedyCard speedRemedy = CardFactory.createRemedy( CardFamily.Speed );
		HazardCard speedHazard = CardFactory.createHazard( CardFamily.Speed );
		p.getHandStack().push( speedRemedy );
		p.getDistanceStack().push( speedHazard );
		
		assertTrue( p.getHandStack().exists( CardType.EndOfLimit ) );
		assertFalse( speedRemedy.playOn( p ) );
		assertFalse( p.getHandStack().exists( CardType.EndOfLimit ) );
		assertTrue( DiscardStack.getInstance().exists( CardType.SpeedLimit ) );
		assertTrue( DiscardStack.getInstance().peek().getType() == CardType.EndOfLimit );
	}
}
