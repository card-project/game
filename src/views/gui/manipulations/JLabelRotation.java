package views.gui.manipulations;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JLabel;

public class JLabelRotation extends JLabel {

	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = -6509811425673640619L;
	private double angle;

	// ------------ CONSTRUCTORS ------------ //
	public JLabelRotation ( Icon icon, double angle ) {
		super( icon );
		this.angle = angle;
	}

	// ------------ METHODS ------------ //
	@Override
	public void paintComponent( Graphics g ) {
		Graphics2D gx = ( Graphics2D ) g;
		gx.rotate( Math.toRadians( angle ), getWidth() / 2, getHeight() / 2 );
		super.paintComponent( g );
	}

	public void setRotation( double angle ) {
		this.angle = angle;
	}

}
