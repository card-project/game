package models.players.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DiscardStack;

import org.junit.Before;
import org.junit.Test;

public class RoadhogTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Roadhog roadhog;
	private HumanPlayer opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //

	@Before public void init() {
		opponent = new HumanPlayer();
		opponents = new ArrayList<>();
		opponents.add( opponent );
		roadhog = new Roadhog( new AIPlayer(), opponents );
	}
	
	@Test public void testChooseStackToDraw() {
		
		assertNull( roadhog.chooseStackToDraw() );
		
		DiscardStack.getInstance().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		
		assertNull( roadhog.chooseStackToDraw() );
		
		DiscardStack.getInstance().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		
		assertNotNull( roadhog.chooseStackToDraw() );
	}

	@Test public void testChooseCardToPlay() throws IllegalCardTypeException {
		
		// -- > Case 1 : Opponent has not started
		
		assertNull( roadhog.chooseCardToPlay() );
		
		opponent.getBattleStack().push( CardFactory.createRemedy( CardFamily.GoStop ) );

		roadhog.owner.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		roadhog.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		roadhog.owner.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		roadhog.owner.getHandStack().push( CardFactory.createDistance( 100 ) );
		
		// -- > Case 2 : Opponent has started
		
		assertNotNull( roadhog.chooseCardToPlay() );
		assertTrue( roadhog.chooseCardToPlay() instanceof HazardCard );
		assertEquals( roadhog.chooseCardToPlay().getFamily(), CardFamily.StateOfCar );
		
		// -- > Case 3 : Opponent is protected
		
		opponent.getSafetyStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertNull( roadhog.chooseCardToPlay() );
		opponent.getSafetyStack().pop();
		
		// -- > Case 4 : Opponent is already attacked
		
		opponent.getBattleStack().push( CardFactory.createHazard( CardFamily.Tire ) );
		assertNull( roadhog.chooseCardToPlay() );

		// -- > Case 5 : Opponent is attacked but not slowed
		
		roadhog.owner.getHandStack().push( CardFactory.createHazard( CardFamily.Speed ) );

		assertNotNull( roadhog.chooseCardToPlay() );
		assertTrue( roadhog.chooseCardToPlay() instanceof HazardCard );
		assertEquals( roadhog.chooseCardToPlay().getFamily(), CardFamily.Speed );
				
	}

	@Test public void testChooseTargetToAttack() {
		// Algorithms used for Roadhog#chooseTargetToAttack
		// and Roadhog#chooseCardToPlay are the same.
		assertTrue( true );
	}

	@Test public void testChooseCardToDiscard() throws IllegalCardTypeException {

		roadhog.owner.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		roadhog.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		roadhog.owner.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		roadhog.owner.getHandStack().push( CardFactory.createDistance( 100 ) );
		
		assertNull( roadhog.chooseCardToDiscard() );
		
		roadhog.owner.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		
		assertNotNull( roadhog.chooseCardToDiscard() );
		assertTrue( roadhog.chooseCardToDiscard() instanceof HazardCard );
		assertEquals( roadhog.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	}

}
