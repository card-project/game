package models.cards;

import static org.junit.Assert.*;
import models.exceptions.IllegalCardTypeException;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DiscardStack;

import org.junit.Test;

public class RemedyCardTest {

	// ------------ ATTRIBUTES ------------ //
	
	private RemedyCard c = (RemedyCard) CardFactory.createCard( CardType.GoRoll );
	
	// ------------ METHODS ------------ //
	
	@Test public void testIsPlayableOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		
		// -- > Case 1 : Player has not started yet
		
		assertTrue( c.isPlayableOn( p ) );
		
		c = (RemedyCard) CardFactory.createCard( CardType.Repairs );
		
		assertFalse( c.isPlayableOn( p ) );

		// -- > Case 2 : Player is not slowed
		
		c = (RemedyCard) CardFactory.createCard( CardType.EndOfLimit );
		
		assertFalse( c.isPlayableOn( p ) );

		// -- > Case 3 : Player is slowed
		
		p.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		
		c = (RemedyCard) CardFactory.createCard( CardType.EndOfLimit );
		
		assertTrue( c.isPlayableOn( p ) );
		
		p.getDistanceStack().pop();
		
		// -- > Case 4 : Player is not attacked
		
		c = (RemedyCard) CardFactory.createCard( CardType.Repairs );
		
		assertFalse( c.isPlayableOn( p ) );
		
		// -- > Case 5 : Player is attacked
		
		p.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		
		c = (RemedyCard) CardFactory.createCard( CardType.Repairs );
		
		assertTrue( c.isPlayableOn( p ) );
		
		p.getBattleStack().pop();
		
		// -- > Case 6 : Player is attacked and wrong remedy
		
		p.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		
		c = (RemedyCard) CardFactory.createCard( CardType.SpareTire );
		
		assertFalse( c.isPlayableOn( p ) );
		
		
		
		
	}

	@Test public void testPlayOn() throws IllegalCardTypeException {
		Player p = new HumanPlayer();
		RemedyCard speedRemedy = ( RemedyCard ) CardFactory.createCard( CardType.EndOfLimit );
		HazardCard speedHazard = ( HazardCard ) CardFactory.createCard( CardType.SpeedLimit );
		p.getHandStack().push( speedRemedy );
		p.getDistanceStack().push( speedHazard );
		
		assertTrue( p.getHandStack().exists( CardType.EndOfLimit ) );
		assertFalse( speedRemedy.playOn( p ) );
		assertFalse( p.getHandStack().exists( CardType.EndOfLimit ) );
		assertTrue( DiscardStack.getInstance().exists( CardType.SpeedLimit ) );
		assertTrue( DiscardStack.getInstance().peek().getType() == CardType.EndOfLimit );
	}
}
