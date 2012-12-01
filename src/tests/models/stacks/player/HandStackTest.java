package tests.models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.exceptions.IllegalCardTypeException;
import models.stacks.player.HandStack;

import org.junit.Before;
import org.junit.Test;

public class HandStackTest {

	// ------------ ATTRIBUTES ------------ //
	
	private HandStack handStack = new HandStack();
	
	// ------------ METHODS ------------ //

	@Before
	public void initialize() {
		handStack.getCards().clear();
	}
	
	@Test
	public void testHasRemedyFor() {
		CardType refType = CardType.Repairs;
		Card c = CardFactory.createCard( refType );

		try {
			this.handStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue( handStack.hasRemedyFor( c.getFamily() ) );
		
		HazardCard hc = (HazardCard) CardFactory.createCard( CardType.Accident );
		
		assertTrue( handStack.hasRemedyFor( hc.getFamily() ) );
	}

	@Test
	public void testContainsSafety() {
		Card c = CardFactory.createCard( CardType.DrivingAce );
		
		try {
			handStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue( handStack.containsSafety() );
	}

	@Test
	public void testContainsHazard() {
		Card c = CardFactory.createCard( CardType.Accident );
		
		try {
			handStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue( handStack.containsHazard() );	
	}

	@Test
	public void testContainsRemedy() {
		Card c = CardFactory.createCard( CardType.Repairs );
		
		try {
			handStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue( handStack.containsRemedy() );
	}

	@Test
	public void testContainsDistance() {
		testDistance( CardType.Distance25 );
		testDistance( CardType.Distance50 );
		testDistance( CardType.Distance75 );
		testDistance( CardType.Distance100 );
		testDistance( CardType.Distance200 );
		
	}

	private void testDistance( CardType distanceType ) {
		try {
			this.handStack.push( (DistanceCard) CardFactory.createCard( distanceType ) );
		} catch ( IllegalCardTypeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue( handStack.containsDistance() );
		this.handStack.getCards().clear();
	}
	
	@Test
	public void testContainsSlowDistanceCard() {
		testSlowDistance( CardType.Distance25, true );
		testSlowDistance( CardType.Distance50, true  );
		testSlowDistance( CardType.Distance75, false );
		testSlowDistance( CardType.Distance100, false );
		testSlowDistance( CardType.Distance200, false );
	}
	
	private void testSlowDistance( CardType distanceType, boolean expected ) {
		try {
			this.handStack.push( (DistanceCard) CardFactory.createCard( distanceType ) );
		} catch ( IllegalCardTypeException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals( expected, handStack.containsSlowDistanceCard() );
		this.handStack.getCards().clear();
	}

}
