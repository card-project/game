package controller.gamePreparator;

import javax.swing.event.EventListenerList;

import models.Game;
import models.exceptions.AliasAlreadyChosenException;
import models.exceptions.IllegalDistanceException;
import models.exceptions.IllegalHumanPlayerNumberException;
import models.exceptions.IllegalPlayerNumberException;
import models.players.HumanPlayer;
import controller.MainController;

/**
 * This controller initialize a default state for the one player game.
 */
public class OnePlayerGamePreparator {

	// ------------ ATTRIBUTES ------------ //
	
	private Game model;
	private MainController controller;
	
	private final int NB_HUMANPLAYERS = 1;
	private final int FINALGOAL = 1000;
	
	private String playerName;
	private int numberOfOpponents = -1;
	
	private EventListenerList listeners;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public OnePlayerGamePreparator(Game model, MainController controller) {
		this.model = model;
		this.controller = controller;
	}
	
	// ------------ METHODS ------------ //
	
	/**
	 * Start the initialization process. 
	 */
	public void execute() {
		
		this.initTotalPlayers();
		this.initHumanPlayer();
		this.defineFinalGoal();
		this.definePlayersAlias();
		this.model.setAIPlayersAlias();
		this.model.initiateAIPlayers();
		
		this.prepareDecks();
	}
	
	/**
	 * Shuffle and distribute cards to players.
	 */
	private void prepareDecks() {
		this.model.getDeckStack().shuffle();
		this.model.distributeCardsToPlayers();
	}
	
	/**
	 * Init the total count of players.
	 */
	private void initTotalPlayers() {
		try {
			this.model.setPlayersNumber(this.numberOfOpponents+1);
		} catch (IllegalPlayerNumberException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Init a single human player.
	 */
	private void initHumanPlayer() {
		try {
			this.model.setHumanPlayersNumber(NB_HUMANPLAYERS);
		} catch (IllegalHumanPlayerNumberException e) {
			e.printStackTrace();
		}
	}
	
	public void defineFinalGoal() {
		try {
			this.model.setDistanceGoal (FINALGOAL);
		} catch ( IllegalDistanceException e ) {}
	}
	
	public void definePlayersAlias() {
		
		for ( int i = 0; i < this.model.getPlayers().length ; i++ ) {
			
			if ( this.model.getPlayers()[i] instanceof HumanPlayer ) {
				
				try {
					this.model.setPlayerAlias( this.model.getPlayers()[i], this.playerName);
					this.controller.setPlayer((HumanPlayer) this.model.getPlayers()[i]);
				} catch ( AliasAlreadyChosenException e ) {
					e.printStackTrace();
				}
				
			}
		}
	}

	// ------------ SETTERS ------------ //
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public void setOpponents(int numberOfOpponents) {
		this.numberOfOpponents = numberOfOpponents;
	}
	
}
