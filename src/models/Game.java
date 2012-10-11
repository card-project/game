package models;

import java.util.Collections;
import java.util.Scanner;

import models.cards.distance.Distance100;
import models.cards.distance.Distance200;
import models.cards.distance.Distance25;
import models.cards.distance.Distance50;
import models.cards.distance.Distance75;
import models.cards.hazards.Accident;
import models.cards.hazards.OutOfGas;
import models.cards.hazards.FlatTire;
import models.cards.hazards.Stop;
import models.cards.hazards.SpeedLimit;
import models.cards.remedies.EndOfLimit;
import models.cards.remedies.GoRoll;
import models.cards.remedies.Gasoline;
import models.cards.remedies.Repairs;
import models.cards.remedies.SpareTire;
import models.cards.safeties.DrivingAce;
import models.cards.safeties.ExtraTank;
import models.cards.safeties.RightOfWay;
import models.cards.safeties.PunctureProof;
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

	public static final Integer MIN_HUMAN_PLAYERS = 0;
	public static final Integer MIN_PLAYERS = 2;
	public static final Integer MAX_PLAYERS = 6;
	public static final Integer MIN_DISTANCE_GOAL = 700;
	public static final Integer MAX_DISTANCE_GOAL = 1000;
	public static final Integer MIN_AI_LEVEL = 1;
	public static final Integer MAX_AI_LEVEL = 3;
	public static final Integer MIN_HAND_CARDS = 0;
	public static final Integer MAX_HAND_CARDS = 4;

	private Integer goal;
	public Integer nbPlayers;
	private Player[] players;
	private Turn[] turns;
	private DiscardStack discardStack;
	private DeckStack deckStack;

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

	public void setNbPlayers( Integer numberOfPlayers )
			throws PlayersNumberException {
		if ( numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS ) {
			throw new PlayersNumberException(
					"Players number must be included between 2 and 6." );
		} else {
			this.nbPlayers = numberOfPlayers;
			this.players = new Player[nbPlayers];
		}
	}

	public void setHumanPlayers( Integer numberOfHumanPlayers )
			throws PlayersNumberException, TooManyHumanPlayersException {
		if ( numberOfHumanPlayers < MIN_HUMAN_PLAYERS
				|| numberOfHumanPlayers > MAX_PLAYERS ) {
			throw new PlayersNumberException(
					"Players number must be included between 0 and "
							+ nbPlayers + "." );
		} else if ( numberOfHumanPlayers > this.nbPlayers ) {
			throw new TooManyHumanPlayersException(
					"Number of human players must not exceed the number of players." );
		} else {
			for ( int i = 0; i < this.players.length; i++ ) {
				this.players[i] = ( i < numberOfHumanPlayers ) ? new HumanPlayer()
						: new AIPlayer();
			}
		}
	}

	public void setGoal( Integer distanceGoal ) throws DistanceGoalException {
		if ( distanceGoal < MIN_DISTANCE_GOAL
				|| distanceGoal > MAX_DISTANCE_GOAL || distanceGoal % 25 != 0 ) {
			throw new DistanceGoalException(
					"Distance goal must be included between 700 and 1000km and a multiple of 25." );
		} else {
			this.goal = distanceGoal;
		}
	}

	public void setPlayerName( Player player, String chosenAlias )
			throws AliasAlreadyChosenException {
		for ( int i = 0; i < this.players.length; i++ ) {
			if ( chosenAlias.equals( this.players[i].getAlias() ) ) {
				throw new AliasAlreadyChosenException(
						"Alias already chosen by another player." );
			}
		}

		for ( int i = 0; i < this.players.length; i++ ) {
			if ( this.players[i].equals( player ) ) {
				this.players[i].setAlias( chosenAlias );
			}
		}
	}

	public void setAIPlayersAlias() {
		Boolean aliasIsAlreadyChosen;
		String[] aiAliases = { "ann", "bob", "celo", "dan", "emile", "fanny" };
		for ( int i = 0; i < players.length; i++ ) {
			if ( players[i] instanceof AIPlayer ) {
				do {
					try {
						aliasIsAlreadyChosen = false;
						this.setPlayerName(
								players[i],
								aiAliases[(int) ( Math.random() * aiAliases.length )] );
					} catch ( AliasAlreadyChosenException e ) {
						aliasIsAlreadyChosen = true;
					}
				} while ( aliasIsAlreadyChosen );
			}
		}
	}

	public void setAIPlayerLevel( AIPlayer p, Integer chosenLevel )
			throws AILevelOutOfBoundsException {
		if ( chosenLevel < MIN_AI_LEVEL || chosenLevel > MAX_AI_LEVEL ) {
			throw new AILevelOutOfBoundsException(
					"Level chosen must be included between 1 and 3." );
		} else {
			p.setLevel( chosenLevel );
		}
	}

	public void prepare() {
		this.deckStack = DeckStack.getInstance();
		this.discardStack = DiscardStack.getInstance();

		/* ADD ATTACK CARDS */

		for ( int i = 0; i < Accident.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Accident() );
		}

		for ( int i = 0; i < OutOfGas.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new OutOfGas() );
		}

		for ( int i = 0; i < FlatTire.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new FlatTire() );
		}

		for ( int i = 0; i < Stop.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Stop() );
		}

		for ( int i = 0; i < SpeedLimit.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new SpeedLimit() );
		}

		/* ADD COUNTER CARDS */

		for ( int i = 0; i < Repairs.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Repairs() );
		}

		for ( int i = 0; i < Gasoline.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Gasoline() );
		}

		for ( int i = 0; i < SpareTire.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new SpareTire() );
		}

		for ( int i = 0; i < GoRoll.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new GoRoll() );
		}

		for ( int i = 0; i < EndOfLimit.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new EndOfLimit() );
		}

		/* ADD SPECIALS CARDS */

		this.deckStack.add( RightOfWay.getInstance() );
		this.deckStack.add( DrivingAce.getInstance() );
		this.deckStack.add( ExtraTank.getInstance() );
		this.deckStack.add( PunctureProof.getInstance() );

		/* ADD MILESTONES CARDS */

		for ( int i = 0; i < Distance25.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Distance25() );
		}

		for ( int i = 0; i < Distance50.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Distance50() );
		}

		for ( int i = 0; i < Distance75.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Distance75() );
		}

		for ( int i = 0; i < Distance100.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Distance100() );
		}

		for ( int i = 0; i < Distance200.MAX_INSTANCES; i++ ) {
			this.deckStack.add( new Distance200() );
		}

		Collections.shuffle( this.deckStack );
	}

	public void preparePlayersHand() {
		// Draw initial hand (4 cards) for each player.
		for ( Player p : players ) {
			for ( int i = 0; i < MAX_HAND_CARDS; i++ ) {
				p.draw( this.deckStack );
			}
		}
	}

	public Integer getFirstPlayerIndex() {
		return (int) ( Math.random() * nbPlayers );
	}
	
	public static void main( String[] args ) {
		try ( Scanner test = new Scanner( System.in ) ) {
			Integer t = test.nextInt();
			System.out.println( t );
			test.close();
		}
		
		try ( Scanner test = new Scanner( System.in ) ) {
			String t = test.nextLine();
			System.out.println( t );
			test.close();
		}
		
		try ( Scanner test = new Scanner( System.in ) ) {
			String t = test.nextLine();
			System.out.println( t );
			test.close();
		}
			
	}
}
