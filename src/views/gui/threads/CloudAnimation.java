package views.gui.threads;

import java.awt.Point;

import javax.swing.JLabel;

public class CloudAnimation extends Animation {

	// ------------ ATTRIBUTES ------------ //
	private Thread thread = null;
	private JLabel cloud = null;
	private boolean cloudIsMoving = true;

	private int offset = 1;
	private int maxWidth;
	private int speed;

	// ------------ CONSTRUCTORS ------------ //
	public CloudAnimation ( JLabel cloudLabel, int maxWidth, int speed ) {
		this.cloud = cloudLabel;
		this.maxWidth = maxWidth;
		this.speed = speed;

		this.thread = new Thread( this );
		this.thread.start();
	}

	// ------------ METHODS ------------ //
	@Override
	public void run() {
		while ( this.cloudIsMoving ) {
			Point newPosition = new Point();
			int x = 0;
			int y = ( int ) this.cloud.getLocation().getY();

			if ( ( this.maxWidth - ( this.cloud.getLocation().x ) ) <= 0 )
				x = -this.cloud.getWidth();
			else
				x = ( int ) this.cloud.getLocation().getX() + offset;

			newPosition.setLocation( x, y );

			this.cloud.setLocation( newPosition );

			try {
				Thread.sleep( this.speed );
			} catch ( InterruptedException e ) {
				e.printStackTrace();
			}
		}
	}

	public void stopAnimation() {
		this.cloudIsMoving = false;
	}
}
