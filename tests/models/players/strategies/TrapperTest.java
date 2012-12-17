package models.players.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.RemedyCard;
import models.exceptions.IllegalCardTypeException;
import models.players.AIPlayer;

import org.junit.Before;
import org.junit.Test;

public class TrapperTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Trapper trapper;
	
	// ------------ METHODS ------------ //
	
	@Before public void init() {
		trapper = new Trapper( new AIPlayer() );
	}
	
	@Test public void testChooseCardToPlay() throws IllegalCardTypeException {
		
		assertNull( trapper.chooseCardToPlay() );
		
		trapper.player.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		
		assertNull( trapper.chooseCardToPlay() );
		
		trapper.player.getBattleStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		
		assertNull( trapper.chooseCardToPlay() );
		assertTrue( trapper.player.isAttacked() );
		
		trapper.player.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		
		assertNotNull( trapper.chooseCardToPlay() );
		assertTrue( trapper.chooseCardToPlay() instanceof RemedyCard );
		assertEquals( trapper.chooseCardToPlay().getFamily(), CardFamily.StateOfCar );
	}

}
