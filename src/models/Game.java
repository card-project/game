package models;

import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.IllegalDistanceException;
import models.exceptions.IllegalHumanPlayerNumberException;
import models.exceptions.IllegalPlayerNumberException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.DeckStack;
import models.stacks.game.DiscardStack;
import models.stacks.player.HandStack;

/**
 * @author Simon RENOULT
 * @vresion 0.2.2
 */
public class Game {

	// ------------ CONSTANTS ------------ //

	private static final int MIN_PLAYERS_NB = 2;
	private static final int MAX_PLAYERS_NB = 6;
	private static final int MIN_HUMAN_PLAYERS_NB = 1;
	private static final int MIN_DISTANCE_GOAL = 700;
	private static final int MAX_DISTANCE_GOAL = 1000;
	
	// ------------ ATTRIBUTES ------------ // 
	
	private Integer goal;
	private Player[] players;
	private DiscardStack discardStack;
	private DeckStack deckStack;

	// ------------ CONSTRUCTORS ------------ // 

	public Game() {
		this.deckStack = DeckStack.getInstance();
		this.discardStack = DiscardStack.getInstance();
	}
	
	// ------------ METHODS ------------ // 
	
	public void distributeCardsToPlayers() {
		// Draw initial hand (4 cards) for each player.
		for ( Player p : players ) {
			for ( int i = 0; i < HandStack.MAX_CARD_NB; i++ ) {
				p.draw( this.deckStack );
			}
		}
		
	}
	
	public String toString() {
		String players = "";
		
		for ( Player p : this.players ){
			players += p.toString();
		}
		
		return "Goal : " + this.goal + '\n' +
				"Players : " + players  + '\n' +
				"DiscardStack " + this.discardStack + '\n' +
				"DeckStack" + this.deckStack;
	}
	
	// ------------ SETTERS ------------ // 

	public void setPlayersNumber( int playersNumber ) throws IllegalPlayerNumberException {
		if ( playersNumber < MIN_PLAYERS_NB || playersNumber > MAX_PLAYERS_NB ) {
			throw new IllegalPlayerNumberException();
		} else {
			this.players = new Player [playersNumber];
		}
	}

	public void setHumanPlayers( int humanPlayersNumber ) throws IllegalHumanPlayerNumberException {
		if ( humanPlayersNumber < MIN_HUMAN_PLAYERS_NB || humanPlayersNumber > this.players.length ) {
			throw new IllegalHumanPlayerNumberException();
		} else {
			for ( int i = 0; i < this.players.length; i++ ) {
				this.players[i] = ( i < humanPlayersNumber ) ? new HumanPlayer() : new AIPlayer();
			}
		}
	}	
	
	public void setDistanceGoal( int distanceGoal ) throws IllegalDistanceException {
		if ( ( distanceGoal < MIN_DISTANCE_GOAL || distanceGoal > MAX_DISTANCE_GOAL || distanceGoal%25 != 0 ) ) {
			throw new IllegalDistanceException();
		} else {
			this.goal = distanceGoal;
		}
	}
	

	public void setPlayerAlias( Player player, String askedPlayerAlias ) throws AliasAlreadyChosenException  {
		for ( int i = 0; i < this.players.length; i++ ) {
			if ( askedPlayerAlias.equals( this.players[i].getAlias() ) ) {
				throw new AliasAlreadyChosenException( "Alias already chosen." );
			}
		}
		
		player.setAlias( askedPlayerAlias );
	}
	
	public void setAIPlayersAlias() {
		String[] aiAliases = { "ann", "bob", "celo", "dan", "emile", "fanny" };
		
		for ( int i = 0; i < this.players.length; i++ ) {
			if ( players[i] instanceof AIPlayer ) {
				Boolean aliasIsAlreadyChosen;
				do {
					try {
						aliasIsAlreadyChosen = false;
						this.setPlayerAlias( players[i], aiAliases[(int) ( Math.random() * aiAliases.length )] );
					} catch ( AliasAlreadyChosenException e ) {
						aliasIsAlreadyChosen = true;
					}
				} while ( aliasIsAlreadyChosen );
			}
		}
	}
	
	public void setAIPlayerLevel( AIPlayer p, Integer chosenLevel ) {
		p.setLevel( chosenLevel );
	}
	
	// ------------ GETTERS------------ // 
	
	public Player[] getPlayers() {
		return this.players;
	}

	public DeckStack getDeckStack() {
		return this.deckStack;
	}

	public DiscardStack getDiscardStack() {
		return this.discardStack;
	}

	public Integer getGoal() {
		return this.goal;
	}

}
