package models.exceptions;

public class TooManyHumanPlayersException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4245997798132290634L;

	public TooManyHumanPlayersException( String string ) {
		super( string );
	}
}
