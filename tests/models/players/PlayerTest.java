package models.players;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.stacks.player.PlayerStack;

import org.junit.Before;
import org.junit.Test;


public class PlayerTest {

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
	
	// ------------ SAFETY ------------ //
	
	@Test public void testCanPlaySafety() {
		canPlaySafety( CardType.DrivingAce );
		canPlaySafety( CardType.PunctureProof );
		canPlaySafety( CardType.RightOfWay );
		canPlaySafety( CardType.ExtraTank );
	}
	
	private void canPlaySafety( CardType type ) {
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( testPlayer.canPlay( null ));
		testPlayer.handStack.pop();
	}

	// ------------ HAZARD ------------ //

	@Test public void testCanPlayHazard() throws IllegalCardTypeException {
		canPlayHazard( CardType.Accident );
		canPlayHazard( CardType.Stop );
		canPlayHazard( CardType.FlatTire );
		canPlayHazard( CardType.OutOfGas );
		canPlayHazard( CardType.SpeedLimit );
	}
	
	private void canPlayHazard( CardType hazardType ) throws IllegalCardTypeException {
			
		// -- > Setting context : Add the hazard in the hand
		
		testPlayer.handStack.push( CardFactory.createCard( hazardType ) );
		
		// -- > Case 1 : No initial GoRoll
		
		assertFalse( testPlayer.canPlayHazard( opponents ));
		
		// -- > Setting context : Add initial GoRoll
		
		opponent.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
		
		// -- > Case 2 : Attack a free opponent
		
		assertTrue( testPlayer.canPlayHazard( opponents ));
		
		// -- > Setting context : Add an attack on battle stack
		
		opponent.battleStack.push( CardFactory.createCard( CardType.Stop ) );
		
		// -- > Case 3 : Attack an attacked opponent
		
		if ( hazardType == CardType.SpeedLimit ) {
			assertTrue( testPlayer.canPlayHazard( opponents) );
		} else {
			assertFalse( testPlayer.canPlayHazard( opponents) );
		}
		
		// -- > Reseting context
		
		opponent.battleStack.pop();
		
		// -- > Setting context : Add a speed limit on distance stack
		
		opponent.distanceStack.push( CardFactory.createCard( CardType.SpeedLimit ) );
		
		// -- > Case 4 : Slow a slowed opponents
		
		if ( hazardType == CardType.SpeedLimit ) {
			assertFalse( testPlayer.canPlayHazard( opponents) );
		} else {
			assertTrue( testPlayer.canPlayHazard( opponents) );
		}
		
		// -- > Reseting context
		
		opponent.distanceStack.pop();
		
		// -- > Case 5 : Attack a protected opponent
		
		// -- > Case 5.1 : DrivingAce
		
		opponent.safetyStack.push( CardFactory.createCard( CardType.DrivingAce ) );
		
		if ( hazardType == CardType.Accident ) {
			assertFalse( testPlayer.canPlayHazard( opponents ) );
		} else {
			assertTrue( testPlayer.canPlayHazard( opponents ) );
		}

		opponent.safetyStack.pop();
		
		// -- > Case 5.2 : ExtraTank

		opponent.safetyStack.push( CardFactory.createCard( CardType.ExtraTank ) );
		
		if ( hazardType == CardType.OutOfGas ) {
			assertFalse( testPlayer.canPlayHazard( opponents ) );
		} else {
			assertTrue( testPlayer.canPlayHazard( opponents ) );
		}

		opponent.safetyStack.pop();
		
		// -- > Case 5.3 : PunctureProof
		
		opponent.safetyStack.push( CardFactory.createCard( CardType.PunctureProof ) );
		
		if ( hazardType == CardType.FlatTire ) {
			assertFalse( testPlayer.canPlayHazard( opponents ) );
		} else {
			assertTrue( testPlayer.canPlayHazard( opponents ) );
		}

		opponent.safetyStack.pop();
		
		// -- > Case 5.4 : RightOfWay
		
		opponent.safetyStack.push( CardFactory.createCard( CardType.RightOfWay ) );
		
		if ( hazardType == CardType.Stop || hazardType == CardType.SpeedLimit ) {
			assertFalse( testPlayer.canPlayHazard( opponents ) );
		} else {
			assertTrue( testPlayer.canPlayHazard( opponents ) );
		}

		opponent.safetyStack.pop();
		
		initialize();
	}
	
	// ------------ DISTANCE ------------ //
	
	@Test public void testCanPlayDistance() {
		canPlayDistance( CardType.Distance25 );
		canPlayDistance( CardType.Distance50 );
		canPlayDistance( CardType.Distance75 );
		canPlayDistance( CardType.Distance100 );
		canPlayDistance( CardType.Distance200 );
	}
	
	private void canPlayDistance( CardType type ) {
		
		// -- > Case 1 : you have not started yet
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( testPlayer.canPlay( opponents ) );
		testPlayer.handStack.pop();
		
		// -- > Set context for incoming tests : set initialGoRoll as true
		
		try {
			testPlayer.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 2 : initial GoRoll is played
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		assertTrue( testPlayer.canPlay( opponents ) );
		testPlayer.handStack.pop();
		
		// -- > Case 3 : you are attacked

		try {
			testPlayer.battleStack.push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( testPlayer.canPlay( opponents ) );
		testPlayer.battleStack.pop();
		testPlayer.handStack.pop();
		
		// -- > Case 4 : you are slowed
	
		try {
			testPlayer.distanceStack.push( CardFactory.createCard( CardType.SpeedLimit ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		if ( type == CardType.Distance25 || type == CardType.Distance50 ) {
			assertTrue( testPlayer.canPlay( opponents ) );
		} else {
			assertFalse( testPlayer.canPlay( opponents ) );
		}
		
		initialize();
	}

	// ------------ REMEDY ------------ //
	
	@Test public void testCanPlayRemedy() {
		canPlayRemedy( CardType.EndOfLimit, CardType.SpeedLimit );
		canPlayRemedy( CardType.Gasoline, CardType.OutOfGas );
		canPlayRemedy( CardType.GoRoll, CardType.Stop );
		canPlayRemedy( CardType.Repairs, CardType.Accident );
		canPlayRemedy( CardType.SpareTire, CardType.FlatTire );
	}
	
	private void canPlayRemedy( CardType type, CardType opposite ) {
		// -- > Case 1 : initial GoRoll is not played
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		if ( type == CardType.GoRoll ) {
			assertTrue( testPlayer.canPlay( opponents ) );
		} else {
			assertFalse( testPlayer.canPlay( opponents ) );
		}
		
		testPlayer.handStack.pop();
		
		// -- > Set context : add initial GoRoll
		
		try {
			testPlayer.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 2 : attacked/slowed and good family
		
		PlayerStack destination;
		destination = type == CardType.SpeedLimit ? testPlayer.distanceStack : testPlayer .battleStack;
		
		try {
			destination.push( CardFactory.createCard( opposite ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( testPlayer.canPlay( opponents ) );
		testPlayer.battleStack.pop();
		testPlayer.handStack.pop();
		
		// -- > Case 3 : not attacked
		
		try {
			testPlayer.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( testPlayer.canPlay( opponents ) );
		testPlayer.handStack.pop();
		
		initialize();
	}
}
