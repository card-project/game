package models.stacks.player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import models.cards.Card;
import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.CardType;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;

import org.junit.Test;

public class SafetyStackTest {

	// ------------ ATTRIBUTES ------------ //

	private SafetyStack safetyStack = new SafetyStack();

	// ------------ METHODS ------------ //

	@Test
	public void testPush() {
		SafetyCard correctCard = (SafetyCard) CardFactory
				.createCard( CardType.DrivingAce );
		DistanceCard distanceCard = (DistanceCard) CardFactory
				.createCard( CardType.Distance25 );
		HazardCard hazardCard = (HazardCard) CardFactory
				.createCard( CardType.Accident );
		RemedyCard remedyCard = (RemedyCard) CardFactory
				.createCard( CardType.Repairs );

		push( correctCard, true );
		push( distanceCard, false );
		push( hazardCard, false );
		push( remedyCard, false );
	}

	private void push( Card c, boolean expected ) {
		Throwable caught = null;

		try {
			safetyStack.push( c );
		} catch ( IllegalCardTypeException e ) {
			caught = e;
		}

		assertEquals( caught == null, expected );
	}
}
