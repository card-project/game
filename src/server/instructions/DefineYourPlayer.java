package server.instructions;

import models.players.HumanPlayer;

public class DefineYourPlayer extends Instruction {

	// ------------ ATTRIBUTES ------------ //	
	private HumanPlayer player;
	private static final long serialVersionUID = 4980538934971120807L;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public DefineYourPlayer(HumanPlayer player) {
		this.player = player;
	}
	
	public HumanPlayer getPlayer() {
		return player;
	}
}
