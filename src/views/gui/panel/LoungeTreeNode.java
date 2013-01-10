package views.gui.panel;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * This class represents a node for the tree. We use this class to display the
 * sever's list.
 */
public class LoungeTreeNode extends DefaultMutableTreeNode {

	// ------------ ATTRIBUTES ------------ //

	private static final long serialVersionUID = 4876191100896803786L;

	private String ipAdress;
	private String loungeName;
	private String port;
	private boolean isDisplayed = false;

	private final int TIMER = 10;
	private int valid = TIMER;

	private LoungeTreeNode parent;

	// ------------ CONSTRUCTORS ------------ //

	/**
	 * Constructor.
	 */
	public LoungeTreeNode ( String ipAdress, String port, String loungeName ) {
		super();
		this.ipAdress = ipAdress;
		this.loungeName = loungeName;
		this.port = port;

		this.initialiseTimer();
	}

	// ------------ METHODS ------------ //

	@Override
	public String toString() {
		return ipAdress + ":" + loungeName;
	}

	@Override
	public boolean equals( Object obj ) {
		return this.toString().equals( obj.toString() );
	}

	// ------------ GETTERS ------------ //

	public String getIpAdress() {
		return ipAdress;
	}

	public String getLoungeName() {
		return loungeName;
	}

	public String getPort() {
		return port;
	}

	public boolean isDisplayed() {
		return isDisplayed;
	}

	public void setDisplayed( boolean isDisplayed ) {
		this.isDisplayed = isDisplayed;
	}

	/**
	 * The lounge adress is valid for a certain time. After a while, we delete
	 * this lounge's adress. We return false if this lounge isn't valid.
	 * 
	 * @return boolean
	 */
	public boolean isStillValid() {
		this.valid--;
		return ( this.valid > 0 );
	}

	/**
	 * Initialize the timer to the default value.
	 */
	public void initialiseTimer() {
		this.valid = TIMER;
	}
}
