package views.terminal;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Game;
import models.cards.Card;
import models.cards.hazards.HazardCard;
import models.exceptions.AILevelOutOfBoundsException;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.DiscardChoiceOutOfBoundsException;
import models.exceptions.DistanceGoalException;
import models.exceptions.IllegalMoveException;
import models.exceptions.PlayedCardIndexOutOfBoundsException;
import models.exceptions.PlayersNumberException;
import models.exceptions.TooManyHumanPlayersException;
import models.moves.BasicMove;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;

public class Main {
	public static void main( String[] args ) {
		
		Game g = new Game();

		Main.setGameOptions( g );
		Main.play( g );
	}

	private static void setGameOptions( Game g ) {
		Boolean userChoiceIsIncorrect = null;

		/* TOTAL PLAYERS NUMBER */

		do {
			System.out.print( "How many players ? " );
			userChoiceIsIncorrect = false;
			try {
				g.setNbPlayers( new Scanner( System.in ).nextInt() );
			} catch ( InputMismatchException e ) {
				userChoiceIsIncorrect = true;
				System.out.println( "Please enter a number." );
			} catch ( PlayersNumberException e ) {
				System.out.println( e.getMessage() );
				userChoiceIsIncorrect = true;
			}
		} while ( userChoiceIsIncorrect );

		/* HUMAN PLAYERS NUMBER */

		do {
			System.out.print( "How many human players among these ones ? " );
			userChoiceIsIncorrect = false;
			try {
				g.setHumanPlayers( new Scanner( System.in ).nextInt() );
			} catch ( InputMismatchException e ) {
				userChoiceIsIncorrect = true;
				System.out.println( "Please enter a number." );
			} catch ( PlayersNumberException e ) {
				System.out.println( e.getMessage() );
				userChoiceIsIncorrect = true;
			} catch ( TooManyHumanPlayersException e ) {
				System.out.println( e.getMessage() );
				userChoiceIsIncorrect = true;
			}
		} while ( userChoiceIsIncorrect );

		/* DISTANCE GOAL */

		do {
			System.out.print( "How far would you like to go ? " );
			userChoiceIsIncorrect = false;
			try {
				g.setGoal( new Scanner( System.in ).nextInt() );
			} catch ( InputMismatchException e ) {
				userChoiceIsIncorrect = true;
				System.out.println( "Please enter a number." );
			} catch ( DistanceGoalException e ) {
				userChoiceIsIncorrect = true;
				System.out.println( e.getMessage() );
			}
		} while ( userChoiceIsIncorrect );

		/* HUMAN PLAYERS ALIAS */

		for ( int i = 0, j = i + 1; i < g.getPlayers().length; i++, j++ ) {
			if ( g.getPlayers()[i] instanceof HumanPlayer ) {
				do {
					System.out.print( "What is Player " + j + " alias ? " );
					userChoiceIsIncorrect = false;
					try {
						g.setPlayerName( g.getPlayers()[i], new Scanner(
								System.in ).nextLine() );
					} catch ( AliasAlreadyChosenException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while ( userChoiceIsIncorrect );
			}
		}

		/* AI PLAYERS ALIAS */

		g.setAIPlayersAlias();

		/* AI PLAYERS LEVEL */

		for ( Player p : g.getPlayers() ) {
			if ( p instanceof AIPlayer ) {
				do {
					System.out.print( "What is " + p.getAlias()
							+ " (AI) level ? " );
					userChoiceIsIncorrect = false;
					try {
						g.setAIPlayerLevel( (AIPlayer) p, new Scanner(
								System.in ).nextInt() );
					} catch ( InputMismatchException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( "Please enter a number." );
					} catch ( AILevelOutOfBoundsException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while ( userChoiceIsIncorrect );
			}
		}

		/* GAME PREPARATION */

		g.prepare();
		System.out.println( '\n' + "Shuffling cards..." );

		/* PLAYERS HAND */

		g.preparePlayersHand();
		System.out.println( "Distributing cards to players..." );
	}

	private static void play( Game g ) {

		/* DECIDING FIRST PLAYER */

		Integer firstPlayerIndex = g.getFirstPlayerIndex();
		System.out.println( "Deciding who will begin..." 
				+ g.getPlayers()[firstPlayerIndex].getAlias()
				+ " begins." );

		/* GAME PLAYING */

		Boolean gameIsOver = false;
		Integer currentPlayerIndex = firstPlayerIndex;
		do {
			Player currentPlayer = g.getPlayers()[currentPlayerIndex];

			if ( currentPlayer instanceof HumanPlayer ) {
				humanPlay( g, (HumanPlayer) currentPlayer );
			} else {
				AIPlay( g, (AIPlayer) currentPlayer );
			}

			// STEP 4 : Check if game is over
			if ( currentPlayer.getKilometers() == g.getGoal() || g.getDeckStack().isEmpty() ) {
				gameIsOver = true;
			} else {
				// STEP 5 : Switch to the next player
				if ( ++currentPlayerIndex > g.getPlayers().length - 1 ) {
					currentPlayerIndex = 0;
				}
			}
		} while ( !gameIsOver );
	}
	
	private static void humanPlay( Game g, HumanPlayer player ) {
		System.out.println( '\n' + player.toString() 
			+ ( !g.getDiscardStack().isEmpty() ? '\n' + "DISCARD STACK : " + g.getDiscardStack() : "" ) );

		// STEP 1 : Draw a card (discard or deck stack)
		
		System.out.println( "-> DRAWING STEP" );

		if ( g.getDiscardStack().isEmpty() ) {
			System.out.println( "Discard stack is empty, deck stack is chosen." );
			Card c = player.draw( g.getDeckStack() );
			System.out.println( "DRAWN : " + c );
		} else {
			Boolean drawingCardChoiceIsCorrect = true;
			do {
				System.out.println( "[D]eck or [d]iscard ?" );
				String userChoice = new Scanner( System.in ).nextLine();
				if ( userChoice.isEmpty() || userChoice.equals( "D" ) ) {
					player.draw( g.getDeckStack() );
				} else if ( userChoice.equals( "d" ) ) {
					player.draw( g.getDiscardStack() );
				} else {
					drawingCardChoiceIsCorrect = false;
				}
			} while ( !drawingCardChoiceIsCorrect );
		}

		// STEP 2 : Play a card
		System.out.println( "-> PLAYING STEP" );
		
		Boolean playingCardChoiceIsCorrect;
		BasicMove currentMove = new BasicMove( player );
		do {
			playingCardChoiceIsCorrect= true;
			Card chosenCard = null;
			
			// STEP 2.1 : Pick a card to play
			Boolean playingCardIndexIsCorrect;
			do {
				playingCardIndexIsCorrect = true;
				try {
					System.out.println( player.getHandStack() );
					System.out.println( "Which card do you play ? Choose its index (from 1 to 5)." );
					int chosenCardIndex = Integer.valueOf( new Scanner ( System.in ).nextLine() );
					chosenCard = player.pickCardToPlay( chosenCardIndex );
				} catch ( PlayedCardIndexOutOfBoundsException e ) {
					playingCardIndexIsCorrect = false;
					System.out.println( e.getMessage() );
				}
			} while ( ! playingCardIndexIsCorrect );
			
			// STEP 2.1.1 : if the card is an hazard, choose its target
			if ( chosenCard instanceof HazardCard ) {
				Boolean targetChoiceIsCorrect;
				do {
					targetChoiceIsCorrect = true;
					System.out.println( "Who do you play this card on ? Choose his index." );
					for ( int i = 0; i < g.getPlayers().length; i++ ) {
						System.out.println( i + 1 + " - " + g.getPlayers()[i].getAlias() );
					}
					int targetPlayerIndex = Integer.valueOf ( new Scanner ( System.in ).nextLine() );
					if ( targetPlayerIndex < 0 || targetPlayerIndex > g.getPlayers().length - 1 ) {
						targetChoiceIsCorrect = false;
					} else {
						currentMove.setTarget( g.getPlayers()[ targetPlayerIndex - 1] );
					}
				} while ( ! targetChoiceIsCorrect );
			}
			
			// STEP 2.2 : Check whether the move is allowed
			try {
				currentMove.setCardToPlay( chosenCard );
			} catch ( IllegalMoveException e ) {
				playingCardChoiceIsCorrect = false;
				System.out.println( "Impossible move : " + e.getMessage() );
			}
		} while ( !playingCardChoiceIsCorrect );
		
		// STEP 2.3 : Play the card
		player.play( currentMove );
		
		// STEP 3 : Discard a card
		
		System.out.println( "-> DISCARDING STEP" );

		if ( player.getHandStack().size() > Game.MAX_HAND_CARDS ) {
			Boolean discardingCardChoiceIsCorrect;
			do {
				System.out.println( "Too many cards in hand, you must discard one."
					+ "\n Which card do you discard ? Choose its index (from 1 to 5)."
					+ '\n' + player.getHandStack() + '\n' );
				discardingCardChoiceIsCorrect = true;
				try {
					player.discard( new Scanner( System.in ).nextInt(), g.getDiscardStack() );
				} catch ( InputMismatchException e ) {
					discardingCardChoiceIsCorrect = false;
					System.out.println( "Please enter a number." );
				} catch ( DiscardChoiceOutOfBoundsException e ) {
					discardingCardChoiceIsCorrect = true;
					System.out.println( e.getMessage() );
				}
			} while ( !discardingCardChoiceIsCorrect );
		}
	}
	
	private static void AIPlay ( Game g, AIPlayer p ) {
		// STEP 1 : Draw a card
		// STEP 2 : Play a card
		// STEP 3 : Discard a card
	}
}
