package models.players.strategies;

import java.util.ArrayList;

import models.players.AIPlayer;
import models.players.Brain;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Before;

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
		
		b = new Brain( new AIPlayer(), opponents, 700 );
	}
	
}
