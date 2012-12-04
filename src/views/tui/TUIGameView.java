package views.tui;


/**
 * @author Simon RENOULT
 * @version 0.2.3
 */
public class TUIGameView extends TUIView {
	
	// ------------ ATTRIBUTES ------------ //
		
	// ------------ CONSTRUCTORS ------------ //
	
	// ------------ METHODS ------------ //

	public void tickBox( boolean isTrue ) {
		super.inform( "[" + ( isTrue ? "V" : "X" ) + "]" );
	}
	
	public void tickBox ( boolean isTrue, String information ) {
		tickBox( isTrue );
		super.inform( " " + information );
	}
	
	public String askDrawingStack( String topDiscardCard ) {
		super.inform( "Discard stack : " + topDiscardCard + '\n' );
		super.ask( "Which stack do you chosse to draw on : [D]eck or [d]iscard ?" );
		return super.getAnswerAsString();
	}

	public int askPlayingCard( String cardList ) {
		super.ask( cardList, "Which card do you play ? Choose its index from 1 to 5." );
		return super.getAnswerAsInteger();
	}

	public int askTargetingOpponent( String playerListString ) {
		super.ask( playerListString, "What player do you target ? Please choose his or her index." );
		return super.getAnswerAsInteger();
	}
	
	public int askDiscardingCardChoice( String cardList ) {
		super.ask( cardList, "Too many card in your hand. Which one do you discard ?" +
				" Choose its index." );
		return Integer.valueOf( super.input.nextLine() );
	}
}
