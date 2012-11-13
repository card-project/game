package views.tui;

import java.util.Scanner;

/**
 * @author Simon RENOULT
 * @version 0.2.1
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

	public String askDrawingStack() {
		System.out.print( "Which stack do you chosse to draw on ? " +
				"[D]eck or [d]iscard ? " );
		return super.input.nextLine();
	}

	public int askPlayingCard( String cardList ) {
		System.out.println( cardList + '\n' +
				"Which card do you play ? " +
				"Choose its index from 1 to 5." );
		
		return Integer.valueOf( super.input.nextLine() );
	}

	public int askTargetingOpponent( String playerListString ) {
		System.out.println( "What player do you target ? " +
				"Please choose its index." + '\n' +
				playerListString );
		
		return Integer.valueOf( super.input.nextLine() );
	}
	
	public int askDiscardingCardChoice( String cardList ) {
		System.out.println( cardList + '\n' +
				"Too many card in your hand." +
				" Which one do you discard ?" +
				" Choose its index from 1 to 4.");
		return Integer.valueOf( super.input.nextLine() );
	}
}
