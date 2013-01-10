package views.tui;

import models.players.AIPlayer;

/**
 * Menu view of the TUI (Textual User Interface) view classes.
 * 
 * It represents the view of the game menu and allows the user to set the game
 * options through its methods. <br />
 * It is the only class which handles the menu standard output.
 * 
 * @author Simon RENOULT
 * @version 1.2
 */
public class TUIMenuView extends TUIView {

	// ------------ ATTRIBUTES ------------ //

	// ------------ CONSTRUCTORS ------------ //

	// ------------ METHODS ------------ //

	/**
	 * Ask the user the number of players.
	 * 
	 * @return The number of players as an integer.
	 */
	public int askGlobalPlayersNumber() {
		super.ask( "How many players ?" );
		return super.getAnswerAsInteger();
	}

	/**
	 * Ask the user the number of human players.
	 * 
	 * @return The number of human players as an integer.
	 */
	public int askHumanPlayersNumber() {
		super.ask( "How many human players ?" );
		return super.getAnswerAsInteger();
	}

	/**
	 * Ask the user distance goal.
	 * 
	 * @return The number goal distance as an integer.
	 */
	public int askDistanceGoal() {
		super.ask( "How far do you want to go ?" );
		return super.getAnswerAsInteger();
	}

	/**
	 * Ask a player alias.
	 * 
	 * @return The chosen alias as an integer.
	 */
	public String askPlayerAlias( int playerIndex ) {
		super.ask( "What is player " + ( playerIndex + 1 ) + " alias ?" );
		return super.getAnswerAsString();
	}

	/**
	 * Ask the virtual player level.
	 * 
	 * @return The chosen level as an integer.
	 */
	public int askAIPlayerLevel( AIPlayer p ) {
		super.ask( "What is " + p.getAlias() + " (AI) level ?" );
		return super.getAnswerAsInteger();
	}
}
