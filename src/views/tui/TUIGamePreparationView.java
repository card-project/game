package views.tui;

/**
 * @author Simon RENOULT
 * @version 1.0
 *
 */
public class TUIGamePreparationView extends TUIView {

	// ------------ ATTRIBUTES ------------ //
	
	// ------------ CONSTRUCTORS ------------ //
	
	// ------------ METHODS ------------ //
	
	public int askFirstPlayer( String playerList ) {
		super.ask( playerList, "Which player starts ? Choose its index (0 for random)." );
		return getAnswerAsInteger();
	}
}
