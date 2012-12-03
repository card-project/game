package models.exceptions.moveExceptions;


public class PlayerIsSlowedException extends IllegalMoveException {

	public PlayerIsSlowedException( String string ) {
		super( string );
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3024942193140528860L;

}
