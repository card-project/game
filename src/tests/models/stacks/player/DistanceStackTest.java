package tests.models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.cards.CardFactory;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.exceptions.IllegalCardTypeException;
import models.stacks.player.DistanceStack;

import org.junit.Before;
import org.junit.Test;

public class DistanceStackTest {

	// ------------ ATTRIBUTES ------------ //
	
	private DistanceStack distanceStack = new DistanceStack();;
	
	// ------------ METHODS ------------ //
	
	@Before 
	public void initialize() {
		this.distanceStack.getCards().clear();
		this.distanceStack.resetBonuses();
	}
	
	@Test
	public void testPush() {
		this.push( CardType.Distance25, true );
		this.push( CardType.Distance25, true );
		this.push( CardType.Distance25, true );
		
		this.push( CardType.Distance50, true );
		this.push( CardType.Distance50, true );
		this.push( CardType.Distance50, true );
		
		this.push( CardType.Distance75, true );
		this.push( CardType.Distance75, true );
		this.push( CardType.Distance75, true );

		this.push( CardType.Distance100, true );
		this.push( CardType.Distance100, true );
		this.push( CardType.Distance100, true );
		
		this.push( CardType.Distance200, true );
		this.push( CardType.Distance200, true );	

		this.push( CardType.Distance200, false );
	}	
	
	private void push( CardType type, boolean expected ) {
		Throwable caught = null;
		
		try {
			this.distanceStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			caught = e;
		}
		
		assertEquals( caught == null, expected );
	}
	
	@Test
	public void testGetTravelledDistance() {
		DistanceCard c25 = (DistanceCard) CardFactory.createCard( CardType.Distance25 );
		DistanceCard c50 = (DistanceCard) CardFactory.createCard( CardType.Distance50 );
		DistanceCard c75 = (DistanceCard) CardFactory.createCard( CardType.Distance75 );
		DistanceCard c100 = (DistanceCard) CardFactory.createCard( CardType.Distance100 );
		DistanceCard c200 = (DistanceCard) CardFactory.createCard( CardType.Distance200 );

		try {
			this.distanceStack.push( c25 );
			this.distanceStack.push( c50 );
			this.distanceStack.push( c75 );
			this.distanceStack.push( c100 );
			this.distanceStack.push( c200 );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		int total = c25.getRange() + c50.getRange() + c75.getRange() + c100.getRange() + c200.getRange();
		
		assertEquals( total, distanceStack.getTravelledDistance() );
	
	}

	@Test
	public void testMaxNumberOfDistance200IsReached() {
		assertFalse( this.distanceStack.maxNumberOfDistance200IsReached() );
		
		this.distanceStack.getCards().add( CardFactory.createCard( CardType.Distance200 ) );
		assertFalse( this.distanceStack.maxNumberOfDistance200IsReached() );
		
		this.distanceStack.getCards().add( CardFactory.createCard( CardType.Distance200 ) );
		assertTrue( this.distanceStack.maxNumberOfDistance200IsReached() );
	}

	@Test
	public void testIsSlowed() {
		try {
			this.distanceStack.push( CardFactory.createCard( CardType.SpeedLimit ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( this.distanceStack.isSlowed() );
	}
	
	@Test
	public void testIncreaseBy100() {
		DistanceCard c25 = (DistanceCard) CardFactory.createCard( CardType.Distance25 );
		DistanceCard c50 = (DistanceCard) CardFactory.createCard( CardType.Distance50 );
		DistanceCard c75 = (DistanceCard) CardFactory.createCard( CardType.Distance75 );
		DistanceCard c100 = (DistanceCard) CardFactory.createCard( CardType.Distance100 );
		DistanceCard c200 = (DistanceCard) CardFactory.createCard( CardType.Distance200 );

		try {
			this.distanceStack.push( c25 );
			this.distanceStack.push( c50 );
			this.distanceStack.push( c75 );
			this.distanceStack.push( c100 );
			this.distanceStack.push( c200 );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		int total = c25.getRange() + c50.getRange() + c75.getRange() + c100.getRange() + c200.getRange();
		
		this.distanceStack.increaseBy100();
		
		assertEquals( distanceStack.getTravelledDistance(), total + 100 ); 
	}

	@Test
	public void testIncreaseBy300() {
		DistanceCard c25 = (DistanceCard) CardFactory.createCard( CardType.Distance25 );
		DistanceCard c50 = (DistanceCard) CardFactory.createCard( CardType.Distance50 );
		DistanceCard c75 = (DistanceCard) CardFactory.createCard( CardType.Distance75 );
		DistanceCard c100 = (DistanceCard) CardFactory.createCard( CardType.Distance100 );
		DistanceCard c200 = (DistanceCard) CardFactory.createCard( CardType.Distance200 );

		try {
			this.distanceStack.push( c25 );
			this.distanceStack.push( c50 );
			this.distanceStack.push( c75 );
			this.distanceStack.push( c100 );
			this.distanceStack.push( c200 );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		int total = c25.getRange() + c50.getRange() + c75.getRange() + c100.getRange() + c200.getRange();
		
		this.distanceStack.increaseBy300();
		
		assertEquals( distanceStack.getTravelledDistance(), total + 300 ); 	
	}

}
