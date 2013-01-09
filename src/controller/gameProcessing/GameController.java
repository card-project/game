package controller.gameProcessing;

import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.players.Player;
import models.stacks.game.GameStack;
import view.BasicView;
import controller.MainController;


public abstract class GameController {

	// ------------ ATTRIBUTES ------------ //
	
	protected Game game = null;
	protected ArrayList<BasicView> viewList = null;
	protected MainController controller;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public GameController(Game gameModel, ArrayList<BasicView> viewList, MainController controller) {
		super();
		this.controller = controller;
		this.game = gameModel;
		this.viewList = viewList;
	}
	
	// ------------ ABSTRACT METHODS ------------ //
	
	public abstract void run();
	
	public abstract void playCard(Card card, Player target);
	public abstract void draw(GameStack stack);
	public abstract void discard(Card chosenCard);
	public abstract void nextPlayer();
	
}
