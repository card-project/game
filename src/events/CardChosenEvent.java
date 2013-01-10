package events;

import java.util.EventObject;

import views.gui.panel.assets.CardIcon;

public class CardChosenEvent extends EventObject {

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -5223895916434385498L;

	// ------------ CONSTRUCTORS ------------ //

	public CardChosenEvent(CardIcon source) {
		super(source);
	}

}
