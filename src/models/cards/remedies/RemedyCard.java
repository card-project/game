package models.cards.remedies;

import models.cards.Card;
import models.cards.hazards.HazardCard;


public class RemedyCard extends Card {
	public static final Integer MAX_INSTANCES = 21;
	public static final HazardCard OPPOSITE = null;
	
	public static Integer getMaxInstances() {
		return MAX_INSTANCES;
	}
	public static HazardCard getOpposite() {
		return OPPOSITE;
	}
	
	

}
