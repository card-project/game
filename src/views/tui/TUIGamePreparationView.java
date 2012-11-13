package views.tui;

public class TUIGamePreparationView extends TUIView {

	// ------------ ATTRIBUTES ------------ //
	
	// ------------ CONSTRUCTORS ------------ //
	
	// ------------ METHODS ------------ //
	
	public int askFirstPlayer( String playerList ) {
		System.out.print( playerList + '\n' +
				"Which player starts ? Choose its index (0 for random). " );
		
		return Integer.valueOf( super.input.nextLine() );
	}
}
