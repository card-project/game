package models.players.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DiscardStack;

import org.junit.Before;
import org.junit.Test;

public class ProtectorTest {

	// ------------ ATTRIBUTES ------------ //
	
	protected Protector protector;
	private HumanPlayer opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before public void init() {
		opponent = new HumanPlayer();
		opponents = new ArrayList<>();
		opponents.add( opponent );
		protector = new Protector( new AIPlayer(), opponents );
	}
	
	@Test public void testChooseStackToDraw() throws IllegalCardTypeException {
		
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
		
		protector.owner.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		DiscardStack.getInstance().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.StateOfCar );
		DiscardStack.getInstance().pop();
		protector.owner.getBattleStack().pop();
		
		// -- > Case 5 : Player is slowed and available Remedy
		
		protector.owner.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ) );
		DiscardStack.getInstance().push( CardFactory.createRemedy( CardFamily.Speed ));
		assertNotNull( protector.chooseStackToDraw() );
		assertTrue( protector.chooseStackToDraw().peek() instanceof RemedyCard );
		assertEquals( protector.chooseStackToDraw().peek().getFamily(), CardFamily.Speed );
		DiscardStack.getInstance().pop();
		protector.owner.getDistanceStack().pop();
	}

	@Test public void testChooseCardToPlay() throws IllegalCardTypeException {

		// -- > Setting context : Initializing hand
		
		protector.owner.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		protector.owner.getHandStack().push( CardFactory.createDistance( 100 ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ));
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.GoStop ));
		
		// -- > Case 1 : Not attacked/slowed
		
		assertNull ( protector.chooseCardToPlay() );
		
		// -- > Case 2 : Safety in hand

		protector.owner.getHandStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily() , CardFamily.StateOfCar );

		// -- > Case 3 : Attacked and safety
		
		protector.owner.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamily() , CardFamily.StateOfCar );
		protector.owner.getBattleStack().pop();
		
		// -- > Case 4 : Slowed and safety
		
		protector.owner.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ));
		protector.owner.getHandStack().push( CardFactory.createSafety( CardFamily.Speed ));
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof SafetyCard );
		assertEquals( protector.chooseCardToPlay().getFamilies().get( 0 ), CardFamily.Speed );
		assertEquals( protector.chooseCardToPlay().getFamilies().get( 1 ), CardFamily.GoStop);
		protector.owner.getDistanceStack().pop();
		protector.owner.getHandStack().removeAll();
		
		// -- > Case 5 : Attacked and remedy
		
		protector.owner.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof RemedyCard );
		assertEquals( protector.chooseCardToPlay().getFamily(), CardFamily.StateOfCar );
		protector.owner.getHandStack().removeAll();
		protector.owner.getBattleStack().pop();
		
		// -- > Case 6 : Slowed and remedy
		
		protector.owner.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.Speed ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof RemedyCard );
		assertEquals( protector.chooseCardToPlay().getFamily(), CardFamily.Speed );
		protector.owner.getHandStack().removeAll();
		protector.owner.getDistanceStack().pop();
		
		
		// -- > Case 7 : Slowed and Attacked and remedies
		
		protector.owner.getBattleStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		protector.owner.getDistanceStack().push( CardFactory.createHazard( CardFamily.Speed ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.Speed ) );
		assertNotNull ( protector.chooseCardToPlay() );
		assertTrue( protector.chooseCardToPlay() instanceof RemedyCard );
		assertEquals( protector.chooseCardToPlay().getFamily(), CardFamily.StateOfCar );
		protector.owner.getHandStack().removeAll();
		protector.owner.getBattleStack().pop();
		protector.owner.getDistanceStack().pop();
		
	}

	@Test public void testChooseCardToDiscard() throws IllegalCardTypeException {
		
		// -- > Setting context : Initializing hand
		
		protector.owner.getHandStack().push( CardFactory.createHazard( CardFamily.StateOfCar ) );
		protector.owner.getHandStack().push( CardFactory.createDistance( 100 ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.GoStop ) );
		
		// -- > Case 1 : Discard a random remedy
		
		assertNotNull ( protector.chooseCardToDiscard() );
		
		// -- > Case 2 : Duplicate remedies

		protector.owner.getHandStack().push( CardFactory.createRemedy( CardFamily.StateOfCar ) );
		assertNotNull( protector.chooseCardToDiscard() );
		assertTrue( protector.chooseCardToDiscard() instanceof RemedyCard );
		assertEquals( protector.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	
		// -- > Case 3 : Duplicate remedy/played safety
		
		protector.owner.getSafetyStack().push( CardFactory.createSafety( CardFamily.StateOfCar ) );
		assertNotNull( protector.chooseCardToDiscard() );
		assertTrue( protector.chooseCardToDiscard() instanceof RemedyCard );
		assertEquals( protector.chooseCardToDiscard().getFamily(), CardFamily.StateOfCar );
	}

}
