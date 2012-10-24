package Launcher;

import controllers.tui.TUIController;
import models.Game;

/**
 * 
 * Entry point of the "1000 Bornes" program.
 * 
 * It initiates a new {@link Game} model and a new
 * {@link TUIController} so that the model can be handled
 * and the game played.
 * 
 * @author Simon RENOULT
 * @version 0.1
 */
public class Launcher {

	/**
	 * Initiation of a new {@link Game} model and transfer to a new
	 * {@link TUIController}.
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		new TUIController( new Game() ).run();
	}

}
