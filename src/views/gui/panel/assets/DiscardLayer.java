package views.gui.panel.assets;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import listeners.CardAndTargetListener;
import models.cards.Card;
import models.players.Player;
import models.stacks.player.HandStack;
import controller.MainController;
import events.CancelChosenCardEvent;
import events.CardAndTargetChosenEvent;
import events.CardChosenEvent;

public class DiscardLayer extends BlackLayer implements CardAndTargetListener{

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -8282877416219749815L;
	private JPanel opponentContainer;
	private final int OFFSET = 50;
	
	private ArrayList<CardIcon> cardIconList = new ArrayList<CardIcon>();
	
	private Card chosenCard = null;
	private Player target = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public DiscardLayer(int width, int height, MainController controller) {
		super(width, height, controller);
	}
	
	// ------------ METHODS ------------ // 
	
	@Override
	protected void setElements() {
		this.setLayout(null);
	}
	
	public void drawHandAndOpponents(Player player) {
		this.defineHand(player);
	}
	
	
	private void defineHand(Player player) {
		
		HandStack playerHand = player.getHandStack();
		int i = 0, numberOfCards = playerHand.size();
		
		Dimension cardDimension = new Dimension(
			(dimension.width - 2*OFFSET) / numberOfCards,
			(dimension.height/2) - 2*OFFSET
		);
		
		for (Card card : playerHand) {
			CardIcon c = new CardIcon(card, cardDimension, this);
			c.setTooglerEnabled(false);
			
			c.setBounds(
				OFFSET + i * (cardDimension.width),
				this.getSize().height/2 - cardDimension.height / 2,
				
				cardDimension.width,
				cardDimension.height
 			);
			
			this.cardIconList.add(c);
			this.addLayer(c);
			
			i++;
		}
	}
	
	// ------------ EVENTS & LISTENERS ------------ //
	@Override
	public void cardAndTargetChosen(CardAndTargetChosenEvent event) {}
	
	@Override
	public void cardChosen(CardChosenEvent event) {
		
		CardIcon choosenCardIcon = (CardIcon) event.getSource();
		this.chosenCard = choosenCardIcon.getCard();
		
		if(this.chosenCard != null) {
			System.out.println("Card chosen : " + this.chosenCard);
			this.controller.discard(this.chosenCard);
		}
	}
	
	@Override
	public void cancelChosenCard(CancelChosenCardEvent event) {}
	
}
