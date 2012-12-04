package models.moves;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.DistanceCard;
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
import models.stacks.game.DiscardStack;

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

		Card refCard = CardFactory.createCard( CardType.Distance25 );
		
		bm.origin = new HumanPlayer();
		bm.target = new HumanPlayer();
		bm.cardToPlay = refCard;

		// -- > Set context
		
		try {
			bm.origin.getHandStack().push( refCard );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		// -- > Realize
		
		assertTrue( bm.origin.getHandStack().exists( refCard.getType() ) );
		
		try {
			assertFalse( bm.realize() );
		} catch ( IllegalCardTypeException e ) { 
			e.printStackTrace();
		}
		
		assertEquals( refCard, bm.target.getDistanceStack().peek() );
		assertFalse( bm.origin.getHandStack().exists( refCard.getType() ) );
		assertEquals( ((DistanceCard) refCard).getRange(), bm.target.getDistanceStack().getTravelledDistance());
	}

	@Test
	public void testRealizeHazard() {

		Card refCard = CardFactory.createCard( CardType.Accident );
		
		bm.origin = new HumanPlayer();
		bm.target = new HumanPlayer();
		bm.cardToPlay = refCard;

		// -- > Set context
		
		try {
			bm.origin.getHandStack().push( refCard );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		// -- > Realize
		
		assertTrue( bm.origin.getHandStack().exists( refCard.getType() ) );
		
		try {
			assertFalse( bm.realize() );
		} catch ( IllegalCardTypeException e ) { 
			e.printStackTrace();
		}
		
		assertEquals( refCard, bm.target.getBattleStack().peek() );
		assertFalse( bm.origin.getHandStack().exists( refCard.getType() ) );
		assertTrue( bm.target.getBattleStack().exists( refCard.getType() ) );
		assertTrue( bm.target.isAttacked() );
	}

	@Test
	public void testRealizeRemedy() {

		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();
		Card refCard = CardFactory.createCard( CardType.Repairs );
		
		bm.origin = refOrigin;
		bm.target = refOrigin;
		bm.cardToPlay = refCard;

		// -- > Set context
		
		try {
			bm.origin.getHandStack().push( refCard );
		} catch ( IllegalCardTypeException e2 ) {
			e2.printStackTrace();
		}
		
		try {
			bm.origin.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
			bm.origin.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}

		assertTrue( bm.origin.getHandStack().exists( refCard.getType() ) );
		
		assertTrue( bm.origin.isAttacked() );
		
		// -- > Realize
		
		try {
			assertFalse( bm.realize() );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertFalse( bm.origin.isAttacked() );

		assertFalse( bm.origin.getHandStack().exists( refCard.getType() ) );

		assertEquals( bm.origin.getBattleStackContent().getType(), CardType.GoRoll );
		
		assertEquals( DiscardStack.getInstance().peek().getType(), CardType.Repairs );
		assertTrue( DiscardStack.getInstance().exists( CardType.Accident ) );
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
		
		// -- > Case 1 : No initial go roll
		
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
		
		// -- > Case 2 : initial GoRoll is played 

		bm.cardToPlay = CardFactory.createCard( CardType.Distance25 );

		try {
			bm.performDistanceCardVerification( bm.target );
		} catch ( IllegalMoveException e ) {
			caught = e;
		}
		
		assertNull( caught );
		caught = null;
		
		// -- > Case 3 : PlayerIsAttacked
		
		try {
			bm.target.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		Class a = PlayerIsAttackedException.class;

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
	public void testPerformHazardCardVerification() {
		Throwable caught = null;
		HumanPlayer refOrigin = new HumanPlayer();
		HumanPlayer refTarget = new HumanPlayer();

		bm.origin = refOrigin;
		bm.target = refTarget;
		
		// -- > Case 1 : no initial GoRoll
		
		bm.cardToPlay = CardFactory.createCard( CardType.Accident );

		try {
			bm.target.getSafetyStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
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
		try {
			bm.target.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		// -- > Case 2
		
		bm.cardToPlay = CardFactory.createCard( CardType.Accident );

		try {
			bm.target.getSafetyStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
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

		try {
			bm.target.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
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

		try {
			bm.target.getBattleStack().push( CardFactory.createCard( CardType.OutOfGas ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
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
