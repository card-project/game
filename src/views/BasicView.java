package views;

import listeners.GameEventListener;
import listeners.LoungeListener;
import controller.MainController;

public abstract class BasicView implements Viewable, LoungeListener, GameEventListener {

	// ------------ ATTRIBUTES ------------ //

	protected MainController controller = null;

	// ------------ CONSTRUCTORS ------------ //

	public BasicView ( MainController c ) {
		super();
		this.controller = c;
	}

	// ------------ METHODS ------------ //

	public MainController getController() {
		return controller;
	}

}
