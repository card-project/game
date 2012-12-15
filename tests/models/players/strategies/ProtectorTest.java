package models.players.strategies;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.CardFactory;
import models.cards.DistanceCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;
import models.players.AIPlayer;
import models.stacks.game.DiscardStack;

import org.junit.Test;

public class ProtectorTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Protector protector = new Protector( new AIPlayer() );
	
	// ------------ METHODS ------------ //
	
	@Test
	public void testChooseStackToDraw() throws IllegalCardTypeException {
		
		// -- > Case 1 : DiscardStack is empty
		
		assertNull( protector.chooseStackToDraw() );
		
		// -- > Case 2 : Non corresponding card in discard stack
		
		DiscardStack.getInstance().push( CardFactory.createCard( CardType.Accident ) );
		assertNull( protector.chooseStackToDraw() );
		DiscardStack.getInstance().pop();
		
		// -- > Case 3 : Not started and initial GoRoll available in discardStack
		
		DiscardStack.getInstance().push( CardFactory.createCard( CardType.GoRoll ) );
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.GoStop );
		DiscardStack.getInstance().pop();
		
		// -- > Case 4 : Player is attacked and available Remedy
		
		protector.player.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		DiscardStack.getInstance().push( CardFactory.createCard( CardType.Repairs) );
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.StateOfCar );
		DiscardStack.getInstance().pop();
		protector.player.getBattleStack().pop();
		
		// -- > Case 5 : Player is slowed and available Remedy
		
		protector.player.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		DiscardStack.getInstance().push( CardFactory.createCard( CardType.EndOfLimit) );
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.Speed );
		DiscardStack.getInstance().pop();
		protector.player.getDistanceStack().pop();
	}

	@Test
	public void testChooseCardToPlay() throws IllegalCardTypeException {

		// -- > Setting context : Initializing hand
		
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Accident ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Distance100 ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Repairs ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.GoRoll ) );
		
		// -- > Case 1 : Not attacked/slowed
		
		assertNull ( protector.chooseCardToPlay() );
		
		// -- > Case 2 : Safety in hand

		protector.player.getHandStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily() , CardFamily.StateOfCar );

		// -- > Case 3 : Attacked and safety
		
		protector.player.getBattleStack().push( CardFactory.createCard( CardType.Accident ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily() , CardFamily.StateOfCar );
		protector.player.getBattleStack().pop();
		
		// -- > Case 4 : Slowed and safety
		
		protector.player.getDistanceStack().push( CardFactory.createCard( CardType.SpeedLimit ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.RightOfWay ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily(), CardFamily.GoStop );
		protector.player.getDistanceStack().pop();
		
	}

	@Test
	public void testChooseCardToDiscard() throws IllegalCardTypeException {
		
		// -- > Setting context : Initializing hand
		
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Accident ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Distance100 ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Repairs ) );
		protector.player.getHandStack().push( CardFactory.createCard( CardType.GoRoll ) );
		
		// -- > Case 1 : Nothing to discard
		
		assertNull ( protector.chooseCardToDiscard() );
		
		// -- > Case 2 : Duplicate remedies
		
		protector.player.getHandStack().push( CardFactory.createCard( CardType.Repairs ) );
		assertNotNull( protector.chooseCardToDiscard() );
		assertTrue( protector.chooseCardToDiscard() instanceof RemedyCard );
		assertEquals( protector.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	
		// -- > Case 3 : Duplicate remedy/played safety
		
		protector.player.getSafetyStack().push( CardFactory.createCard( CardType.DrivingAce ) );
		assertNotNull( protector.chooseCardToDiscard() );
		assertTrue( protector.chooseCardToDiscard() instanceof RemedyCard );
		assertEquals( protector.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	}

}
