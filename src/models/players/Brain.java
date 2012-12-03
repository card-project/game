package models.players;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import models.cards.Card;
import models.players.strategies.Driver;
import models.players.strategies.Protector;
import models.players.strategies.Roadhog;
import models.players.strategies.Strategy;
import models.players.strategies.Trapper;
import models.stacks.game.GameStack;

public class Brain {

	// ------------ ATTRIBUTES ------------ //

	private LinkedList<Strategy> mind = new LinkedList<Strategy>();
	private AIPlayer player = null;

	// ------------ CONSTRUCTORS ------------ //

	public Brain( AIPlayer p, ArrayList<Player> opponents ) {

		this.player = p;

		int strat = new Random().nextInt() % 4;

		switch ( strat ) {
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

	/**
	 * Choose and return a card to discard.
	 * 
	 * @return {@link Card} the useless card to discard
	 */
	public Card chooseCardToDiscard() {

		Card cardToDiscard = null;

		for ( int i = ( this.mind.size() - 1 ); i >= 0; i-- ) {
			cardToDiscard = this.mind.get( i ).chooseCardToDiscard();
		}

		return cardToDiscard;
	}

	/**
	 * Choose the card to play.
	 * 
	 * @return {@link Card}
	 */
	public Card chooseCardToPlay() {

		boolean nextStrategy = true;
		Iterator<Strategy> list = this.mind.iterator();
		Card c = null;

		while ( list.hasNext() && nextStrategy ) {

			Strategy s = list.next();
			c = s.chooseCardToPlay();

			if ( c != null )
				nextStrategy = true;
		}

		return c;
	}

	/**
	 * In which stack should the {@link AIPlayer} take the card.
	 * 
	 * @return {@link GameStack}
	 */
	public GameStack chooseStackToDraw() {
		return this.mind.getFirst().chooseStackToDraw();
	}

}
