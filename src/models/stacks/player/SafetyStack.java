package models.stacks.player;

import models.cards.Card;
import models.cards.SafetyCard;
import models.exceptions.IllegalCardTypeException;

public class SafetyStack extends PlayerStack {

	// ------------ ATTRIBUTES ------------ //

	// ------------ METHODS ------------ //

	@Override
	public void push( Card item ) throws IllegalCardTypeException {
		if ( ! ( item instanceof SafetyCard ) ) {
			throw new IllegalCardTypeException();
		} else {
			this.cards.push( item );
		}
	}
}
