package launcher;

import models.Game;
import controllers.tui.TUIController;

public class Launcher {

	public static void main( String[] args ) {

		Game gameModel = new Game();
		/* MainController controller = new MainController(gameModel); */
		new TUIController( gameModel ).run();

	}

}
