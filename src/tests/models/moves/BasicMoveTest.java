package tests.models.moves;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import models.moves.BasicMove;
import models.players.HumanPlayer;
import models.players.Player;

import org.junit.Before;
import org.junit.Test;

public class BasicMoveTest {

	// ------------ CONSTANTS ------------ //

	// ------------ ATTRIBUTES ------------ //
	
	private BasicMove bm;
	
	@Before public void setDefaultBasicMove() {
		
	}

	@Test
	public void testBasicMove() {
		basicMove( null );
		basicMove( new HumanPlayer() );
	}
	
	private void basicMove( Player p ) {
		Throwable caught = null;
		
		try {
			new BasicMove( p );
		} catch ( NullPointerException e ) {
			caught = e;
		}
		
		if ( p == null ) {
			assertNotNull( caught );
		} else {
			assertNull( caught );
		}
	}

	@Test
	public void testRealize() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testTargetIsCompatible() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testCardAndPlayerStackAreCompatible() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testPerformDistanceCardVerification() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testPerformHazardCardVerification() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testPerformRemedyCardVerification() {
		fail( "Not yet implemented" );
	}

	@Test
	public void testIsAnAttack() {
		fail( "Not yet implemented" );
	}

}
