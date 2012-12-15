package models.players.strategies;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.RemedyCard;
import models.exceptions.IllegalCardTypeException;
import models.players.AIPlayer;
import models.stacks.game.DiscardStack;

import org.junit.Test;

public class DriverTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Driver driver = new Driver( new AIPlayer() );
	
	// ------------ METHODS ------------ //
	
	@Test public void testChooseStackToDraw() {

		// -- > Case 1 : No distance available on discard stack
		
		DiscardStack.getInstance().push( CardFactory.createCard( CardType.Accident ) );
		assertNull( driver.chooseStackToDraw() );
		
		// -- > Case 2 : Distance available on discard stack
		
		DiscardStack.getInstance().push( CardFactory.createCard( CardType.Distance25 ) );
		assertTrue( driver.chooseStackToDraw() instanceof DiscardStack );
	}
	
	@Test public void testChooseCardToPlay() throws IllegalCardTypeException {
		
		// -- > Setting context : Initiate player's hand
		
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.Distance100 ) );
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.Distance50) );
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.Accident ) );
		
		// -- > Case 1 : Nothing to play (need initial GoRoll)
		
		assertNull( driver.chooseCardToPlay() );
		
		// -- > Case 2 : GoRoll in hand and player has not started
		
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.GoRoll ) );
		assertTrue( driver.chooseCardToPlay().getFamily() == CardFamily.GoStop );
		assertTrue( driver.chooseCardToPlay() instanceof RemedyCard );
		
		// -- > Case 3 : Distance in hand and play has started
		
		driver.getPlayer().getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		assertEquals( ( ( DistanceCard ) driver.chooseCardToPlay() ).getRange(), 100 ); 
		assertTrue( driver.chooseCardToPlay() instanceof DistanceCard );
	}
	
	@Test public void testChooseCardToDiscard() throws IllegalCardTypeException {
		
		// -- > Setting context : Initiate player's hand
	
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.Distance100 ) );
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.Distance50) );
		driver.getPlayer().getHandStack().push( CardFactory.createCard( CardType.Accident ) );

		// -- > Case 1
		
		assertTrue( driver.chooseCardToDiscard() instanceof DistanceCard );
		assertEquals( ( ( DistanceCard ) driver.chooseCardToDiscard() ).getRange(), 50 );
	}
}
