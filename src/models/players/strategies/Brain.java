package models.players.strategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import models.cards.Card;
import models.players.AIPlayer;
import models.players.Player;
import models.stacks.game.DeckStack;
import models.stacks.game.GameStack;

public class Brain implements Strategy {

	// ------------ ATTRIBUTES ------------ //

	private LinkedList<Strategy> mind = new LinkedList<Strategy>();
	private AIPlayer player;
	private Integer gameDistanceGoal;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Create a new brain with different organized strategies.
	 * Their priority depends on their list index.
	 * 
	 * @param owner {@link Player} owning the current {@link Brain}.
	 * @param opponents {@link ArrayList} of {@link Player} defining the other players.
	 */
	public Brain( AIPlayer owner, ArrayList<Player> opponents ) {
		this.player = owner;
		initStrategies( opponents );
	}
	
	// ------------ METHODS ------------ //
	
	private void initStrategies( ArrayList<Player> opponents ) {
		switch ( new Random().nextInt( 3 ) ) {
		case 0:
			this.mind.add( new Driver( this.player ) );
			this.mind.add( new Protector( this.player ) );
			this.mind.add( new Roadhog( this.player, opponents ) );
			break;
		case 1:
			this.mind.add( new Roadhog( this.player, opponents ) );
			this.mind.add( new Driver( this.player ) );
			this.mind.add( new Protector( this.player ) );
			break;
		case 2:
			this.mind.add( new Driver( this.player ) );
			this.mind.add( new Trapper( this.player ) );
			this.mind.add( new Protector( this.player ) );
			break;
		}
	}
	
	@Override
	public GameStack chooseStackToDraw() {
		GameStack chosenStack = null;
		for( Strategy s : this.mind ) {
			if ( chosenStack == null ) {
				chosenStack = s.chooseStackToDraw();
			}
		}
		
		if ( chosenStack == null ) {
			chosenStack =  DeckStack.getInstance();
		}
		
		return chosenStack;
	}

	@Override
	public Card chooseCardToPlay() {
		Card chosenCard = null;
		
		if ( this.player.canPlay( this.player.getOpponents(), this.gameDistanceGoal ) ) {
			for( Strategy s : this.mind ) {
				if ( chosenCard == null ) {
					chosenCard = s.chooseCardToPlay();
				}
			}
		}
		
		return chosenCard;
	}

	@Override
	public Card chooseCardToDiscard() {
		Card cardToDiscard = null;
		
		for ( int i = this.mind.size() - 1 ; i >= 0 ; i-- ) {
			if ( cardToDiscard == null ) {
				cardToDiscard = this.mind.get( i ).chooseCardToDiscard();
			}
		}
		
		return cardToDiscard;
	}

	public Player chooseTargetToAttack() {
		Player chosenTarget = null;
		for( Strategy s : this.mind ) {
			if ( s instanceof Roadhog ) {
				chosenTarget = ( ( Roadhog ) s ).chooseTargetToAttack();
			}
		}
		
		return chosenTarget;
	}
	
	// -------------- GETTERS -------------- //
	
	public LinkedList<Strategy> getMind() {
		return mind;
	}
	
	// ------------ SETTERS ------------ //

	public void setDistanceGoal( Integer goal ) {
		this.gameDistanceGoal = goal;
	}

}
