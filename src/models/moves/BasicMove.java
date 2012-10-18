package models.moves;

import models.cards.Card;
import models.cards.distances.Distance200;
import models.cards.distances.Distance25;
import models.cards.distances.Distance50;
import models.cards.distances.DistanceCard;
import models.cards.hazards.Accident;
import models.cards.hazards.FlatTire;
import models.cards.hazards.HazardCard;
import models.cards.hazards.OutOfGas;
import models.cards.hazards.SpeedLimit;
import models.cards.hazards.Stop;
import models.cards.remedies.RemedyCard;
import models.cards.safeties.DrivingAce;
import models.cards.safeties.ExtraTank;
import models.cards.safeties.PunctureProof;
import models.cards.safeties.RightOfWay;
import models.cards.safeties.SafetyCard;
import models.exceptions.IllegalMoveException;
import models.players.Player;
import models.stacks.Stack;

public class BasicMove extends Move {
	private Card cardToPlay;
	private Player source;
	private Player target;
	
	public BasicMove( Player origin ) {
		this.source = origin;
	}
	
	public Card getCardToPlay() {
		return cardToPlay;
	}

	public void setCardToPlay( Card c ) throws IllegalMoveException {
		if ( c instanceof HazardCard ) {
			if ( c instanceof SpeedLimit ) {
				if ( target.getDistanceStack().getFirst() instanceof SpeedLimit ) {
					throw new IllegalMoveException( "Stack already attacked." );
				} else if ( target.getSafetyStack().contains( RightOfWay.getInstance() ) ) {
					throw new IllegalMoveException( "Player is protected." );
				}  
			} else if ( target.getBattleStack().getFirst() instanceof HazardCard ) {
				throw new IllegalMoveException( "Player already attacked." );
			} else if ( target.getSafetyStack().contains( DrivingAce.getInstance() ) 
					&& c instanceof Accident ) {
				throw new IllegalMoveException( "Player is protected." );
			} else if ( target.getSafetyStack().contains( ExtraTank.getInstance() )
				&& c instanceof OutOfGas ) {
				throw new IllegalMoveException( "Player is protected." );
			} else if ( target.getSafetyStack().contains( PunctureProof.getInstance() )
				&& c instanceof FlatTire ) {
				throw new IllegalMoveException( "Player is protected." );
			} else if ( target.getSafetyStack().contains( RightOfWay.getInstance() )
				&& c instanceof Stop ) {
				throw new IllegalMoveException( "Player is protected." );
			}
		} else if ( c instanceof RemedyCard ) {
			if ( ! ( source.getBattleStack().getFirst() instanceof HazardCard ) ) {
				throw new IllegalMoveException( "Stack must be attacked." );
			}
		} else if ( c instanceof DistanceCard ) {
			if ( source.getBattleStack().getFirst() instanceof HazardCard ) {
				throw new IllegalMoveException( "You are attacked." );
			} else if ( ! ( c instanceof Distance25 || c instanceof Distance50 ) 
					&& source.getDistanceStack().contains( /* FIXME SpeedLimit*/ ) ) {
				// TODO
			} else if ( c instanceof Distance200 && source.getDistanceStack().maxNumberOfDistance200IsReached() ) {
				throw new IllegalMoveException( "Maximum number of Distance 200 is reached." );
			}
		} else if ( c instanceof SafetyCard ) {
			
		} 

		this.cardToPlay = c;
	}

	public Player getSource() {
		return source;
	}

	public void setSource( Player source ) {
		this.source = source;
	}

	public Boolean isAnAttack() {
		return this.cardToPlay instanceof HazardCard;
	}

	public void attack( Player target ) throws IllegalMoveException {
		Boolean isAlreadyAttacked = false;
		
		for ( Card c : target.getBattleStack() ) {
			if ( c instanceof HazardCard ) {
				isAlreadyAttacked = true;
			}
		}
		
		for ( Card c : target.getDistanceStack() ) {
			if ( c instanceof SpeedLimit ) {
				isAlreadyAttacked = true;
			}
		}
		
		if ( isAlreadyAttacked ) {
			throw new IllegalMoveException( "This stack is already attacked." );
		} else {
			if ( this.cardToPlay instanceof HazardCard ) {
				Stack.transferCard( cardToPlay, this.source.getHandStack(), target.getBattleStack() );
			} else if ( this.cardToPlay instanceof SpeedLimit ) {
				Stack.transferCard( cardToPlay, this.source.getHandStack(), target.getDistanceStack() );
			}
		}
	}

	public void play() {
		
		
	}
}
