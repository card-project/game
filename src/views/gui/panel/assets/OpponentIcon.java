package views.gui.panel.assets;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JLabel;

import listeners.CardAndTargetListener;
import models.players.Player;
import views.gui.manipulations.SVGIcon;
import views.gui.panel.Constant;
import events.CardAndTargetChosenEvent;

public class OpponentIcon extends JLabel implements MouseListener {

	// ------------ ATTRIBUTES ------------ //

	private static final long serialVersionUID = 8417330013434882474L;
	private Player player;
	private CardAndTargetListener listener;

	// ------------ CONSTRUCTORS ------------ //

	public OpponentIcon ( Player player, Dimension panel, CardAndTargetListener listener ) {

		this.player = player;
		this.listener = listener;

		this.addMouseListener( this );

		this.setHorizontalAlignment( JLabel.CENTER );
		this.createIcon( player.getBib(), panel );
	}

	// ------------ METHODS ------------ //

	private void createIcon( int bib, Dimension panel ) {

		double corrector = new Double( panel.height ).doubleValue() / Constant.CARD_DIMENSION.height;
		Dimension rescaledDimension = new Dimension( ( int ) ( Constant.CARD_DIMENSION.width * corrector ),
				( int ) ( Constant.CARD_DIMENSION.height * corrector ) );

		SVGIcon icon;
		try {
			icon = new SVGIcon( new File( new String( "assets/carIcons/car" + bib + ".svg" ) ).toURI().toString(),
					rescaledDimension.width, rescaledDimension.height );
			this.setIcon( icon );
		} catch ( TranscoderException e ) {
			e.printStackTrace();
		}
	}

	public int getBib() {
		return this.player.getBib();
	}

	private void fireCardAndTargetChosen() {
		this.listener.cardAndTargetChosen( new CardAndTargetChosenEvent( this.player ) );
	}

	@Override
	public void mouseClicked( MouseEvent e ) {
		this.fireCardAndTargetChosen();
	}

	@Override
	public void mouseEntered( MouseEvent e ) {
	}

	@Override
	public void mouseExited( MouseEvent e ) {
	}

	@Override
	public void mousePressed( MouseEvent e ) {
	};

	@Override
	public void mouseReleased( MouseEvent e ) {
	}

}
