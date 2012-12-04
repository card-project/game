package models.players;

import static org.junit.Assert.*;

import java.util.ArrayList;

import models.cards.CardFactory;
import models.cards.CardType;
import models.exceptions.IllegalCardTypeException;
import models.stacks.player.PlayerStack;

import org.junit.*;

public class PlayerTest {

	// ------------ ATTRIBUTES ------------ //

	private Player p;
	private Player opponent;
	private ArrayList<Player> opponents;
	
	// ------------ METHODS ------------ //
	
	@Before
	public void initialize() {
		p = new HumanPlayer();
		opponent = new HumanPlayer();
		opponents = new ArrayList<Player>();
		opponents.add( opponent );
	}

	@Test
	public void testIsAttacked() {
		// TODO end me.
	}
	
	@Test
	public void testIsSlowed() {
		// TODO end me.
	}
	
	@Test
	public void testIsProtected() {
		// TODO end me.
	}
	
	@Test
	public void testCanPlaySafety() {
		canPlaySafety( CardType.DrivingAce );
		canPlaySafety( CardType.PunctureProof );
		canPlaySafety( CardType.RightOfWay );
		canPlaySafety( CardType.ExtraTank );
	}
	
	private void canPlaySafety( CardType type ) {
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( p.canPlay( null ));
		p.handStack.pop();
	}

	@Test
	public void testCanPlayAccident() {
		canPlayHazard( CardType.Accident );
	}

	@Test
	public void testCanPlayFlatTire() {
		canPlayHazard( CardType.FlatTire );
	}

	@Test
	public void testCanPlayOutOfGas() {
		canPlayHazard( CardType.OutOfGas );
	}

	@Test
	public void testCanPlaySpeedLimit() {
		canPlayHazard( CardType.SpeedLimit );
	}

	@Test
	public void testCanPlayStop() {
		canPlayHazard( CardType.Stop );
	}
	
	private void canPlayHazard( CardType type ) {

		// -- > Case 1 : opponent has not started yet
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( p.canPlay( opponents ) );
		p.handStack.pop();
		
		// -- > Set context for incoming tests : set initialGoRoll as true
		
		try {
			opponent.getBattleStack().push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 2 : opponent has started
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( p.canPlay( opponents ) );
		
		// -- > Set context for incoming test : add a hazard on opponent
		
		PlayerStack destination;
		destination = type == CardType.SpeedLimit ? opponent.distanceStack : opponent.battleStack;
		
		try {
			destination.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 3 : opponent is already attacked
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( p.canPlay( opponents ) );
	}

	@Test
	public void testCanPlayDistance25() {
		canPlayDistance( CardType.Distance25 );
	}

	@Test
	public void testCanPlayDistance50() {
		canPlayDistance( CardType.Distance50 );
	}

	@Test
	public void testCanPlayDistance75() {
		canPlayDistance( CardType.Distance75 );
	}

	@Test
	public void testCanPlayDistance100() {
		canPlayDistance( CardType.Distance100 );
	}

	@Test
	public void testCanPlayDistance200() {
		canPlayDistance( CardType.Distance200 );
	}
	
	private void canPlayDistance( CardType type ) {
		// -- > Case 1 : you have not started yet
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( p.canPlay( opponents ) );
		p.handStack.pop();
		
		// -- > Set context for incoming tests : set initialGoRoll as true
		
		try {
			p.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 2 : initial GoRoll is played
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e1 ) {
			e1.printStackTrace();
		}
		
		assertTrue( p.canPlay( opponents ) );
		p.handStack.pop();
		
		// -- > Case 3 : you are attacked

		try {
			p.battleStack.push( CardFactory.createCard( CardType.Accident ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( p.canPlay( opponents ) );
		p.battleStack.pop();
		p.handStack.pop();
		
		// -- > Case 4 : you are slowed
	
		try {
			p.distanceStack.push( CardFactory.createCard( CardType.SpeedLimit ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		if ( type == CardType.Distance25 || type == CardType.Distance50 ) {
			assertTrue( p.canPlay( opponents ) );
		} else {
			assertFalse( p.canPlay( opponents ) );
		}
		
		p.handStack.pop();
		p.distanceStack.pop();
		
	}

	@Test
	public void testCanPlayEndOfLimit() {
		canPlayRemedy( CardType.EndOfLimit, CardType.SpeedLimit );
	}
	
	@Test
	public void testCanPlayGasoline() {
		canPlayRemedy( CardType.Gasoline, CardType.OutOfGas );
	}
	
	@Test
	public void testCanPlayGoRoll() {
		canPlayRemedy( CardType.GoRoll, CardType.Stop );
	}

	@Test
	public void testCanPlayRepairs() {
		canPlayRemedy( CardType.Repairs, CardType.Accident );
	}
	
	@Test
	public void testCanPlaySpareTire() {
		canPlayRemedy( CardType.SpareTire, CardType.FlatTire );
	}
	
	private void canPlayRemedy( CardType type, CardType opposite ) {
		// -- > Case 1 : initial GoRoll is not played
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}

		if ( type == CardType.GoRoll ) {
			assertTrue( p.canPlay( opponents ) );
		} else {
			assertFalse( p.canPlay( opponents ) );
		}
		
		p.handStack.pop();
		
		// -- > Set context : add initial GoRoll
		
		try {
			p.battleStack.push( CardFactory.createCard( CardType.GoRoll ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		// -- > Case 2 : attacked/slowed and good family
		
		PlayerStack destination;
		destination = type == CardType.SpeedLimit ? p.distanceStack : p .battleStack;
		
		try {
			destination.push( CardFactory.createCard( opposite ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertTrue( p.canPlay( opponents ) );
		p.battleStack.pop();
		p.handStack.pop();
		
		// -- > Case 3 : not attacked
		
		try {
			p.handStack.push( CardFactory.createCard( type ) );
		} catch ( IllegalCardTypeException e ) {
			e.printStackTrace();
		}
		
		assertFalse( p.canPlay( opponents ) );
		p.handStack.pop();
	}
}
