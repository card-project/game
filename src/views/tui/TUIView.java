package views.tui;

import java.util.Scanner;

/**
 * 
 * @author Simon RENOULT
 * @version 1.1
 */
public abstract class TUIView {

	// ------------ ATTRIBUTES ------------ //

	protected Scanner input;

	// ------------ CONSTRUCTORS ------------ //

	public TUIView () {
		this.input = new Scanner( System.in );
	}

	// ------------ METHODS ------------ //

	/**
	 * Notify an information to the user through the command line.
	 * 
	 * @param information
	 *            Notification to show up.
	 */
	public void inform( String information ) {
		System.out.print( information );
	}

	public void title( String content ) {
		System.out.print( '\n' + "+------------ " + content.toUpperCase() + " ------------+" + '\n' );
	}

	/**
	 * Ask something to the user.
	 * 
	 * @param question
	 *            Question to ask.
	 */
	public void ask( String question ) {
		System.out.print( question + '\n' + "> " );
	}

	/**
	 * Ask somethinf to the user with a precondition.
	 * 
	 * @param previousInformation
	 * @param question
	 *            Question to ask.
	 */
	public void ask( String previousInformation, String question ) {
		this.inform( previousInformation + '\n' );
		this.ask( question );
	}

	/**
	 * Warn the user through the command line.
	 * 
	 * @param warning
	 *            Notification to show up.
	 */
	public void warn( String warning ) {
		System.out.println( "! " + warning );
	}

	/**
	 * @return the standard input as an integer.
	 */
	public int getAnswerAsInteger() {
		return Integer.valueOf( this.input.nextLine() );
	}

	/**
	 * @return the standard input as a String.
	 */
	public String getAnswerAsString() {
		return this.input.nextLine();
	}
}
