package views.tui;


/**
 * @author Simon RENOULT
 * @version 0.2.1
 */
public class TUIGameView extends TUIView {
	
	// ------------ ATTRIBUTES ------------ //
		
	// ------------ CONSTRUCTORS ------------ //
	
	// ------------ METHODS ------------ //

	public String askDrawingStack() {
		System.out.print( "Which stack do you chosse to draw on ? " +
				"[D]eck or [d]iscard ? " + '\n' +
				"> " );

		return super.input.nextLine();
	}

	public int askPlayingCard( String cardList ) {
		System.out.print( cardList + '\n' +
				"Which card do you play ? " +
				"Choose its index from 1 to 5." + '\n' +
				"> " );
		
		return Integer.valueOf( super.input.nextLine() );
	}

	public int askTargetingOpponent( String playerListString ) {
		System.out.println( "What player do you target ? " +
				"Please choose his or her index." + '\n' +
				playerListString + '\n' +
				"> " );
		
		return Integer.valueOf( super.input.nextLine() );
	}
	
	public int askDiscardingCardChoice( String cardList ) {
		System.out.println( cardList + '\n' +
				"Too many card in your hand." +
				" Which one do you discard ?" +
				" Choose its index from 1 to 4." + '\n' +
				"> ");
		
		return Integer.valueOf( super.input.nextLine() );
	}
}
