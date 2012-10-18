package models.cards;

import models.cards.distances.Distance100;
import models.cards.distances.Distance200;
import models.cards.distances.Distance25;
import models.cards.distances.Distance50;
import models.cards.distances.Distance75;
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


public class CardFactory {
	public static Card createCard( CardType ct ) {
		switch ( ct ) {
		// Distances
		case Distance25:
			return new Distance25();
		case Distance50:
			return new Distance50();
		case Distance75:
			return new Distance75();
		case Distance100:
			return new Distance100();
		case Distance200:
			return new Distance200();
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
