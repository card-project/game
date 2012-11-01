package controllers.tui;

import models.Game;
import views.tui.TUIGameView;
import views.tui.TUIMenuView;

/**
 * Main TUI (Textual User Interface) controller.
 * 
 * Represents the interaction between model and TUI view.<br />
 * It owns the unique {@link Game} model class and handle both view and model.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class TUIController {

	// ------------ ATTRIBUTES ------------ //

	private Game currentGame;

	// ------------ CONSTRUCTORS ------------ //

	/**
	 * Build the unique TUIController object and assign the predefined Game
	 * object to the object attribute.
	 * 
	 * @param g
	 *            Game object to work on.
	 */
	public TUIController( Game g ) {
		this.currentGame = g;
	}

	// ------------ METHODS ------------ //

	/**
	 * Unique public object method, it initiates the MVC management processes.
	 */
	public void run() {
		new TUIMenuController( new TUIMenuView(), currentGame ).run();
		new TUIGameController( new TUIGameView(), currentGame ).run();
	}

}
