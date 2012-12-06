package controllers.tui.gameProcessing;

import models.Game;
import models.players.Player;
import models.stacks.player.HandStack;
import views.tui.TUIGameView;

/**
 * TUI (Textual User Interface) game view.
 * 
 * Allow users to play the game.
 * 
 * @author Simon RENOULT
 * @version 0.4.4
 *
 */
public class TUIGameController {

	// ------------ ATTRIBUTES ------------ //
	
	private TUIGameView tui;
	private Game currentGame;
	
	private DrawingStepController drawingStepController;
	private PlayingStepController playingStepController;
	private DiscardingStepController discardingStepControler;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Build the unique TUIGameController object and assign the
	 * {@link TUIGameView} and {@link Game} defined in the mother
	 * class to current object attributes.
	 * 
	 * @param tuiGame {@link TUIGameView} to work on.
	 * @param currentGame {@link Game} to work on.
	 */
	public TUIGameController( TUIGameView gv, Game g ) {
		this.tui = gv;
		this.currentGame = g;
		
		this.drawingStepController = new DrawingStepController( this.tui, this.currentGame );
		this.playingStepController = new PlayingStepController( this.tui, this.currentGame );
		this.discardingStepControler = new DiscardingStepController( this.tui, this.currentGame );
	}
	
	// ------------ METHODS ------------ //

	/**
	 * Unique public object method, it allows the user
	 * to play the game. 
	 * 
	 * @param firstPlayerIndex Index of the first Player.
	 */
	public void run( int firstPlayerIndex ) {
		this.playCurrentGame( firstPlayerIndex );
	}
	
	/**
	 * Main game loop. 
	 * Chain the drawing, playing and discarding steps.
	 * 
	 * @param firstPlayerIndex Index of the first player.
	 */
	public void playCurrentGame( int firstPlayerIndex ) {
		
		Player currentPlayer = currentGame.getPlayers()[firstPlayerIndex];

		boolean gameIsOver = false;
		int currentPlayerIndex = firstPlayerIndex;
		
		do {
			int distance = currentPlayer.getDistanceStack().getTravelledDistance();
			
			// STEP 0 : Show player's turn name.
			
			tui.title( "TURN OF " + currentPlayer.getAlias() + " - " + distance + "km"  );
			
			// STEP 2 : draw a card
			tui.inform( '\n' + "-- > DRAWING" + '\n' );
			drawingStepController.setCurrentPlayer( currentPlayer );
			drawingStepController.run();
			
			// STEP 3 : play a card
			tui.inform( '\n' + "-- > PLAYING" + '\n');
			
			tui.tickBox( currentPlayer.getBattleStack().initialGoRollIsPlayed(), "Initial GoRoll status." + '\n' );
			
			tui.tickBox( currentPlayer.isSlowed(), "Speed limitation." + '\n' );
			
			String attacked = currentPlayer.isAttacked() ? currentPlayer.getBattleStack().peek().toString() : "Not attacked";
			tui.tickBox( currentPlayer.isAttacked(), attacked + '\n' );
			
			String safetyList = currentPlayer.getSafetyStack().isEmpty() ? "No safety." : currentPlayer.getSafetyStack().toString(); 
			tui.tickBox( ! currentPlayer.getSafetyStack().isEmpty(), safetyList + '\n' );
			
			playingStepController.setCurrentPlayer( currentPlayer );
			playingStepController.run();
			
			// STEP 4 : discard a card
			if ( currentPlayer.getHandStack().size() > HandStack.MAX_CARD_NB ) {
				tui.inform( '\n' + "-- > DISCARDING STEP" + '\n' );
				discardingStepControler.setCurrentPlayer( currentPlayer );
				discardingStepControler.run();
			}
			
			// STEP 5 : check if game is over
			gameIsOver = currentPlayer.getDistanceStack().getTravelledDistance() == currentGame.getGoal();
			
			// STEP 6 : switch to next player
			if ( ! gameIsOver ) {
				currentPlayerIndex = ( ++currentPlayerIndex > currentGame.getPlayers().length - 1 ) ? 0 : currentPlayerIndex ;
				currentPlayer = currentGame.getPlayers()[currentPlayerIndex];
			}
			
		} while ( ! gameIsOver );
		
		tui.inform( currentPlayer.getAlias() + " has won !" );
	}
}
