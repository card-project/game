package models.players.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.cards.CardFactory;
import models.cards.CardFamily;
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
		
		DiscardStack.getInstance().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertNull( protector.chooseStackToDraw() );
		DiscardStack.getInstance().pop();
		
		// -- > Case 3 : Not started and initial GoRoll available in discardStack
		
		DiscardStack.getInstance().push( CardFactory.createRemedy( CardFamily.GoStop ) );
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.GoStop );
		DiscardStack.getInstance().pop();
		
		// -- > Case 4 : Player is attacked and available Remedy
		
		protector.player.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		DiscardStack.getInstance().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.StateOfCar );
		DiscardStack.getInstance().pop();
		protector.player.getBattleStack().pop();
		
		// -- > Case 5 : Player is slowed and available Remedy
		
		protector.player.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ) );
		DiscardStack.getInstance().push( CardFactory.createRemedy( CardFamily.Speed ));
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.Speed );
		DiscardStack.getInstance().pop();
		protector.player.getDistanceStack().pop();
	}

	@Test
	public void testChooseCardToPlay() throws IllegalCardTypeException {

		// -- > Setting context : Initializing hand
		
		protector.player.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		protector.player.getHandStack().push( CardFactory.createDistance( 100 ) );
		protector.player.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ));
		protector.player.getHandStack().push( CardFactory.createRemedy( CardFamily.GoStop ));
		
		// -- > Case 1 : Not attacked/slowed
		
		assertNull ( protector.chooseCardToPlay() );
		
		// -- > Case 2 : Safety in hand

		protector.player.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily() , CardFamily.StateOfCar );

		// -- > Case 3 : Attacked and safety
		
		protector.player.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily() , CardFamily.StateOfCar );
		protector.player.getBattleStack().pop();
		
		// -- > Case 4 : Slowed and safety
		
		protector.player.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ));
		protector.player.getHandStack().push( CardFactory.createSafety( CardFamily.Speed ));
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily(), CardFamily.GoStop );
		protector.player.getDistanceStack().pop();
		
	}

	@Test
	public void testChooseCardToDiscard() throws IllegalCardTypeException {
		
		// -- > Setting context : Initializing hand
		
		protector.player.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		protector.player.getHandStack().push( CardFactory.createDistance( 100 ) );
		protector.player.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		protector.player.getHandStack().push( CardFactory.createRemedy( CardFamily.GoStop ) );
		
		// -- > Case 1 : Nothing to discard
		
		assertNull ( protector.chooseCardToDiscard() );
		
		// -- > Case 2 : Duplicate remedies
		

		protector.player.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertNotNull( protector.chooseCardToDiscard() );
		assertTrue( protector.chooseCardToDiscard() instanceof RemedyCard );
		assertEquals( protector.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	
		// -- > Case 3 : Duplicate remedy/played safety
		
		protector.player.getSafetyStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertNotNull( protector.chooseCardToDiscard() );
		assertTrue( protector.chooseCardToDiscard() instanceof RemedyCard );
		assertEquals( protector.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	}

}
