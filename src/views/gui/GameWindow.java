package views.gui;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import models.Game;
import models.players.Player;
import views.BasicView;
import views.gui.panel.global.GamePanel;
import views.gui.panel.global.MenuPanel;
import views.gui.panel.global.NetworkPanel;
import views.gui.panel.global.OnePlayerPanel;
import views.gui.panel.global.WaitingPanel;
import views.gui.panel.global.WaitingRoomPanel;
import controller.MainController;
import events.CardDrawnEvent;
import events.ChooseCardAndTargetEvent;
import events.ChooseCardToDiscardEvent;
import events.DeckToDrawEvent;
import events.GameIsOverEvent;
import events.GameStartedEvent;
import events.LoungeFoundEvent;
import events.PlayerHasPlayedEvend;

public class GameWindow extends BasicView {

	// ------------ ATTRIBUTES ------------ //
	private ArrayList<GraphicsDevice> devices = new ArrayList<GraphicsDevice>();
	private ArrayList<GameJFrame> frames = new ArrayList<GameJFrame>();
	
	private Dimension screenDimension;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public GameWindow(MainController c) {
		super(c);
		this.initWindows();
	}
	
	// ------------ METHODS ------------ //
	
	
	public void initWindows() {
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		
		// We get the different screens/devices available.
		GraphicsDevice[] devices = env.getScreenDevices();

		int i = 0;
//		for (int i = 0; i < devices.length && i < 2; i++) {
			this.devices.add(devices[i]);
			this.frames.add(new GameJFrame(devices[i]));
//		}
		
		this.initScreensPanel();
		this.displayMenuView();
		
		for (GameJFrame g : this.frames) {
			g.displayWindow();
		}
	}
	
	private void initScreensPanel() {
		
		for (int i = 0; i < this.devices.size() && i < 2; i++) {
			GameJFrame frame = this.frames.get(i);
			
			// If we are working on the main screen
			if(i == 0)
			{
				frame.addPanel(new MenuPanel(this.devices.get(i), this.getController()), "MenuPanel");
				frame.addPanel(new GamePanel(this.devices.get(i),this.getController()), "GamePanel");
				frame.addPanel(new OnePlayerPanel(this.devices.get(i),this.getController()), "OnePlayerPanel");
				frame.addPanel(new NetworkPanel(this.devices.get(i),this.getController()), "NetworkPanel");
				frame.addPanel(new WaitingRoomPanel(this.devices.get(i),this.getController()), "WaitingRoomPanel");
			}
			else
			{
				frame.addPanel(new WaitingPanel(this.devices.get(i), this.getController()), "MenuPanel");
				frame.addPanel(new WaitingPanel(this.devices.get(i), this.getController()), "GamePanel");
				frame.addPanel(new WaitingPanel(this.devices.get(i), this.getController()), "OnePlayerPanel");
				frame.addPanel(new WaitingPanel(this.devices.get(i), this.getController()), "NetworkPanel");
				frame.addPanel(new WaitingRoomPanel(this.devices.get(i),this.getController()), "WaitingRoomPanel");
			}
		}
	}
	
	@Override
	public void displayMenuView() {
		for (GameJFrame frame : this.frames) {
			frame.displayPanel("MenuPanel");
		}
	}
	
	@Override
	public void displayGameView() {
		for (GameJFrame frame : this.frames) {
			frame.displayPanel("GamePanel");
		}
	}
	
	@Override
	public void displayOnePlayerView() {
		for (GameJFrame frame : this.frames) {
			frame.displayPanel("OnePlayerPanel");
		}
	}
	
	@Override
	public void displayMultiplayerView() {
		for (GameJFrame frame : this.frames) {
			frame.displayPanel("NetworkPanel");
		}
	}
	
	@Override
	public void displayResultView() {
		for (GameJFrame frame : this.frames) {
			frame.displayPanel("OnePlayerPanel");
		}
	}
	
	@Override
	public void displayWaitingRoom() {
		for (GameJFrame frame : this.frames) {
			frame.displayPanel("WaitingRoomPanel");
		}
	}
	
	// ------------ LISTENERS ------------ //
	@Override
	public void loungeFound(LoungeFoundEvent e) {		
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("NetworkPanel")) {
				frame.editLoungeList(e);
			}
		}
	}
	
	@Override
	public void cardHasBeenDrawn(CardDrawnEvent event) {
		// TODO
	}
	
	@Override
	public void chooseDeckToDraw(DeckToDrawEvent event) {
		System.out.println("[ It's time to draw ]");
		this.hideBlackLayers();
		this.displayDeck(this.controller.getGameModel().getCurrentPlayer());
	}
	
	@Override
	public void gameIsOver(GameIsOverEvent event) {
		System.out.println("Game is over");
	}
	
	@Override
	public void chooseCardToPlay(ChooseCardAndTargetEvent event) {
		this.hideBlackLayers();
		this.displayHand((Player) event.getSource());
	}
	
	@Override
	public void gameStarted(GameStartedEvent event) {
		this.displayInitialGameState((Game)event.getSource());	
	}
	
	@Override
	public void playerHasPlayed(PlayerHasPlayedEvend event) {
		this.hideBlackLayers();
		this.animatePlayer((Player) event.getSource());
	}
	
	@Override
	public void chooseCardToDiscard(ChooseCardToDiscardEvent event) {
		System.out.println("Choose a card to discard");
		this.hideBlackLayers();
		this.displayDiscardChoice(this.controller.getGameModel().getCurrentPlayer());
		
	}
	
	
	// ------------ UPDATING VIEW ------------ //
	
	private void displayInitialGameState(Game game) {
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("GamePanel")) {
				frame.initGame(game);
			}
		}
	}
	
	private void hideBlackLayers() {
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("GamePanel")) {
				frame.hideBackLayers();
			}
		}
	}
	
	private void animatePlayer(Player p) {
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("GamePanel")) {
				frame.animatePlayer(p);
			}
		}
	}
	
	private void displayDeck(Player player) {
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("GamePanel")) {
				frame.displayDeckLayer(player);
			}
		}
	}
	
	private void displayHand(Player player) {
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("GamePanel")) {
				frame.displayHandLayer(player);
			}
		}
	}
	
	private void displayDiscardChoice(Player player) {
		for (GameJFrame frame : this.frames) {
			if(frame.getPanelDisplayed().equals("GamePanel")) {
				frame.displayDiscardChoice(player);
			}
		}
	}
}
