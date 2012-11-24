package models.players;

import java.util.LinkedList;
import java.util.Random;

import models.cards.Card;
import models.players.strategies.Driver;
import models.players.strategies.Protector;
import models.players.strategies.Roadhog;
import models.players.strategies.Strategy;
import models.players.strategies.Trapper;
import models.stacks.GameStack;

public class Brain implements Strategy{
	
	// ------------ ATTRIBUTES ------------ //
	
	private LinkedList<Strategy> mind = new LinkedList<Strategy>();
	private AIPlayer player = null;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public Brain(AIPlayer p) {
		
		this.player = p;
		
		int strat = new Random().nextInt()%4;
		
		switch (strat) {
		case 0:
			this.mind.add(new Driver(this.player));
			this.mind.add(new Protector(this.player));
			this.mind.add(new Roadhog(this.player));
			break;
			
		case 1:
			this.mind.add(new Driver(this.player));
			this.mind.add(new Roadhog(this.player));
			this.mind.add(new Protector(this.player));
			break;
			
		case 2:
			this.mind.add(new Roadhog(this.player));
			this.mind.add(new Protector(this.player));
			this.mind.add(new Driver(this.player));
			break;
			
		case 3:
			this.mind.add(new Driver(this.player));
			this.mind.add(new Trapper(this.player));
			this.mind.add(new Roadhog(this.player));
			break;
		}
	}
	
	// ------------ METHODS ------------ //
	
	@Override
	public Card chooseCardToDiscard() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Card chooseCardToPlay() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public GameStack chooseStackToDraw() {
		// TODO Auto-generated method stub
		return null;
	}

}
