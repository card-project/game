package models.players.strategies;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Before;
import org.junit.Test;

public class BrainTest {

	// ------------ ATTRIBUTES ------------ //
	
	private Brain b;
	private AIPlayer p;
	private HumanPlayer opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before public void init() {
		opponents = new ArrayList<Player>();
		opponent = new HumanPlayer();
		opponents.add( opponent );
		
		p = new AIPlayer();
		b = new Brain( p, opponents );
	}
	
	@Test
	public void test() {
		fail( "Not yet implemented" );
	}

}
