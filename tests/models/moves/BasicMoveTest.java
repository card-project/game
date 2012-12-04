package models.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.exceptions.moveExceptions.IllegalMoveException;
import models.exceptions.moveExceptions.InitialGoRollNotPlayedException;
import models.exceptions.moveExceptions.PlayerIsAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotAttackedException;
import models.exceptions.moveExceptions.PlayerIsNotSlowedException;
import models.exceptions.moveExceptions.PlayerIsProtectedException;
import models.exceptions.moveExceptions.PlayerIsSlowedException;
import models.exceptions.moveExceptions.UnsuitableRemedyException;
import models.players.HumanPlayer;

import org.junit.Before;
import org.junit.Test;

public class BasicMoveTest {

	// ------------ CONSTANTS ------------ //

	// ------------ ATTRIBUTES ------------ //

	private BasicMove bm;

	// ------------ METHODS ------------ //
	
	@Before
	public void setDefaultBasicMove() {
		bm = new BasicMove( new HumanPlayer() );
	}

	@Test
	public void testRealize() {
		
		// Case 1 : origin is null
		
		Throwable caught = null;
		
		bm.origin = null;
		bm.target = new HumanPlayer();
		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );
		
		try {
			bm.realize();
		} catch ( IllegalAccessError e ) {
			caught = e;
		} catch ( IllegalCardTypeException e2 ) {
			caught = e2;
		}
		
		assertNotNull ( caught );
		assertTrue ( caught instanceof IllegalAccessError );
		caught = null;
		
		// Case 2 : target is null
		
		bm.origin = new HumanPlayer();
		bm.target = null;
		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );

		try {
			bm.realize();
		} catch ( IllegalAccessError e ) {
			caught = e;
		} catch ( IllegalCardTypeException e2 ) {
			caught = e2;
		}
		
		assertNotNull ( caught );
		assertTrue ( caught instanceof IllegalAccessError );
		caught = null;
		
		// Case 3 : cardToPlay is null
		
		bm.origin = new HumanPlayer();
		bm.target = new HumanPlayer();
		bm.cardToPlay = null;

		try {
			bm.realize();
		} catch ( IllegalAccessError e ) {
			caught = e;
		} catch ( IllegalCardTypeException e2 ) {
			caught = e2;
		}
		
		assertNotNull ( caught );
		assertTrue ( caught instanceof IllegalAccessError );
		caught = null;
	}

	@Test
	public void testRealizeDistance() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = CardFactory.createCard( CardType.Distance25 );
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		bm.cardToPlay = refCard;
		
		try {
			assertFalse( bm.realize() );
		} catch ( IllegalCardTypeException e ) { 
			e.printStackTrace();
		}
		
		assertEquals( refCard, bm.target.getDistanceStack().peek() );
		
	}

	@Test
	public void testRealizeHazard() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = CardFactory.createCard( CardType.Accident );
		
		bm.origin = refOrigin;
		bm.target = refTarget;
		bm.cardToPlay = refCard;
		
		try {
			assertFalse( bm.realize() );
		} catch ( IllegalCardTypeException e ) { 
			e.printStackTrace();
		}
		
		assertEquals( refCard, bm.target.getBattleStack().peek() );
	}

	@Test
	public void testRealizeRemedy() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = CardFactory.createCard( CardType.Repairs );
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		bm.cardToPlay = refCard;

		try {
			bm.origin.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
			bm.origin.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		try {
			assertFalse( bm.realize() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertEquals( bm.origin.getBattleStackContent().getType(), CardType.GoRoll );
	}

	@Test
	public void testRealizeSafetyAndNoAttack() {

		HumanPlayer refOrigin = new HumanPlayer();
		Card refCard = null;
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		
		refCard = CardFactory.createCard( CardType.DrivingAce );
		bm.cardToPlay = refCard;

		try {
			assertTrue( bm.realize() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( bm.target.getHandStack().exists( CardType.DrivingAce ) );
		assertTrue( bm.target.getSafetyStack().exists( CardType.DrivingAce ) );
		assertEquals( bm.origin.getDistanceStack().getBonus100(), 1 );
	}

	@Test
	public void testRealizeSafetyAndWrongAttack() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = null;
		HazardCard refHazard = null;
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		
		refCard = CardFactory.createCard( CardType.DrivingAce );
		refHazard = (HazardCard) CardFactory.createCard( CardType.OutOfGas );
		
		bm.cardToPlay = refCard;
		
		assertFalse( bm.target.isAttacked() );
		
		try {
			bm.target.getBattleStack().push( refHazard );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		assertTrue( bm.target.isAttacked() );
		
		try {
			assertTrue( bm.realize() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( bm.origin.getSafetyStack().exists( CardType.DrivingAce ) );
		assertEquals( bm.origin.getDistanceStack().getBonus100(), 1 );
		assertTrue( bm.target.isAttacked() );
	}
	
	@Test
	public void testRealizeSafetyAndRightAttack() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = null;
		HazardCard refHazard = null;
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		
		refCard = CardFactory.createCard( CardType.RightOfWay );
		refHazard = (HazardCard) CardFactory.createCard( CardType.Stop );
		
		bm.cardToPlay = refCard;
		
		assertFalse( bm.target.isAttacked() );
		
		try {
			bm.target.getBattleStack().push( refHazard );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		assertTrue( bm.target.isAttacked() );
		
		try {
			assertTrue( bm.realize() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( bm.origin.getSafetyStack().exists( CardType.RightOfWay ) );
		assertEquals( bm.origin.getDistanceStack().getBonus100(), 1 );
		assertFalse( bm.target.isAttacked() );
	}
	
	@Test
	public void testRealizeRightOfWayAndSlowed() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = null;
		HazardCard refHazard = null;
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		bm.cardToPlay = refCard;
		
		refCard = CardFactory.createCard( CardType.RightOfWay );
		refHazard = (HazardCard) CardFactory.createCard( CardType.SpeedLimit );
		
		bm.cardToPlay = refCard;
		
		assertFalse( bm.target.isSlowed() );
		
		try {
			bm.target.getDistanceStack().push( refHazard );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		assertTrue( bm.target.isSlowed() );
		
		try {
			assertTrue( bm.realize() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( bm.origin.getSafetyStack().exists( CardType.RightOfWay ) );
		assertEquals( bm.origin.getDistanceStack().getBonus100(), 1 );
		assertFalse( bm.target.isSlowed() );
	}
	
	@Test
	public void testPerformDistanceCardVerification() {
		Throwable caught = null;
		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();

		bm.origin = refOrigin;
		bm.target = refTarget;
		
		// -- > Case 1
		
		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );
		
		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue( caught instanceof InitialGoRollNotPlayedException );
		caught = null;
		
		// -- > Set context
		
		try {
			bm.target.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 2

		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );

		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		caught = null;
		
		// -- > Case 3
		
		try {
			bm.target.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );

		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue( caught instanceof PlayerIsAttackedException );
		caught = null;
		
		// -- > Reset context
		
		try {
			bm.target.getBattleStack().discardHazards();
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		// -- > Case 4
		
		try {
			bm.target.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		bm.cardToPlay = CardFactory.createCard( CardType.Distance200 );

		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue( caught instanceof PlayerIsSlowedException );
		caught = null;
		
		// -- > Case 5
		
		bm.cardToPlay = CardFactory.createCard( CardType.Distance50 );

		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		caught = null;
		
		// -- > Case 6
		
		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );

		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		caught = null;
	}

	@Test
	public void testPerformHazardCardVerification() throws IllegalCardTypeException {
		Throwable caught = null;
		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();

		bm.origin = refOrigin;
		bm.target = refTarget;
		
		// -- > Case 1 : no initial GoRoll
		
		bm.cardToPlay = CardFactory.createCard( CardType.Accident );
		bm.target.getSafetyStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		
		try {
			bm.performHazardCardVerification( refTarget );
		} catch ( IllegalMoveException e ) {
			caught = e ;
		}
		
		assertNotNull( caught );
		assertTrue( caught instanceof IllegalMoveException );
		caught = null;
		bm.target.getSafetyStack().pop();
		
		// -- > Set context for next cases
		
		// Play the initial GoRoll
		bm.target.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		
		// -- > Case 2
		
		bm.cardToPlay = CardFactory.createCard( CardType.Accident );
		bm.target.getSafetyStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		
		try {
			bm.performHazardCardVerification( refTarget );
		} catch ( IllegalMoveException e ) {
			caught = e ;
		}
		
		assertNotNull( caught );
		assertTrue( caught instanceof PlayerIsProtectedException );
		caught = null;
		bm.target.getSafetyStack().pop();
		
		// -- > Case 3
		
		bm.cardToPlay = CardFactory.createCard( CardType.SpeedLimit );
		bm.target.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		
		try {
			bm.performHazardCardVerification( refTarget );
		} catch ( IllegalMoveException e ) {
			caught = e ;
		}
		
		assertTrue( caught instanceof PlayerIsSlowedException );
		assertNotNull ( caught );
		caught = null;
		
		// -- > Case 4
		
		bm.cardToPlay = CardFactory.createCard( CardType.Accident );
		bm.target.getBattleStack().push( CardFactory.createCard( CardType.OutOfGas ) );
		
		try {
			bm.performHazardCardVerification( refTarget );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue( caught instanceof PlayerIsAttackedException );
		caught = null;
	}
	
	@Test
	public void remedyCardVerificationSpeedNotSlowed() {
		Throwable caught = null;
		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();

		bm.origin = refOrigin;
		bm.target = refOrigin;
		
		bm.cardToPlay = CardFactory.createCard( CardType.EndOfLimit );
		
		try {
			bm.performRemedyCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue ( caught instanceof PlayerIsNotSlowedException );
		assertNull( bm.target.getBattleStackContent() );		
	}

	@Test
	public void remedyCardVerificationSpeedSlowed() {
		Throwable caught = null;
		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();

		bm.origin = refOrigin;
		bm.target = refOrigin;
		
		bm.cardToPlay = CardFactory.createCard( CardType.EndOfLimit );

		try {
			bm.target.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		try {
			bm.performRemedyCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		assertNull( bm.target.getBattleStackContent() );	
	}
	
	@Test
	public void remedyCardVerificationOthers() {
		Throwable caught = null;
		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();

		bm.origin = refOrigin;
		bm.target = refOrigin;
		
		// -- > Case 1
		
		bm.cardToPlay = CardFactory.createCard( CardType.GoRoll );
		
		try {
			bm.performRemedyCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull ( caught );
		caught = null;
		
		// -- > Case 2
		
		bm.cardToPlay = CardFactory.createCard( CardType.Repairs );
		
		try {
			bm.performRemedyCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue ( caught instanceof PlayerIsNotAttackedException );
		caught = null;
		
		// -- > Set context
		
		try {
			bm.target.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 3
		
		bm.cardToPlay = CardFactory.createCard( CardType.Gasoline );
		
		try {
			bm.performRemedyCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNotNull( caught );
		assertTrue ( caught instanceof UnsuitableRemedyException );
		caught = null;
		
		// -- > Case 4

		bm.cardToPlay = CardFactory.createCard( CardType.Repairs );
		
		try {
			bm.performRemedyCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		caught = null;
	}
	
	@Test
	public void testIsAnAttack() {
		bm.cardToPlay = CardFactory.createCard( CardType.Repairs );
		assertFalse( bm.isAnAttack() );
		
		bm.cardToPlay = CardFactory.createCard( CardType.Accident );
		assertTrue ( bm.isAnAttack() );
	}

}
