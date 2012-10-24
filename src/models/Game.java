package models;

import models.exceptions.AliasAlreadyChosenException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.DeckStack;
import models.stacks.DiscardStack;
import models.stacks.HandStack;

public class Game {

	// ------------ ATTRIBUTES ------------ // 
	
	private Integer goal;
	public Integer nbPlayers;
	private Player[] players;
	private Turn[] turns;
	private DiscardStack discardStack;
	private DeckStack deckStack;

	// ------------ CONSTRUCTORS ------------ // 

	public Game() {
		this.deckStack = DeckStack.getInstance();
		this.discardStack = DiscardStack.getInstance();
	}
	
	// ------------ METHODS ------------ // 
	
	// ------------ SETTERS ------------ // 

	public void setPlayersNumber( int playersNumber ) {
		this.nbPlayers = playersNumber;
		this.players = new Player [nbPlayers];
	}

	public void setHumanPlayers( int humanPlayersNumber ) {
		for ( int i = 0; i < this.players.length; i++ ) {
			this.players[i] = ( i < humanPlayersNumber ) ? new HumanPlayer() : new AIPlayer();
		}
	}	
	
	public void setDistanceGoal( int distanceGoal ) {
		this.goal = distanceGoal;
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
	
	public void preparePlayersHand() {
		// Draw initial hand (4 cards) for each player.
		for ( Player p : players ) {
			for ( int i = 0; i < HandStack.REGULAR_CARD_NB ; i++ ) {
				p.draw( this.deckStack );
			}
		}
	}
	
	// ------------ GETTERS------------ // 
	
	public int getNbPlayers() {
		return this.nbPlayers;
	}
	
	public Player[] getPlayers() {
		return this.players;
	}

	public Turn[] getTurns() {
		return this.turns;
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
