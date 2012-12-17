package models.players;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.exceptions.IllegalCardTypeException;

import org.junit.Before;
import org.junit.Test;


public class PlayerTest {

	// ------------ CONSTANTS ------------ //
	
	private final static int GOAL = 1000;
	
	// ------------ ATTRIBUTES ------------ //

	private Player testPlayer;
	private Player opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before public void initialize() {
		testPlayer = new HumanPlayer();
		opponent = new HumanPlayer();
		opponents = new ArrayList<Player>();
		opponents.add( opponent );
	}
	
	@Test public void testIsAttacked() {
		// TODO end me.
	}
	
	@Test public void testIsSlowed() {
		// TODO end me.
	}
	
	@Test public void testIsProtectedFrom() throws IllegalCardTypeException {
		isProtectedFrom( CardFamily.StateOfCar );
		isProtectedFrom( CardFamily.Tire );
		isProtectedFrom( CardFamily.Gas );
		isProtectedFrom( CardFamily.Speed );
		isProtectedFrom( CardFamily.GoStop );
	}

	private void isProtectedFrom( CardFamily cf ) throws IllegalCardTypeException {
		HazardCard hazardCard = CardFactory.createHazard( cf );
		
		// -- > Case 1 : No protection
		
		assertFalse( testPlayer.isProtectedFrom( hazardCard ) );
		
		// -- > Case 2 : DrivingAce
		
		opponent.safetyStack.push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		
		if ( cf == CardFamily.StateOfCar ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );
		}
		
		opponent.safetyStack.pop();
		
		// -- > Case 3 : ExtraTank

		opponent.safetyStack.push( CardFactory.createSafety( CardFamily.Gas ) );

		if ( cf == CardFamily.Gas ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );			
		}
		
		opponent.safetyStack.pop();
		
		// -- > Case 4 : PunctureProof

		opponent.safetyStack.push( CardFactory.createSafety( CardFamily.Tire ) );

		if ( cf == CardFamily.Tire ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );			
		}
		
		opponent.safetyStack.pop();
		
		// -- > Case 5 : RightOfWay

		opponent.safetyStack.push( CardFactory.createSafety( CardFamily.GoStop ) );

		if ( cf == CardFamily.GoStop|| cf == CardFamily.Speed ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );			
		}
		
		opponent.safetyStack.pop();
		
	}
	
	@Test public void testCanPlay() throws IllegalCardTypeException {
		
		// -- > Case 1 : Player has not started yet : Distance

		testPlayer.getHandStack().push( CardFactory.createDistance( 25 ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 2 : Player has not started yet : Safety
		
		testPlayer.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 3 : Player has started
		
		testPlayer.getHandStack().push( CardFactory.createRemedy( CardFamily.GoStop ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 4 : Repairs if not attacked
		
		testPlayer.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 5 : Hazard if opponent is not attacked
		
		testPlayer.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 6 : Hazard on not started opponent

		testPlayer.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();
		
		// -- > Case 7 : Hazard on started opponent

		opponent.getBattleStack().push( CardFactory.createRemedy( CardFamily.GoStop ) );

		testPlayer.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 8 : Hazard on (started) attacked opponent
		
		opponent.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		
		testPlayer.getHandStack().push( CardFactory.createHazard( CardFamily.Tire ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		opponent.getBattleStack().pop();
		
		// -- > Case 9 : Remedy if not attacked
		
		testPlayer.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 10 : Remedy if attacked

		testPlayer.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		
		testPlayer.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();
		
		testPlayer.getBattleStack().pop();
		
		// -- > Case 11 : Safety
		
		testPlayer.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		
	}

	@Test public void test() throws IllegalCardTypeException {
		testPlayer.handStack.push( CardFactory.createHazard( CardFamily.Gas ) );
		testPlayer.handStack.push( CardFactory.createDistance( 200 ) );
		testPlayer.handStack.push( CardFactory.createRemedy( CardFamily.Speed ) );
		testPlayer.handStack.push( CardFactory.createDistance( 100 ) );
		testPlayer.handStack.push( CardFactory.createHazard( CardFamily.Speed ) );
		
		( ( RemedyCard )CardFactory.createRemedy( CardFamily.GoStop ) ).playOn( testPlayer );
		
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		
	}
	
}
