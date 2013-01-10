package views.gui.panel.assets;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JLabel;

import listeners.CardAndTargetListener;
import models.cards.Card;

import org.apache.batik.transcoder.TranscoderException;

import views.gui.manipulations.SVGIcon;
import views.gui.panel.Constant;
import events.CancelChosenCardEvent;
import events.CardChosenEvent;

public class CardIcon extends JLabel implements MouseListener {

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = 832452309172636773L;
	private Card card;
	private CardAndTargetListener layer;
	
	private boolean toogler = true;
	private boolean tooglerEnabled = true;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public CardIcon(Card type, Dimension labelDimension) {
		this(type, labelDimension, null);
	}
	
	public CardIcon(Card type, Dimension labelDimension, CardAndTargetListener listener) {
		this.card = type;
		this.layer = listener;
		
		this.setSize(labelDimension);
		
		this.setHorizontalAlignment(JLabel.CENTER);
		this.createIcon();
		
		if(listener != null)
			this.setListener();
	}
	
	// ------------ METHODS ------------ //
	
	private void setListener() {
		this.addMouseListener(this);
	}

	private void createIcon() {
		
		double corrector = new Double(this.getSize().height).doubleValue() / Constant.CARD_DIMENSION.height;
		
		Dimension rescaledDimension = new Dimension(
			( int ) (Constant.CARD_DIMENSION.width * corrector),
			( int ) (Constant.CARD_DIMENSION.height * corrector)
		);
		
		SVGIcon icon;
		try {
			
			if(this.card != null)
				icon = new SVGIcon(new File(new String("assets/" + this.card.getType() + ".svg")).toURI().toString(), rescaledDimension.width, rescaledDimension.height);
			else
				icon = new SVGIcon(new File(new String("assets/Null.svg")).toURI().toString(), rescaledDimension.width, rescaledDimension.height);
			
			this.setIcon(icon);
		} catch (TranscoderException e) {
			e.printStackTrace();
		}
	}
	
	public Card getCard() {
		return card;
	}
	
	public void setTooglerEnabled(boolean tooglerEnabled) {
		this.tooglerEnabled = tooglerEnabled;
	}
	
	private void fireCardChoosenEvent() {
		this.layer.cardChosen(new CardChosenEvent(this));
	}
	
	private void fireCancelChoosenCardEvent() {
		this.layer.cancelChosenCard(new CancelChosenCardEvent(this));
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(tooglerEnabled)
		{
			if(toogler) {
				this.fireCardChoosenEvent();
				this.toogler = false;
			}
			else {
				this.toogler = true;
				this.fireCancelChoosenCardEvent();
			}
		}
		else
			this.fireCardChoosenEvent();
			
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
}
