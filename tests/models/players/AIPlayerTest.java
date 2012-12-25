package models.players;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AIPlayerTest {

	// ------------ CONSTANTS ------------ //
	
	private static final Integer DISTANCE_GOAL = 400;
	
	// ------------ ATTRIBUTES ------------ //
	
	private AIPlayer p = new AIPlayer();
	private HumanPlayer opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before public void initialize() {
		opponents = new ArrayList<Player>();
		opponent = new HumanPlayer();
		
		this.opponents.add( opponent );
	}
	
	@Test public void testDraw() {
		
	}

	@Test public void testPlay() {
		fail( "Not yet implemented" );
	}

	@Test public void testDiscard() {
		fail( "Not yet implemented" );
	}

}
