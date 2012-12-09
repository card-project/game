package models.players;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardType;
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
		isProtectedFrom( CardType.Accident );
		isProtectedFrom( CardType.FlatTire );
		isProtectedFrom( CardType.OutOfGas );
		isProtectedFrom( CardType.SpeedLimit );
		isProtectedFrom( CardType.Stop );
	
	}

	private void isProtectedFrom( CardType hazardReference ) throws IllegalCardTypeException {
		HazardCard hazardCard = (HazardCard) CardFactory.createCard( hazardReference );
		
		// -- > Case 1 : No protection
		
		assertFalse( testPlayer.isProtectedFrom( hazardCard ) );
		
		// -- > Case 2 : DrivingAce
		
		opponent.safetyStack.push( CardFactory.createCard( CardType.DrivingAce ) );
		
		if ( hazardReference == CardType.Accident ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );
		}
		
		opponent.safetyStack.pop();
		
		// -- > Case 3 : ExtraTank

		opponent.safetyStack.push( CardFactory.createCard( CardType.ExtraTank ) );

		if ( hazardReference == CardType.OutOfGas ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );			
		}
		
		opponent.safetyStack.pop();
		
		// -- > Case 4 : PunctureProof

		opponent.safetyStack.push( CardFactory.createCard( CardType.PunctureProof ) );

		if ( hazardReference == CardType.FlatTire ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );			
		}
		
		opponent.safetyStack.pop();
		
		// -- > Case 5 : RightOfWay

		opponent.safetyStack.push( CardFactory.createCard( CardType.RightOfWay ) );

		if ( hazardReference == CardType.Stop || hazardReference == CardType.SpeedLimit ) {
			assertTrue( opponent.isProtectedFrom( hazardCard ) );
		} else {
			assertFalse( opponent.isProtectedFrom( hazardCard ) );			
		}
		
		opponent.safetyStack.pop();
		
	}
	
	@Test public void testCanPlay() throws IllegalCardTypeException {
		
		// -- > Case 1 : Player has not started yet : Distance

		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Distance25 ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 2 : Player has not started yet : Safety
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 3 : Player has started
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.GoRoll ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 4 : Repairs if not attacked
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Repairs ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 5 : Hazard if opponent is not attacked
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Accident ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 6 : Hazard on not started opponent

		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Accident ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();
		
		// -- > Case 7 : Hazard on started opponent

		opponent.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );

		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Accident ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 8 : Hazard on (started) attacked opponent
		
		opponent.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.FlatTire ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		opponent.getBattleStack().pop();
		
		// -- > Case 9 : Remedy if not attacked
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Repairs ) );
		assertFalse( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();

		// -- > Case 10 : Remedy if attacked

		testPlayer.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.Repairs ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		testPlayer.getHandStack().pop();
		
		testPlayer.getBattleStack().pop();
		
		// -- > Case 11 : Safety
		
		testPlayer.getHandStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		
	}

	@Test public void test() throws IllegalCardTypeException {
		testPlayer.handStack.push( CardFactory.createCard( CardType.OutOfGas ) );
		testPlayer.handStack.push( CardFactory.createCard( CardType.Distance200 ) );
		testPlayer.handStack.push( CardFactory.createCard( CardType.EndOfLimit ) );
		testPlayer.handStack.push( CardFactory.createCard( CardType.Distance100 ) );
		testPlayer.handStack.push( CardFactory.createCard( CardType.SpeedLimit ) );
		
		( ( RemedyCard )CardFactory.createCard( CardType.GoRoll ) ).playOn( testPlayer );
		
		assertTrue( testPlayer.canPlay( opponents, GOAL ) );
		
	}
	
}
