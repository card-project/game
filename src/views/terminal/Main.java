package views.terminal;

import java.util.Scanner;


import models.Game;
import models.exceptions.AILevelOutOfBoundsException;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.DistanceGoalException;
import models.exceptions.PlayersNumberException;
import models.exceptions.TooManyHumanPlayersException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;


public class Main {
	public static void main(String[] args) {

		Boolean userChoiceIsIncorrect = null;
		Game currentGame = new Game ( );
		Scanner input = new Scanner( System.in );
		
		
		/* TOTAL PLAYERS NUMBER */
		
		do {
			System.out.print( "How many players ? " );
			userChoiceIsIncorrect = false;
			try {
				currentGame.setNbPlayers( Integer.valueOf( input.nextLine() ) );
			} catch ( NumberFormatException e ) {
				userChoiceIsIncorrect  = true;
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
				currentGame.setHumanPlayers( Integer.valueOf( input.nextLine() ) );
			} catch ( NumberFormatException e ) {
				userChoiceIsIncorrect  = true;
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
				currentGame.setGoal( Integer.valueOf( input.nextLine() ) );
			} catch ( NumberFormatException e ) {
				userChoiceIsIncorrect  = true;
				System.out.println( "Please enter a number." );
			} catch ( DistanceGoalException e ) {
				userChoiceIsIncorrect = true; 
				System.out.println( e.getMessage() );
			}
		} while ( userChoiceIsIncorrect );
		
		/* HUMAN PLAYERS ALIAS */
		
		for (int i = 0, j = i + 1; i < currentGame.getPlayers().length; i++, j++) {
			if ( currentGame.getPlayers()[i] instanceof HumanPlayer ) {
				do {
					System.out.print( "What is Player " + j + " alias ? " );
					userChoiceIsIncorrect = false;
					try {
						currentGame.setPlayerName( currentGame.getPlayers()[i], input.nextLine() );
					} catch ( AliasAlreadyChosenException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while ( userChoiceIsIncorrect );
			}
		}
		
		/* AI PLAYERS ALIAS */
		
		currentGame.setAIPlayersAlias();
		
		/* AI PLAYERS LEVEL */
		
		for ( Player p : currentGame.getPlayers() ) {
			if ( p instanceof AIPlayer ) {
				do {
					System.out.print( "What is " + p.getAlias() + " (AI) level ? " );
					userChoiceIsIncorrect = false;
					try {
						currentGame.setAIPlayerLevel( (AIPlayer) p, Integer.valueOf( input.nextLine() ) );
					} catch ( NumberFormatException e ) {
						userChoiceIsIncorrect  = true;
						System.out.println( "Please enter a number." );
					} catch ( AILevelOutOfBoundsException e ) {
						userChoiceIsIncorrect = true;
						System.out.println( e.getMessage() );
					}
				} while ( userChoiceIsIncorrect );
			}
		}
		
		/* GAME PREPARATION */

		System.out.println( "Shuffling cards..." );
		currentGame.prepare( );
		
		/* PLAYERS HAND */
		
		System.out.println( "Distributing cards to players..." );
		currentGame.preparePlayers( );
		
		for( Player p : currentGame.getPlayers() ) {
			System.out.println( p.getHandStack() );
		}
		
		input.close();
		
	}
}
