package models.cards;

/**
 * Card class factory.
 * 
 * Allow lighter/softer dependencies between classes.
 * 
 * @author Simon RENOULT
 * @version 1.1
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
			return new HazardCard( CardFamily.StateOfCar, CardType.Accident );
		case FlatTire:
			return new HazardCard( CardFamily.Tire, CardType.FlatTire );
		case OutOfGas:
			return new HazardCard( CardFamily.Gas, CardType.OutOfGas );
		case SpeedLimit:
			return new HazardCard( CardFamily.Speed, CardType.SpeedLimit );
		case Stop:
			return new HazardCard( CardFamily.GoStop, CardType.Stop );
			// Remedies
		case GoRoll:
			return new RemedyCard( CardFamily.GoStop, CardType.GoRoll );
		case EndOfLimit:
			return new RemedyCard( CardFamily.Speed, CardType.EndOfLimit );
		case Gasoline:
			return new RemedyCard( CardFamily.Gas, CardType.Gasoline );
		case Repairs:
			return new RemedyCard( CardFamily.StateOfCar, CardType.Repairs );
		case SpareTire:
			return new RemedyCard( CardFamily.Tire, CardType.SpareTire );
			// Safeties
		case DrivingAce:
			return new SafetyCard( CardFamily.StateOfCar, CardType.DrivingAce );
		case ExtraTank:
			return new SafetyCard( CardFamily.Gas, CardType.ExtraTank );
		case PunctureProof:
			return new SafetyCard( CardFamily.Tire, CardType.PunctureProof );
		case RightOfWay:
			SafetyCard sc = new SafetyCard( CardFamily.GoStop, CardType.RightOfWay );
			sc.addFamily( CardFamily.Speed );
			return sc;
		default:
			return null;
		}
	}
}
