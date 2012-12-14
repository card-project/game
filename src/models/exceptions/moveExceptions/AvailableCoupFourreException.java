package models.exceptions.moveExceptions;

import models.cards.HazardCard;
import models.cards.SafetyCard;
import models.players.Player;

public class AvailableCoupFourreException extends Exception {

	private SafetyCard safety;
	private Player coupFourreInitiator;
	private HazardCard hazardCardInitiator;
	
	public AvailableCoupFourreException( SafetyCard safetyOf, Player initator, HazardCard originCard ) {
		this.safety = safetyOf;
		this.coupFourreInitiator = initator;
		this.hazardCardInitiator = originCard;
	}

	public HazardCard getHazardCardInitiator() {
		return hazardCardInitiator;
	}

	public Player getCoupFourreInitiator() {
		return coupFourreInitiator;
	}

	public SafetyCard getSafety() {
		return safety;
	}
}
