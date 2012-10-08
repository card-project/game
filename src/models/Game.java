package models;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import models.cards.AttackCard;
import models.cards.Card;
import models.cards.CounterCard;
import models.cards.MilestoneCard;
import models.cards.SpecialCard;
import models.exceptions.AILevelOutOfBoundsException;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.DistanceGoalException;
import models.exceptions.PlayersNumberException;
import models.exceptions.TooManyHumanPlayersException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.DeckStack;
import models.stacks.DiscardStack;

public class Game {

	private static final Integer MIN_HUMAN_PLAYERS = 0;
	private static final Integer MIN_PLAYERS = 2;
	private static final Integer MAX_PLAYERS = 6;
	private static final Integer MIN_DISTANCE_GOAL = 700;
	private static final Integer MAX_DISTANCE_GOAL = 1000;
	private static final Integer MIN_AI_LEVEL = 1;
	private static final Integer MAX_AI_LEVEL = 3;
	private static final Integer MAX_HAND_CARDS = 4;
	
	private Integer goal;
	private Integer nbPlayers;
	private Player[] players;
	private Turn[] turns;
	private DiscardStack discardStack;
	private DeckStack deckStack;
	
	public Player[] getPlayers ( ) {
		return this.players;
	}
	
	public void setNbPlayers ( Integer numberOfPlayers ) throws PlayersNumberException {
		if ( numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS ) {
			throw new PlayersNumberException ( "Players number must be included between 2 and 6." );
		} else {
			this.nbPlayers = numberOfPlayers;
			this.players = new Player[nbPlayers];
		}
	}

	public void setHumanPlayers ( Integer numberOfHumanPlayers ) throws PlayersNumberException, TooManyHumanPlayersException {
		if ( numberOfHumanPlayers < MIN_HUMAN_PLAYERS || numberOfHumanPlayers > MAX_PLAYERS ) {
			throw new PlayersNumberException ( "Players number must be included between 0 and " + nbPlayers + "." );
		} else if ( numberOfHumanPlayers > this.nbPlayers ) {
			throw new TooManyHumanPlayersException ( "Number of human players must not exceed the number of players." );
		} else {
			for (int i = 0 ; i < this.players.length; i++) {
				this.players[i] = ( i < numberOfHumanPlayers ) ? new HumanPlayer() : new AIPlayer();
			}
		}
	}
	
	public void setGoal ( Integer distanceGoal ) throws DistanceGoalException {
		if ( distanceGoal < MIN_DISTANCE_GOAL || distanceGoal > MAX_DISTANCE_GOAL || distanceGoal%25 != 0) {
			throw new DistanceGoalException ( "Distance goal must be included between 700 and 1000km and a multiple of 25." );
		} else {
			this.goal = distanceGoal;
		}
	}
	
	public void setPlayerName ( Player player, String chosenAlias ) throws AliasAlreadyChosenException {
		for (int i = 0; i < this.players.length; i++) {
			if ( chosenAlias.equals( this.players[i].getAlias() ) ) {
				throw new AliasAlreadyChosenException( "Alias already chosen by another player." );
			}
		}
		
		for (int i = 0; i < this.players.length; i++) {
			if ( this.players[i].equals( player ) ) {
				this.players[i].setAlias( chosenAlias );
			}
		}
	}

	public void setAIPlayersAlias() {
		Boolean aliasIsAlreadyChosen;
		String [] aiAliases = { "ann","bob","celo","dan","emile","fanny" };
		for (int i = 0 ; i < players.length; i++ ) {
			if ( players[i] instanceof AIPlayer ) {
				do {
					try {
						aliasIsAlreadyChosen = false;
						this.setPlayerName( players[i], aiAliases[( int ) ( Math.random() * aiAliases.length )] );
					} catch ( AliasAlreadyChosenException e ) {
						aliasIsAlreadyChosen = true;
					}
				} while ( aliasIsAlreadyChosen );
			}
		}
	}

	public void setAIPlayerLevel( AIPlayer p, Integer chosenLevel ) throws AILevelOutOfBoundsException {
		if ( chosenLevel < MIN_AI_LEVEL || chosenLevel > MAX_AI_LEVEL ) {
			throw new AILevelOutOfBoundsException( "Level chosen must be included between 1 and 3." );
		} else {
			p.setLevel( chosenLevel );
		}
	}

	public void prepare() {
		this.deckStack = new DeckStack();
		this.discardStack = new DiscardStack();
		
		for ( int i = 0; i < AttackCard.MAX_INSTANCES ; i++ ) {
			this.deckStack.add( new AttackCard() );		
		}
		
		for ( int i = 0; i < CounterCard.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new CounterCard() );
		}
		
		for ( int i = 0; i < MilestoneCard.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new MilestoneCard() );
		}
		
		for ( int i = 0; i < SpecialCard.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new SpecialCard() );
		}
		
		Collections.shuffle( this.deckStack );
	}

	public void preparePlayers() {
		// Draw initial hand (4 cards) to each player.
		for( Player p : players ) {
			for (int i = 0; i < MAX_HAND_CARDS; i++) {
				p.draw( this.deckStack.getFirst() );
				this.deckStack.removeFirst();
			}
		}
	}
}
