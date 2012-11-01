package models.cards;

import models.cards.distances.DistanceCard;
import models.cards.hazards.Accident;
import models.cards.hazards.FlatTire;
import models.cards.hazards.OutOfGas;
import models.cards.hazards.SpeedLimit;
import models.cards.hazards.Stop;
import models.cards.remedies.EndOfLimit;
import models.cards.remedies.Gasoline;
import models.cards.remedies.GoRoll;
import models.cards.remedies.Repairs;
import models.cards.remedies.SpareTire;
import models.cards.safeties.DrivingAce;
import models.cards.safeties.ExtraTank;
import models.cards.safeties.PunctureProof;
import models.cards.safeties.RightOfWay;


/**
 * Card class factory.
 * 
 * Allow lighter/softer dependencies between classes.
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public class CardFactory {
	public static Card createCard( CardType ct ) {
		switch ( ct ) {
		// Distances
		case Distance25:
			return new DistanceCard( 25 );
		case Distance50:
			return new DistanceCard( 50 );
		case Distance75:
			return new DistanceCard( 75 );
		case Distance100:
			return new DistanceCard( 100 );
		case Distance200:
			return new DistanceCard( 200 );
		// Hazards
		case Accident:
			return new Accident();
		case FlatTire:
			return new FlatTire();
		case OutOfGas:
			return new OutOfGas();
		case SpeedLimit:
			return new SpeedLimit();
		case Stop:
			return new Stop();
		// Remedies
		case GoRoll:
			return new GoRoll();
		case EndOfLimit:
			return new EndOfLimit();
		case Gasoline:
			return new Gasoline();
		case Repairs:
			return new Repairs();
		case SpareTire:
			return new SpareTire();
		// Safeties
		case DrivingAce:
			return DrivingAce.getInstance();
		case ExtraTank:
			return ExtraTank.getInstance();
		case PunctureProof:
			return PunctureProof.getInstance();
		case RightOfWay:
			return RightOfWay.getInstance();
		default:
			return null;
		}
	}
}
