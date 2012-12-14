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
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Brain( AIPlayer p, ArrayList<Player> opponents ) {

		this.player = p;

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
	
	// ------------ METHODS ------------ //
	
	@Override
	public GameStack chooseStackToDraw() {
		
		return null;
	}

	@Override
	public Card chooseCardToPlay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card chooseCardToDiscard() {
		// TODO Auto-generated method stub
		return null;
	}

}
