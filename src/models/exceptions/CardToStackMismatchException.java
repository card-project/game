package models.exceptions;

public class CardToStackMismatchException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CardToStackMismatchException( String string ) {
		super( string );
	}
}
