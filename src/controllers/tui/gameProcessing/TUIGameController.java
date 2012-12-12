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
 * @version 0.5.4
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

		boolean gameIsOver = false, replay = false;
		int currentPlayerIndex = firstPlayerIndex;
		
		do {
			
			this.showTitle( currentPlayer );
			this.drawCard( currentPlayer );
			
			replay = this.playCard( currentPlayer );
			
			this.discardCard( currentPlayer );

			gameIsOver = currentPlayer.getDistanceStack().getTraveledDistance() == currentGame.getGoal();
			
			if ( ! gameIsOver && ! replay ) {
				currentPlayerIndex = ( ++currentPlayerIndex > currentGame.getPlayers().length - 1 ) ? 0 : currentPlayerIndex ;
				currentPlayer = currentGame.getPlayers()[currentPlayerIndex];
			}
		} while ( ! gameIsOver );
		
		tui.inform( currentPlayer.getAlias() + " has won !" );
	}

	private void showTitle( Player p ) {
		int distance = p.getDistanceStack().getTraveledDistance();
		
		String status =  p.hasStarted() ? "Started" : "Not started";
		status += "/" + ( p.isSlowed() ? "Slowed" : "Not slowed" );
		status += "/" + (p.isAttacked() ? p.getBattleStack().peek().toString() : "Not attacked" );
		
		this.tui.title( "TURN OF " + p.getAlias() + " - " + distance + "km - " + status );
	}
	
	private void drawCard( Player p ) {

		this.tui.inform( '\n' + "-- > DRAWING" + '\n' );
		this.drawingStepController.setCurrentPlayer( p );
		this.drawingStepController.run();		
	}
	
	private boolean playCard( Player p ) {
		tui.inform( '\n' + "-- > PLAYING" + '\n');
		
		tui.tickBox( p.getBattleStack().initialGoRollIsPlayed(), "Initial GoRoll status." + '\n' );
		
		tui.tickBox( p.isSlowed(), "Speed limitation." + '\n' );
		
		String attacked = p.isAttacked() ? p.getBattleStack().peek().toString() : "Not attacked";
		tui.tickBox( p.isAttacked(), attacked + '\n' );
		
		String safetyList = p.getSafetyStack().isEmpty() ? "No safety." : p.getSafetyStack().toString(); 
		tui.tickBox( ! p.getSafetyStack().isEmpty(), safetyList + '\n' );
		
		playingStepController.setCurrentPlayer( p );
		
		return playingStepController.run();		
	}
	
	private void discardCard( Player p ) {
		if ( p.getHandStack().size() > HandStack.MAX_CARD_NB ) {
			tui.inform( '\n' + "-- > DISCARDING STEP" + '\n' );
			discardingStepControler.setCurrentPlayer( p );
			discardingStepControler.run();
		}		
	}

}
