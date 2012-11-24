package models.exceptions.moveExceptions;

public class PlayerIsAttackedException extends IllegalMoveException {

	public PlayerIsAttackedException( String string ) {
		super( string );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 604480215604759146L;

}
