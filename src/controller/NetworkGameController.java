package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.players.Player;
import models.stacks.game.GameStack;
import server.Client;
import server.exceptions.ConnectionFailedException;
import server.instructions.ConnectedToLounge;
import server.instructions.Instruction;
import server.instructions.JoinLounge;
import server.instructions.LoungeNoLongerExists;
import view.BasicView;
import controller.gameProcessing.GameController;

public class NetworkGameController extends GameController {

	// ------------ ATTRIBUTES ------------ //
	
	private Client client;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public NetworkGameController(Game gameModel, ArrayList<BasicView> viewList, MainController controller) {
		super(gameModel, viewList, controller);
	}
	
	// ------------ METHODS ------------ //

	public void run() {
		
	};
	
	@Override
	public void discard(Card chosenCard) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(GameStack stack) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void nextPlayer() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void playCard(Card card, Player target) {
		// TODO Auto-generated method stub
		
	}

	public void joinGame(String ipAdress, String port, String lounge) throws ConnectionFailedException {
		
		try {
			Socket clientSocket = new Socket(ipAdress, new Integer(port).intValue());
			
			this.client = new Client(clientSocket, this);
			this.client.joinLounge(lounge);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Connection to the lounge failed.");
			throw new ConnectionFailedException();
		}	
	}

	public void displayWaitingRoom(ConnectedToLounge catchInstruction) {
		System.out.println("Display waiting");
		this.controller.displayWaitingRoom();
	}

	public void gameStarted(Game model) {
		this.controller.displayGameStarted(model);
	}
	
	public MainController getMainController() {
		return this.controller;
	}

	public void startDrawingStep() {
		this.game.getCurrentPlayer().fireChooseDeckToDrawEvent();
	}
	
}
