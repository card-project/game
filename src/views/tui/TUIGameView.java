package views.tui;

import java.util.Scanner;

/**
 * @author Simon RENOULT
 * @version 0.1.1
 */
public class TUIGameView extends TUIView {
	
	// ------------ ATTRIBUTES ------------ //
		
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build a TUIGameView object and initialize the scanner 
	 * attribute on the standard input stream <em>System.in</em>. 
	 */
	public TUIGameView() {
		super.input = new Scanner( System.in );
	}
	
	// ------------ METHODS ------------ //

	public int askFirstPlayer( String playerList ) {
		System.out.print( playerList + '\n' +
			"Which player starts ? Choose its index (0 for random). " );
		
		return Integer.valueOf( super.input.nextLine() );
	}

	public String askDrawingStack() {
		System.out.print( "Which stack do you chosse to draw on ? " +
				"[D]eck or [d]iscard ? " );
		return super.input.nextLine();
	}

	public int askDiscardingCardChoice() {
		System.out.println( "Too many card in your hand." +
				" Which one do you discard ?" +
				" Choose its index from 1 to 4.");
		return Integer.valueOf( super.input.nextLine() );
	}

}
