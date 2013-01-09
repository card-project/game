package view.gui;

import java.awt.CardLayout;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.util.HashMap;

import javax.swing.JFrame;

import models.Game;
import models.players.Player;
import view.gui.panel.BasicPanel;
import view.gui.panel.global.GamePanel;
import view.gui.panel.global.NetworkPanel;
import events.LoungeFoundEvent;

public class GameJFrame extends JFrame{
	
	// ------------ ATTRIBUTES ------------ //
	private BasicPanel currentPanel = null;
	private static final long serialVersionUID = 268735233335449126L;
	
	private GraphicsDevice screenInformations = null;
	private DisplayMode originalMode = null; // We do a back-up to keep in memory the original mode of the screen
	
	private String panelDisplayed = null;
	private CardLayout layoutManager;
	
	private HashMap<Class<? extends BasicPanel>, BasicPanel> layers = new HashMap<Class<? extends BasicPanel>, BasicPanel>();
	
	// ------------ CONSTRUCTORS ------------ //
	public GameJFrame(GraphicsDevice screenInformations) {
		
		super(screenInformations.getDefaultConfiguration());
		
		this.screenInformations = screenInformations;
		this.originalMode = screenInformations.getDisplayMode();
		this.layoutManager = new CardLayout();
		
		this.setConfiguration();
	}
	
	public void setConfiguration() {
		this.setTitle("1000Bornes.");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(this.layoutManager);
	}
	
	public BasicPanel getPanel() {
		return currentPanel;
	}
	
	public void addPanel(BasicPanel panel, String panelName) {
		this.layers.put(panel.getClass(), panel);
		this.add(panel, panelName);
	}
	
	public void displayPanel(String name) {
		this.panelDisplayed = name;
		this.layoutManager.show(this.getContentPane(), name);
	}
	
	public String getPanelDisplayed() {
		return panelDisplayed;
	}
	
	public void displayWindow() {
		boolean isFullScreen = this.screenInformations.isFullScreenSupported();
		
		setResizable(false);
		setUndecorated(isFullScreen);
		
		if(isFullScreen)
		{
			this.screenInformations.setFullScreenWindow(this);
			this.validate();
		}
		else
		{
			this.setSize(
				this.getScreenInformations().getDisplayMode().getWidth(),
				this.getScreenInformations().getDisplayMode().getHeight());
			this.setVisible(true);
		}
	}
	
	public GraphicsDevice getScreenInformations() {
		return screenInformations;
	}

	public void initGame(Game game) {		
		GamePanel panel = (GamePanel) this.layers.get(GamePanel.class);
		
		if(panel != null)
			panel.initGameboard(game);
	}

	public void displayHandLayer(Player player) {
		GamePanel panel = (GamePanel) this.layers.get(GamePanel.class);
		
		if(panel != null)
			panel.displayHandLayer(player);
	}

	public void hideBackLayers() {
		GamePanel panel = (GamePanel) this.layers.get(GamePanel.class);
		
		if(panel != null) {
			panel.hideHandLayer();
			panel.hideDeck();
			panel.hideDiscardChoice();
		}
	}

	public void animatePlayer(Player p) {
		GamePanel panel = (GamePanel) this.layers.get(GamePanel.class);
		
		if(panel != null) {	
			panel.refreshPlayerHand();
			panel.animatePlayer(p);
		}
	}

	public void displayDiscardChoice(Player player) {
		GamePanel panel = (GamePanel) this.layers.get(GamePanel.class);
			
		if(panel != null)
			panel.displayDiscardChoice(player);
	}

	public void displayDeckLayer(Player player) {
		GamePanel panel = (GamePanel) this.layers.get(GamePanel.class);
		
		if(panel != null)
			panel.displayDeck();
	}

	public void editLoungeList(LoungeFoundEvent e) {
		NetworkPanel panel = (NetworkPanel) this.layers.get(NetworkPanel.class);
		
		if(panel != null)
			panel.editLoungeList(e);
	}
}
