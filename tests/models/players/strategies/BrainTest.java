package models.players.strategies;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardFamily;
import models.cards.DistanceCard;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;

import org.junit.Before;
import org.junit.Test;

public class BrainTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Brain b;
	private HumanPlayer opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before public void init() {
		opponents = new ArrayList<Player>();
		opponent = new HumanPlayer();
		opponents.add( opponent );
		
		b = new Brain( new AIPlayer(), opponents );
	}
	
}
