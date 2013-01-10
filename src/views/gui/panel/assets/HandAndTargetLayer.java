package views.gui.panel.assets;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import listeners.CardAndTargetListener;
import models.cards.Card;
import models.cards.HazardCard;
import models.players.Player;
import models.stacks.player.HandStack;

import org.apache.batik.transcoder.TranscoderException;

import views.gui.manipulations.SVGIcon;
import views.gui.panel.Constant;
import controller.MainController;
import events.CancelChosenCardEvent;
import events.CardAndTargetChosenEvent;
import events.CardChosenEvent;

public class HandAndTargetLayer extends BlackLayer implements CardAndTargetListener{

	// ------------ ATTRIBUTES ------------ //
	
	private static final long serialVersionUID = -8282877416219749815L;
	private JPanel opponentContainer;
	private final int OFFSET = 50;
	
	private ArrayList<CardIcon> cardIconList = new ArrayList<CardIcon>();
	private ArrayList<OpponentIcon> opponentIconList = new ArrayList<OpponentIcon>();
	
	private Card chosenCard = null;
	private JLabel discardLabel = null;
	
	
	// ------------ CONSTRUCTORS ------------ //
	
	public HandAndTargetLayer(int width, int height, MainController controller) {
		super(width, height, controller);
	}
	
	// ------------ METHODS ------------ // 
	
	@Override
	protected void setElements() {
		this.setLayout(null);
		this.setOpponentContainer();
	}
	
	private void setOpponentContainer() {
		
		this.opponentContainer = new JPanel();
		this.opponentContainer.setLayout(new GridLayout(1,5));
		this.opponentContainer.setBounds(
				OFFSET,
				OFFSET,
				dimension.width - 2*OFFSET,
				(dimension.height/2) - 2*OFFSET
			);
		
		this.opponentContainer.setOpaque(false);
		
		this.addLayer(this.opponentContainer);
	}

	public void drawHandAndOpponents(Player player) {
		this.defineHand(player);
		this.setSwitchToDiscard();
	}
	
	private void drawOpponents(HazardCard card) {
		
		ArrayList<Player> opponentList = this.controller.getGameModel().getAttackableOpponents(this.controller.getGameModel().getCurrentPlayer(), card);
		
		Dimension dim = new Dimension(
				(dimension.width - 2*OFFSET) / opponentList.size(),
				(dimension.height/2) - 2*OFFSET
		);
	
		int i = 0;
		
		System.out.println("You can attack : ");
		for (Player p : opponentList) {
			
			System.out.println(" == > " + p.getAlias() + " ( "+ p.getBib() +" ) ");
			System.out.println(" +++++++> " + p.getDistanceStack());
			System.out.println(" +++++++> " + p.getSafetyStack());
			
			OpponentIcon icon = new OpponentIcon(p, dim, this);
			icon.setBounds(
				OFFSET + i * (dim.width),
				OFFSET,
				
				dim.width,
				dim.height
			);
			
			this.opponentIconList.add(icon);
			this.addLayer(icon);
			
			i++;
		}
		
		
	}
	
	private void hideOpponents() {
		
		for (OpponentIcon icon : this.opponentIconList) {
			this.removeLayer(icon);
		}
		
		this.opponentIconList.clear();
		this.repaint();
	}
	
	private void setSwitchToDiscard() {
		
		try {
			SVGIcon icon = new SVGIcon(new File("assets/text/wantDiscard.svg").toURI().toURL().toString(), Constant.WANTDISCARD_TEXT.width, Constant.WANTDISCARD_TEXT.height);
			this.discardLabel = new JLabel(icon);
			this.discardLabel.setBounds(
				OFFSET,
				OFFSET,
				Constant.WANTDISCARD_TEXT.width,
				Constant.WANTDISCARD_TEXT.height
			);
			
			this.discardLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					controller.discard(chosenCard);
				}
			});
		
		} catch (TranscoderException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void displaySwitchToDiscard() {
		this.addLayer(this.discardLabel);
		this.repaint();		
	}
	
	private void hideSwitchToDiscard() {
		this.removeLayer(this.discardLabel);
		this.repaint();		
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
			
			c.setBounds(
				OFFSET + i * (cardDimension.width),
				OFFSET + this.getSize().height/2,
				
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
	public void cardAndTargetChosen(CardAndTargetChosenEvent event) {
		
		System.out.println("[  ICIICIICICICICICICI ]");
		System.out.println(this.chosenCard + " - " + event.getOpponent());
		
		if(!this.controller.playCard(this.chosenCard, event.getOpponent()))
			System.out.println("You cant attack this player : " + event.getOpponent().getSafetyStack());
	}
	
	@Override
	public void cardChosen(CardChosenEvent event) {
		
		CardIcon choosenCardIcon = (CardIcon) event.getSource();
		this.chosenCard = choosenCardIcon.getCard();
		
		for (CardIcon c : this.cardIconList) {
			if(!choosenCardIcon.getCard().equals(c.getCard()))
				this.hide(c);
		}
		
		if(this.chosenCard instanceof HazardCard )
			this.drawOpponents((HazardCard) this.chosenCard);
		else
			if(!this.controller.playCard(this.chosenCard, this.controller.getGameModel().getCurrentPlayer()))
				this.displaySwitchToDiscard();
	}
	

	@Override
	public void cancelChosenCard(CancelChosenCardEvent event) {
		this.chosenCard = null;
		for (CardIcon c : this.cardIconList) {
			this.show(c);
		}
		
		this.hideOpponents();
		this.hideSwitchToDiscard();
	}
	
}
