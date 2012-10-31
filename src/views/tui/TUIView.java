package views.tui;

import java.util.Scanner;

/**
 * @author Simon RENOULT
 * @version 1.0
 */
public abstract class TUIView {
	
	// ------------ ATTRIBUTES ------------ //
	
	protected Scanner input;
	
	// ------------ METHODS ------------ //
	
	/**
	 * Notify an information to the user through the command line.
	 * 
	 * @param information Notification to show up.
	 */
	public void inform( String information ) {
		System.out.println( information );
	}
	
	/**
	 * Warn the user through the command line.
	 * 
	 * @param warning Notification to show up.
	 */
	public void warn( String warning ) {
		System.out.println( "! " + warning );
	}
}
