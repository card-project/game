package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.IllegalDistanceException;
import models.exceptions.IllegalHumanPlayerNumberException;
import models.exceptions.IllegalPlayerNumberException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;

import org.junit.Before;
import org.junit.Test;

public class GameTest {

	// ------------ CONSTANTS ------------ //

	private static final int DEFAULT_PLAYER_NB = 4;
	private static final int DEFAULT_HUMAN_PLAYER_NB = 2;
	private static final int DEFAULT_DISTANCE_GOAL = 700;
	private static final String[] DEFAULT_HUMAN_PLAYER_ALIAS = { "simon",
			"adrien" };

	// ------------ ATTRIBUTES ------------ //

	private static Game testGame;

	// ------------ METHODS ------------ //

	@Before public void init() {

		testGame = new Game();

		try {
			testGame.setPlayersNumber( DEFAULT_PLAYER_NB );
			testGame.setHumanPlayersNumber( DEFAULT_HUMAN_PLAYER_NB );

			testGame.setDistanceGoal( DEFAULT_DISTANCE_GOAL );

			int counter = 0;
			for ( Player p : testGame.getPlayers() ) {
				if ( p instanceof HumanPlayer ) {
					testGame.setPlayerAlias( p,
							DEFAULT_HUMAN_PLAYER_ALIAS[counter] );
					counter++;
				}
			}

			testGame.setAIPlayersAlias();

		} catch ( Exception e ) {
			System.out
					.println( "SHOULD NOT HAPPEN ! Something went wrong on default game setting." );
			e.printStackTrace();
			fail();
		}
	}

	// TODO
	@Test public void testDistributeCardsToPlayers() { }

	@Test public void testSetPlayersNumber() {
		setPlayersNumber( Game.MIN_PLAYERS_NB - 1 );
		setPlayersNumber( Game.MIN_PLAYERS_NB );
		setPlayersNumber( Game.MAX_PLAYERS_NB );
		setPlayersNumber( Game.MAX_PLAYERS_NB + 1 );
	}

	private void setPlayersNumber( int playerNumber ) {
		Throwable caught = null;

		try {
			testGame.setPlayersNumber( playerNumber );
		} catch ( IllegalPlayerNumberException e ) {
			caught = e;
		}

		switch ( playerNumber ) {
		case Game.MIN_PLAYERS_NB - 1:
		case Game.MAX_PLAYERS_NB + 1:
			assertNotNull( caught );
			break;
		case Game.MIN_PLAYERS_NB:
		case Game.MAX_PLAYERS_NB:
			assertNull( caught );
			break;
		default:
			fail( "Wrong playerNumber choice." );
		}
	}

	@Test public void testSetHumanPlayers() {
		setHumanPlayersNumber( Game.MIN_HUMAN_PLAYERS_NB - 1 );
		setHumanPlayersNumber( Game.MIN_HUMAN_PLAYERS_NB );
		setHumanPlayersNumber( testGame.getPlayers().length );
		setHumanPlayersNumber( testGame.getPlayers().length + 1 );
	}
 
	private void setHumanPlayersNumber( int humanPlayerNumber ) {
		Throwable caught = null;

		try {
			testGame.setHumanPlayersNumber( humanPlayerNumber );
		} catch ( IllegalHumanPlayerNumberException e ) {
			caught = e;
		}

		if ( humanPlayerNumber == Game.MIN_HUMAN_PLAYERS_NB - 1
				|| humanPlayerNumber == testGame.getPlayers().length + 1 ) {
			assertNotNull( caught );
		} else if ( humanPlayerNumber == Game.MIN_HUMAN_PLAYERS_NB
				|| humanPlayerNumber == testGame.getPlayers().length ) {
			assertNull( caught );
		} else {
			fail( "Wrong human player number." );
		}
	}

	@Test public void testSetDistanceGoal() {
		setDistanceGoal( Game.MIN_DISTANCE_GOAL - 1 );
		setDistanceGoal( Game.MIN_DISTANCE_GOAL );
		setDistanceGoal( Game.MAX_DISTANCE_GOAL );
		setDistanceGoal( Game.MAX_DISTANCE_GOAL + 1 );
	}

	private void setDistanceGoal( int distanceGoal ) {
		Throwable caught = null;

		try {
			testGame.setDistanceGoal( distanceGoal );
		} catch ( IllegalDistanceException e ) {
			caught = e;
		}

		switch ( distanceGoal ) {
		case Game.MIN_DISTANCE_GOAL - 1:
		case Game.MAX_DISTANCE_GOAL + 1:
			assertNotNull( caught );
			break;
		case Game.MIN_DISTANCE_GOAL:
		case Game.MAX_DISTANCE_GOAL:
			assertNull( caught );
			break;
		default:
			fail( "Wrong distance goal value." );
		}
	}

	@Test public void testSetPlayerAlias() {
		// Reset players alias
		for ( Player p : testGame.getPlayers() ) {
			p.setAlias( null );
		}

		Throwable caught = null;

		try {
			testGame.setPlayerAlias( testGame.getPlayers()[0],
					DEFAULT_HUMAN_PLAYER_ALIAS[0] );
		} catch ( AliasAlreadyChosenException e ) {
			caught = e;
		}

		assertNull( caught );
		caught = null;

		try {
			testGame.setPlayerAlias( testGame.getPlayers()[1],
					DEFAULT_HUMAN_PLAYER_ALIAS[1] );
		} catch ( AliasAlreadyChosenException e ) {
			caught = e;
		}

		assertNull( caught );
		caught = null;

		try {
			testGame.setPlayerAlias( testGame.getPlayers()[1],
					DEFAULT_HUMAN_PLAYER_ALIAS[0] );
		} catch ( AliasAlreadyChosenException e ) {
			caught = e;
		}

		assertNotNull( caught );
	}

	// Always true
	@Test public void testSetAIPlayersAlias() {
		assertNull( null );
	}

	@Test public void testGetPlayers() {
		assertNotNull( testGame.getPlayers() );
	}

	@Test public void testGetAIPlayers() {
		for ( Player p : testGame.getAIPlayers() ) {
			assertTrue( p instanceof AIPlayer );
		}
	}

	@Test public void testGetHumanPlayers() {
		for ( Player p : testGame.getHumanPlayers() ) {
			assertTrue( p instanceof HumanPlayer );
		}
	}

	@Test public void testGetDeckStack() {
		assertNotNull( testGame.getDeckStack() );
		assertEquals( testGame.getDeckStack(), DeckStack.getInstance() );
	}

	@Test public void testGetDiscardStack() {
		assertNotNull( testGame.getDiscardStack() );
		assertEquals( testGame.getDiscardStack(), DiscardStack.getInstance() );
	}

	@Test public void testGetGoal() {
		assertNotNull( testGame.getGoal() );
	}

}
