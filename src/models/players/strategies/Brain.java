package models.players.strategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import models.cards.Card;
import models.players.AIPlayer;
import models.players.Player;
import models.stacks.game.GameStack;

public class Brain implements Strategy {

	// ------------ ATTRIBUTES ------------ //

	private LinkedList<Strategy> mind = new LinkedList<Strategy>();
	private AIPlayer player;
	private Integer goal;
	
	// ------------ CONSTRUCTORS ------------ //
	
	/**
	 * Create a new brain with different strategies organized.
	 * Their priority depends on their list index.
	 * 
	 * @param p
	 * @param opponents
	 */
	public Brain( AIPlayer p, ArrayList<Player> opponents ) {
		this.player = p;
		initStrategies( opponents );
	}
	
	// ------------ METHODS ------------ //
	
	private void initStrategies( ArrayList<Player> opponents ) {
		switch ( new Random().nextInt() % 4 ) {
		case 0:
			this.mind.add( new Driver( this.player ) );
			this.mind.add( new Protector( this.player ) );
			this.mind.add( new Roadhog( this.player, opponents ) );
			break;
		case 1:
			this.mind.add( new Driver( this.player ) );
			this.mind.add( new Roadhog( this.player, opponents ) );
			this.mind.add( new Protector( this.player ) );
			break;
		case 2:
			this.mind.add( new Roadhog( this.player, opponents ) );
			this.mind.add( new Protector( this.player ) );
			this.mind.add( new Driver( this.player ) );
			break;
		case 3:
			this.mind.add( new Driver( this.player ) );
			this.mind.add( new Trapper( this.player ) );
			this.mind.add( new Roadhog( this.player, opponents ) );
			break;
		}
	}
	
	@Override
	public GameStack chooseStackToDraw() {
		for( Strategy s : this.mind ) {
			if ( s.chooseStackToDraw() != null ) {
				return s.chooseStackToDraw();
			}
		}
		
		return null;
	}

	@Override
	public Card chooseCardToPlay() {
		if( this.player.canPlay( this.player.getOpponents(), this.goal ) ) {
			for( Strategy s : this.mind ) {
				if ( s.chooseCardToPlay() != null ) {
					return s.chooseCardToPlay();
				}
			}
		}
		
		return null;
	}

	@Override
	public Card chooseCardToDiscard() {
		Card cardToDiscard = null;
		for ( int i = this.mind.size() ; i >= 0 ; i-- ) {
			if ( this.mind.get( i ) != null ) {
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
	
	// ------------ SETTERS ------------ //

	public void setDistanceGoal( Integer goal ) {
		this.goal = goal;
	}

}
