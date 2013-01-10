package views.gui.panel.assets;

import java.awt.Point;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.JLabel;

import listeners.CardAndTargetListener;
import models.Game;
import models.stacks.game.DeckStack;

import org.apache.batik.transcoder.TranscoderException;

import views.gui.manipulations.SVGIcon;
import views.gui.panel.Constant;
import controller.MainController;
import events.CancelChosenCardEvent;
import events.CardAndTargetChosenEvent;
import events.CardChosenEvent;

public class DrawingStepLayer extends BlackLayer implements CardAndTargetListener{

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -8282877416219749815L;
	private final int OFFSET = 50;
	
	private DeckStack target = null;
	private JLabel container = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public DrawingStepLayer(int width, int height, MainController controller) {
		super(width, height, controller);
	}
	
	// ------------ METHODS ------------ // 
	
	@Override
	protected void setElements() {
		this.setLayout(null);	
	}
	
	public void drawHandAndDeck(Game game) {
		this.defineDeck();
		this.fillDeck(game);
	}
	
	private void fillDeck(Game game) {
		
		CardIcon nullCard = new CardIcon(null, Constant.DECK_CARD_DIMENSION, this);
		nullCard.setTooglerEnabled(false);
		
		Point position = new Point(
			this.container.getLocation().x + 340,
			this.container.getLocation().y + 60
		);
		
		nullCard.setBounds(position.x, position.y, Constant.DECK_CARD_DIMENSION.width, Constant.DECK_CARD_DIMENSION.height);
		this.addLayer(nullCard);
		
		if(!game.getDiscardStack().isEmpty())
		{
			CardIcon discardCard = new CardIcon(game.getDiscardStack().peek(), Constant.DECK_CARD_DIMENSION, this);
			discardCard.setTooglerEnabled(false);
			
			position = new Point(
					this.container.getLocation().x + 60,
					this.container.getLocation().y + 60
			);
				
			discardCard.setBounds(position.x, position.y, Constant.DECK_CARD_DIMENSION.width, Constant.DECK_CARD_DIMENSION.height);
			this.addLayer(discardCard);
		}
	}
	
	private void defineDeck() {
		
		try {
			SVGIcon icon = new SVGIcon(new File("assets/DeckContainer.svg").toURI().toURL().toString());
			container = new JLabel(icon);
			
			container.setBounds(
					this.dimension.width / 2 - Constant.DECK_DIMENSION.width/2,
					this.dimension.height / 2 - Constant.DECK_DIMENSION.height/2,
					(int) ( Constant.DECK_DIMENSION.width),
					(int) ( Constant.DECK_DIMENSION.height));
			
			this.addLayer(container);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TranscoderException e) {
			e.printStackTrace();
		}
		
	}
	
	// ------------ EVENTS & LISTENERS ------------ //
	@Override
	public void cardAndTargetChosen(CardAndTargetChosenEvent event) {
	}
	
	@Override
	public void cardChosen(CardChosenEvent event) {
		
		CardIcon choosenCardIcon = (CardIcon) event.getSource();
		
		if(choosenCardIcon.getCard() == null)
			this.controller.drawFrom(this.controller.getGameModel().getDeckStack());
		else
			this.controller.drawFrom(this.controller.getGameModel().getDiscardStack());
	}
	
	@Override
	public void cancelChosenCard(CancelChosenCardEvent event) {}
	
}
