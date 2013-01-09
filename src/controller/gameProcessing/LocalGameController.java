package controller.gameProcessing;

import java.util.ArrayList;

import models.Game;
import models.cards.Card;
import models.exceptions.moveExceptions.AvailableCoupFourreException;
import models.players.AIPlayer;
import models.players.HumanPlayer;
import models.players.Player;
import models.stacks.game.GameStack;
import view.BasicView;
import controller.MainController;
import controller.gamePreparator.OnePlayerGamePreparator;

public class LocalGameController extends GameController {

	private DrawingStepController drawingStepController;
	private PlayingStepController playingStepController;
	private DiscardingStepController discardingStepController;
	
	boolean gameIsOver = false, coupFourre = false;
	
	public LocalGameController(Game model, ArrayList<BasicView> viewList, MainController controller) {
		super(model, viewList, controller);
		
		this.drawingStepController = new DrawingStepController(model);
		this.playingStepController = new PlayingStepController(model);
		this.discardingStepController = new DiscardingStepController(model);
	}
	
	@Override
	public void run() {
		this.start();
	}
	
	private void start() {
		this.game.startGame();
		this.startDrawingStep();
	}
	
	private void startDrawingStep() {
		
		if(this.game.getCurrentPlayer() instanceof HumanPlayer) {
			this.humanDrawingStep();
		}
		else if(this.game.getCurrentPlayer() instanceof AIPlayer) {
			this.AIDrawingStep();
		}
	}
	
	/**
	 * The complete sequence to be executed by a {@link Player}
	 * but without waiting for a human action.
	 */
	private void AIDrawingStep() {
		this.drawCard(this.game.getCurrentPlayer());
		
		if(this.playingStepController.currentPlayerCanPlay())
			this.startPlayingStep();
		else
			this.startDiscardingStep();
		
	}
	
	private void humanDrawingStep() {
		this.game.getCurrentPlayer().fireChooseDeckToDrawEvent();
	}

	@Override
	public void playCard(Card card, Player target) {
		boolean replay;
		
		try {
			 replay = playingStepController.performHumanPlayingStep(card, target);
		} catch (AvailableCoupFourreException e) {
			
			System.out.println("Coup fourré !");
			replay = e.getSafety().playCoupFourre( e.getCoupFourreInitiator(), this.game.getCurrentPlayer(), e.getHazardCardInitiator(), this.game.getGoal() );
			
			if(e.getCoupFourreInitiator() instanceof HumanPlayer ) {
				//this.game.fireCoupFourreAvailable();
			}
			else if (e.getCoupFourreInitiator() instanceof AIPlayer){
				this.drawCard(e.getCoupFourreInitiator());
			}
			
			this.game.setCurrentPlayer(this.game.getIndexOf( e.getCoupFourreInitiator() ));
			System.out.println("Le joueur " + this.game.getCurrentPlayer().getAlias() + " prend la main.");
		}
		
		if(replay)
			this.startDrawingStep();
		else
			this.playerTurnEnded();
		
	}
	
	@Override
	public void draw(GameStack stack) {
		
		drawingStepController.performHumanPlayingStep(stack);
		
		if(this.playingStepController.currentPlayerCanPlay())
		{
			this.startPlayingStep();
		}
		else
			this.startDiscardingStep();
	}
	
	@Override
	public void discard(Card chosenCard) {
		discardingStepController.performHumanDiscardingStep(chosenCard);

		this.playerTurnEnded();
	}
	
	private void startPlayingStep() {
		if(this.game.getCurrentPlayer() instanceof AIPlayer)
			this.AIPlayingStep();
		else
			this.humanPlayingStep();
		
		//this.nextPlayerTurn();
	}
	
	private void startDiscardingStep() {
		if(this.game.getCurrentPlayer() instanceof AIPlayer)
			this.AIDiscardingStep();
		else
			this.humanDiscardingStep();
	}
	
	private void humanDiscardingStep() {
		this.game.fireDiscardCardEvent();
	}

	private void AIDiscardingStep() {
		this.discardingStepController.performAIDiscardingStep();
		
		this.playerTurnEnded();
	}

	private void AIPlayingStep() {
		
		boolean replay;
		try {
			replay = this.playingStepController.performAIPlayingStep();
		} catch (AvailableCoupFourreException e) {
			
			System.out.println("Coup fourré !");
			replay = e.getSafety().playCoupFourre( e.getCoupFourreInitiator(), this.game.getCurrentPlayer(), e.getHazardCardInitiator(), this.game.getGoal() );
			
			if(e.getCoupFourreInitiator() instanceof HumanPlayer ) {
				//this.game.fireCoupFourreAvailable();
			}
			else if (e.getCoupFourreInitiator() instanceof AIPlayer){
				this.drawCard(e.getCoupFourreInitiator());
			}
			
			this.game.setCurrentPlayer(this.game.getIndexOf( e.getCoupFourreInitiator() ));
			System.out.println("Le joueur " + this.game.getCurrentPlayer().getAlias() + " prend la main.");
		}
		
		if(replay)
			this.startDrawingStep();
		else
			this.playerTurnEnded();
	}
	
	private void humanPlayingStep() {
		this.game.firePlayCardEvent();
	}
	
	
	private void playerTurnEnded() {
		this.game.getCurrentPlayer().firePlayerHasPlayedEvent();
	}
	
	public void nextPlayer() {
		if(!this.game.nextPlayer())
		{
			System.out.println("\n++++++++++++++++++++++++++");
			System.out.println(this.game.getCurrentPlayer().toString());
			
			this.startDrawingStep();
		}
	}
	
	private boolean drawCard( Player p ) {
		return this.drawingStepController.run();
	}
	
	public void drawCard(GameStack deck) {
		this.drawingStepController.performHumanPlayingStep(deck);
	}	
	
	/**
	 * Initialize the model for a single player game.
	 * 
	 * @param playerName
	 * @param numberOfOpponents
	 */
	public void initModel(String playerName, int numberOfOpponents) {		
		OnePlayerGamePreparator preparator = new OnePlayerGamePreparator(this.game, this.controller);
		preparator.setPlayerName(playerName);
		preparator.setOpponents(numberOfOpponents);
		preparator.execute();
	}
}
