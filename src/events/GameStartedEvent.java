package events;

import java.util.EventObject;

public class GameStartedEvent extends EventObject {

	private static final long serialVersionUID = -1478699363980274595L;

	public GameStartedEvent ( Object source ) {
		super( source );
	}

}
