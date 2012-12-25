package models.players.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.RemedyCard;
import models.exceptions.IllegalCardTypeException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Before;
import org.junit.Test;

public class TrapperTest extends ProtectorTest {

	// ------------ ATTRIBUTES ------------ //
	
	protected Trapper trapper;
	private HumanPlayer opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before public void init() {
		opponent = new HumanPlayer();
		opponents = new ArrayList<>();
		opponents.add( opponent );
		trapper = new Trapper( new AIPlayer(), opponents );
	}
	
	@Test public void testChooseCardToPlay() throws IllegalCardTypeException {
		
		assertNull( trapper.chooseCardToPlay() );
		
		trapper.owner.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		
		assertNull( trapper.chooseCardToPlay() );
		
		trapper.owner.getBattleStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		
		assertNull( trapper.chooseCardToPlay() );
		assertTrue( trapper.owner.isAttacked() );
		
		trapper.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		
		assertNotNull( trapper.chooseCardToPlay() );
		assertTrue( trapper.chooseCardToPlay() instanceof RemedyCard );
		assertEquals( trapper.chooseCardToPlay().getFamily(), CardFamily.StateOfCar );
	}

}
