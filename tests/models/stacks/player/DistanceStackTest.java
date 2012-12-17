package models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.exceptions.IllegalCardTypeException;

import org.junit.Before;
import org.junit.Test;

public class DistanceStackTest {

	// ------------ ATTRIBUTES ------------ //

	private DistanceStack distanceStack = new DistanceStack();;

	// ------------ METHODS ------------ //

	@Before public void initialize() {
		this.distanceStack.getCards().clear();
		this.distanceStack.resetBonuses();
	}

	@Test public void testPush() {
		assertTrue( "Not implemented yet.", false );
	}

	@Test public void testGetTravelledDistance() {
		DistanceCard c25 = CardFactory.createDistance( 25 );
		DistanceCard c50 = CardFactory.createDistance( 50 );
		DistanceCard c75 = CardFactory.createDistance( 75 );
		DistanceCard c100 = CardFactory.createDistance( 100 );
		DistanceCard c200 = CardFactory.createDistance( 200 );
		
		try {
			this.distanceStack.push( c25 );
			this.distanceStack.push( c50 );
			this.distanceStack.push( c75 );
			this.distanceStack.push( c100 );
			this.distanceStack.push( c200 );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		int total = c25.getRange() + c50.getRange() + c75.getRange()
				+ c100.getRange() + c200.getRange();

		assertEquals( total, distanceStack.getTraveledDistance() );
	}

	@Test
	public void testMaxNumberOfDistance200IsReached() {
		assertFalse( this.distanceStack.maxNumberOfDistance200IsReached() );

		this.distanceStack.getCards().add( CardFactory.createDistance( 200 ) );
		assertFalse( this.distanceStack.maxNumberOfDistance200IsReached() );

		this.distanceStack.getCards().add( CardFactory.createDistance( 200 ) );
		assertTrue( this.distanceStack.maxNumberOfDistance200IsReached() );
	}

	@Test
	public void testIsSlowed() {
		try {
			this.distanceStack.push( CardFactory.createHazard( CardFamily.Speed ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		assertTrue( this.distanceStack.isSlowed() );
	}

	@Test
	public void testIncreaseBy100() {
		DistanceCard c25 = CardFactory.createDistance( 25 );
		DistanceCard c50 = CardFactory.createDistance( 50 );
		DistanceCard c75 = CardFactory.createDistance( 75 );
		DistanceCard c100 = CardFactory.createDistance( 100 );
		DistanceCard c200 = CardFactory.createDistance( 200 );

		try {
			this.distanceStack.push( c25 );
			this.distanceStack.push( c50 );
			this.distanceStack.push( c75 );
			this.distanceStack.push( c100 );
			this.distanceStack.push( c200 );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		int total = c25.getRange() + c50.getRange() + c75.getRange()
				+ c100.getRange() + c200.getRange();

		this.distanceStack.increaseBy100();

		assertEquals( distanceStack.getTraveledDistance(), total + 100 );
	}

	@Test
	public void testIncreaseBy300() {
		DistanceCard c25 = CardFactory.createDistance( 25 );
		DistanceCard c50 = CardFactory.createDistance( 50 );
		DistanceCard c75 = CardFactory.createDistance( 75 );
		DistanceCard c100 = CardFactory.createDistance( 100 );
		DistanceCard c200 = CardFactory.createDistance( 200 );

		try {
			this.distanceStack.push( c25 );
			this.distanceStack.push( c50 );
			this.distanceStack.push( c75 );
			this.distanceStack.push( c100 );
			this.distanceStack.push( c200 );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		int total = c25.getRange() + c50.getRange() + c75.getRange()
				+ c100.getRange() + c200.getRange();

		this.distanceStack.increaseBy300();

		assertEquals( distanceStack.getTraveledDistance(), total + 300 );
	}

}
