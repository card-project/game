package views.terminal;

import java.util.InputMismatchException;
import java.util.Scanner;

import models.Game;
import models.exceptions.AILevelOutOfBoundsException;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.DiscardChoiceOutOfBoundsException;
import models.exceptions.DistanceGoalException;
import models.exceptions.PlayersNumberException;
import models.exceptions.TooManyHumanPlayersException;
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
			} catch (InputMismatchException e) {
				userChoiceIsIncorrect = true;
				System.out.println( "Please enter a number." );
			} catch (PlayersNumberException e) {
				System.out.println( e.getMessage() );
				userChoiceIsIncorrect = true;
			}
		} while (userChoiceIsIncorrect);

		/* HUMAN PLAYERS NUMBER */

		do {
			System.out.print( "How many human players among these ones ? " );
			userChoiceIsIncorrect = false;
			try {
				g.setHumanPlayers( new Scanner( System.in ).nextInt() );
			} catch (InputMismatchException e) {
				userChoiceIsIncorrect = true;
				System.out.println( "Please enter a number." );
			} catch (PlayersNumberException e) {
				System.out.println( e.getMessage() );
				userChoiceIsIncorrect = true;
			} catch (TooManyHumanPlayersException e) {
				System.out.println( e.getMessage() );
				userChoiceIsIncorrect = true;
			}
		} while (userChoiceIsIncorrect);

		/* DISTANCE GOAL */

		do {
			System.out.print( "How far would you like to go ? " );
			userChoiceIsIncorrect = false;
			try {
				g.setGoal( new Scanner( System.in ).nextInt() );
			} catch (InputMismatchException e) {
				userChoiceIsIncorrect = true;
				System.out.println( "Please enter a number." );
			} catch (DistanceGoalException e) {
				userChoiceIsIncorrect = true;
				System.out.println( e.getMessage() );
			}
		} while (userChoiceIsIncorrect);

		/* HUMAN PLAYERS ALIAS */

		for (int i = 0, j = i + 1; i < g.getPlayers().length; i++, j++) {
			if (g.getPlayers()[i] instanceof HumanPlayer) {
				do {
					System.out.print( "What is Player " + j + " alias ? " );
					userChoiceIsIncorrect = false;
					try {
						g.setPlayerName( g.getPlayers()[i],
								new Scanner ( System.in ).nextLine() );
					} catch (AliasAlreadyChosenException e) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while (userChoiceIsIncorrect);
			}
		}

		/* AI PLAYERS ALIAS */

		g.setAIPlayersAlias();

		/* AI PLAYERS LEVEL */

		for (Player p : g.getPlayers()) {
			if (p instanceof AIPlayer) {
				do {
					System.out.print( "What is " + p.getAlias()
							+ " (AI) level ? " );
					userChoiceIsIncorrect = false;
					try {
						g.setAIPlayerLevel( (AIPlayer) p, new Scanner( System.in ).nextInt() );
					} catch (InputMismatchException e) {
						userChoiceIsIncorrect = true;
						System.out.println( "Please enter a number." );
					} catch (AILevelOutOfBoundsException e) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while (userChoiceIsIncorrect);
			}
		}

		/* GAME PREPARATION */

		g.prepare();
		System.out.println( "Shuffling cards..." );
		
		/* PLAYERS HAND */

		g.preparePlayersHand();
		System.out.println( "Distributing cards to players..." );
	}

	private static void play( Game g ) {
		
		/* DECIDING FIRST PLAYER */
	
		Integer firstPlayerIndex = g.getFirstPlayerIndex();
		System.out.println( "Deciding who will begin..." );
		System.out.println( g.getPlayers()[firstPlayerIndex].getAlias()
				+ " begins." );
		
		/* GAME PLAYING */
		
		Boolean gameIsOver = false;
		Integer currentPlayerIndex = firstPlayerIndex;
		do {
			Player currentPlayer = g.getPlayers()[currentPlayerIndex];
			
			System.out.println( currentPlayer.toString() 
					+ ( !g.getDiscardStack().isEmpty() ?  '\n' + "DISCARD STACK : " + g.getDiscardStack() : "" ) );
			
			// STEP 1 : Draw a card (discard or deck stack)
			
			System.out.println( "-> DRAWING STEP" );
			if ( g.getDiscardStack().isEmpty() ) {
				System.out.println( "Deck stack automatically chosen (discard stack is empty)." );
				currentPlayer.draw( g.getDeckStack() );
			} else {
				Boolean userChoiceIsIncorrect = false;
				do {
					System.out.println( "[D]eck or [d]iscard ?" );
					// FIXME
					String userChoice = new Scanner( System.in ).nextLine();
					if ( userChoice.isEmpty() || userChoice.equals( "D" ) ) {
						currentPlayer.draw( g.getDeckStack() );
					} else if ( userChoice.equals( "d") ) {
						currentPlayer.draw( g.getDiscardStack() );
					} else {
						userChoiceIsIncorrect = true;
					}
				} while ( userChoiceIsIncorrect );
			}
			
			// STEP 2 : Play a card 
			System.out.println( "-> PLAYING STEP" );
			
			// TODO Handle playing routine
			
			// STEP 3 : Discard a card
			if ( currentPlayer.getHandStack().size() > Game.MAX_HAND_CARDS ) {
				System.out.println( "-> DISCARDING STEP" );
				Boolean userChoiceIsIncorrect = null;
				do {
					System.out.println( "Too many cards in hand, you must discard one." + '\n' 
							+ "Which card do you discard ? Choose its index (from 1 to 5)." + '\n' 
							+ currentPlayer.getHandStack() + '\n' );
					userChoiceIsIncorrect = false;
					try {
						currentPlayer.discard( new Scanner( System.in ).nextInt(), g.getDiscardStack() );
					} catch ( InputMismatchException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( "Please enter a number." );
					} catch ( DiscardChoiceOutOfBoundsException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while (userChoiceIsIncorrect);
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
}
