package controller;

import java.util.ArrayList;

import models.Game;
import models.players.HumanPlayer;
import views.Viewable;
import controller.gameProcessing.GameController;
import controller.threads.BasicControllerThread;

public abstract class BasicController implements Viewable {
	
	// ------------ ATTRIBUTES ------------ //
	protected ArrayList<BasicControllerThread> runningThreads;
	protected Game gameModel;
	protected GameController gameControlleur = null;
	
	private HumanPlayer player;
	
	// ------------ CONSTRUCTORS ------------ //
	public BasicController(Game gameModel) {
		this.gameModel = gameModel;
		this.runningThreads = new ArrayList<BasicControllerThread>();
	}
	
	public void stopRunningThreads() {
		for (BasicControllerThread thread : this.runningThreads) {
			thread.stop();
		}
	}
	
	public void addRunningThread(BasicControllerThread basic) {
		this.runningThreads.add(basic);
	}
	
	public Game getGameModel() {
		return gameModel;
	}
	
	public void setPlayer(HumanPlayer player) {
		this.player = player;
	}
	
	public HumanPlayer getPlayer() {
		return player;
	}
	

}
