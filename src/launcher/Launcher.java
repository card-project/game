package launcher;

import models.Game;
import controllers.tui.TUIController;

/**
 * 
 * Entry point of the "1000 Bornes" program.
 * 
 * It initiates a new {@link Game} model and a new
 * {@link TUIController} so that the model can be handled
 * and the game played.
 * 
 * @author Simon RENOULT
 * @version 1.0.1
 */
public class Launcher {

	/**
	 * Initiation of a new {@link Game} model and transfer to a new
	 * {@link TUIController}.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		Game g = new Game();
		
		// new GUIController ( g ).run();
		new TUIController( g ).run();
	}

}
