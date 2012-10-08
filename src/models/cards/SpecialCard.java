package models.cards;

import java.util.LinkedList;

public class SpecialCard extends Card {
	public final static Integer MAX_INSTANCES = 4;
	
	private LinkedList<AttackCard> opposites;
}
