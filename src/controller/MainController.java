package controller;

import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.cards.DistanceCard;
import models.cards.HazardCard;
import models.cards.RemedyCard;
import models.cards.SafetyCard;
import models.players.Player;
import models.stacks.game.GameStack;
import server.exceptions.ConnectionFailedException;
import server.instructions.ConnectedToLounge;
import view.BasicView;
import view.gui.GameWindow;
import controller.gameProcessing.LocalGameController;
import controller.threads.FindServerThread;
import events.GameStartedEvent;

public class MainController extends BasicController {

	// ------------ ATTRIBUTES ------------ //
	
	private ArrayList<BasicView> viewList = new ArrayList<BasicView>();
	
	// ------------ CONSTRUCTORS ------------ //
	
	public MainController(Game gameModel) {
		super(gameModel);
		this.viewList.add(new GameWindow(this));	
	}
	
	// ------------ METHODS ------------ //
	@Override
	public void displayMenuView() {
		this.stopRunningThreads();
		
		for (BasicView view : this.viewList) {
			view.displayMenuView();
		}
	}

	@Override
	public void displayOnePlayerView() {
		this.stopRunningThreads();
		
		for (BasicView view : this.viewList) {
			view.displayOnePlayerView();
		}
	}
	
	@Override
	public void displayMultiplayerView() {
		for (BasicView view : this.viewList) {
			this.addRunningThread(new FindServerThread(view));
			view.displayMultiplayerView();
		}
	}

	@Override
	public void displayGameView() {
		this.stopRunningThreads();
		
		for (BasicView view : this.viewList) {
			view.displayGameView();
			gameModel.addEventListener(view);
		}
	}

	@Override
	public void displayResultView() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void displayWaitingRoom() {
		for (BasicView view : this.viewList) {
			view.displayWaitingRoom();
		}
	}
	
	public void displayGameStarted(Game model) {
		this.gameModel = model;
		
		for (BasicView view : this.viewList) {
			gameModel.addEventListener(view);
			view.displayGameView();
			view.gameStarted(new GameStartedEvent(model));
		}
	}
	
	// ------------ METHODS ------------ //
	
	public void initializeOnePlayerGame(String playerName, int numberOfOpponents) {
		
		LocalGameController gc = new LocalGameController(this.gameModel, this.viewList, this);
		gc.initModel(playerName, numberOfOpponents);
		
		this.gameControlleur = gc;
		this.displayGameView();
	}
	
	public void initializeMultiplayerGame(String ipAdress, String port, String lounge) {
		
		NetworkGameController gc = new NetworkGameController(this.gameModel, this.viewList, this);
		try {
			gc.joinGame(ipAdress, port, lounge);
		} catch (ConnectionFailedException e) {
			System.out.println("Unable to connect to the lounge");
			e.printStackTrace();
		}
		
		this.gameControlleur = gc;
		//this.displayWaitingView();
	}
	
	public void startGame() {
		this.gameControlleur.run();		
	}

	public boolean playCard(Card chosenCard, Player target) {

		boolean userChoiceIsCorrect = false;
		
		if ( chosenCard instanceof HazardCard ) {
			userChoiceIsCorrect = ( ( HazardCard ) chosenCard ).isPlayableOn( target );
		} else {
			target = this.gameModel.getCurrentPlayer();
			if ( chosenCard instanceof DistanceCard ) {
				userChoiceIsCorrect = ( ( DistanceCard ) chosenCard ).isPlayableOn( target, super.gameModel.getGoal() );
			} else if ( chosenCard instanceof RemedyCard ) {
				userChoiceIsCorrect = ( ( RemedyCard ) chosenCard).isPlayableOn( target );
			} else if ( chosenCard instanceof SafetyCard ) {
				userChoiceIsCorrect = ( ( SafetyCard ) chosenCard).isPlayableOn( target );
			}
		}
		
		if(userChoiceIsCorrect)
			this.gameControlleur.playCard(chosenCard, target);
		
		return userChoiceIsCorrect;
	}

	public void drawFrom(GameStack deckStack) {
		this.gameControlleur.draw(deckStack);
	}

	public void discard(Card choosenCard) {
		this.gameControlleur.discard(choosenCard);		
	}

	public void nextPlayer() {
		this.gameControlleur.nextPlayer();
	}

	
}
