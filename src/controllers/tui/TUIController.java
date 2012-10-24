package controllers.tui;

import views.tui.TUIMenu;
import models.Game;

/**
 * Main TUI (Textual User Interface) controller.
 * 
 * Represents the interaction between model and TUI view.<br />
 * It owns the unique {@link Game} model class and handle both
 * view and model.
 * 
 * @author Simon RENOULT
 * @version 0.1
 */
public class TUIController {
	
	// ------------ ATTRIBUTES ------------ //
	
	private Game currentGame;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build the unique TUIController object and assign 
	 * the predefined Game object to the object attribute.
	 * 
	 * @param g Game object to work on.
	 */
	public TUIController( Game g ) {
		this.currentGame = g;
	}
	
	// ------------ METHODS ------------ //
	
	/**
	 * Unique public object method, it initiates the
	 * MVC management processes.
	 */
	public void run() {
		new TUIMenuController( new TUIMenu(), currentGame ).run();
		// new TUIGameController ( new TUIGame(), currentGame ); 
	}
	
}
