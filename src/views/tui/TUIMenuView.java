package views.tui;

import java.util.Scanner;

import models.players.AIPlayer;

/**
 * Menu view of the TUI (Textual User Interface) view classes.
 * 
 * It represents the view of the game menu and allows the user
 * to set the game options through its methods. <br />
 * It is the only class which handles the menu standard output.
 *   
 * @author Simon RENOULT
 * @version 1.1
 */
public class TUIMenuView extends TUIView {
	
	// ------------ ATTRIBUTES ------------ //
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build a TUIMenuView object and initialize the scanner 
	 * attribute on the standard input stream <em>System.in</em>. 
	 */
	public TUIMenuView() {

	}
	
	// ------------ METHODS ------------ //
	
	/**
	 * Ask the user the number of players.
	 * 
	 * @return The number of players as an integer.
	 */
	public int askGlobalPlayersNumber() {
		System.out.print( "How many players ? " );
		return Integer.valueOf( input.nextLine() );
	}
	
	/**
	 * Ask the user the number of human players.
	 * 
	 * @return The number of human players as an integer.
	 */
	public int askHumanPlayersNumber() {
		System.out.print( "How many human players ? " );
		return Integer.valueOf( input.nextLine() );
	}
	
	/**
	 * Ask the user distance goal.
	 * 
	 * @return The number goal distance as an integer.
	 */
	public int askDistanceGoal() {
		System.out.print( "How far do you want to go ? " );
		return Integer.valueOf( input.nextLine() );
	}
	
	/**
	 * Ask a player alias.
	 * 
	 * @return The chosen alias as an integer.
	 */
	public String askPlayerAlias( int playerIndex ) {
		System.out.print( "What is player " + (playerIndex + 1) + " alias ? " );
		return input.nextLine();
	}
	
	/**
	 * Ask the virtual player level.
	 * 
	 * @return The chosen level as an integer.
	 */
	public int askAIPlayerLevel( AIPlayer p ) {
		System.out.print( "What is " + p.getAlias() + " (AI) level ? " );
		return Integer.valueOf( input.nextLine() );
	}
}
