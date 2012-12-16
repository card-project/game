package models.cards;

/**
 * Card class factory.
 * 
 * Allow lighter/softer dependencies between classes.
 * 
 * @author Simon RENOULT
 * @version 1.2
 */
public class CardFactory {

	public static HazardCard createHazard( CardFamily cf ) {
		switch ( cf ) {
		case Gas: 
			return new HazardCard( cf, CardType.OutOfGas );
		case GoStop: 
			return new HazardCard( cf, CardType.Stop );
		case Speed: 
			return new HazardCard( cf, CardType.SpeedLimit );
		case StateOfCar: 
			return new HazardCard( cf, CardType.Accident);
		case Tire: 
			return new HazardCard( cf, CardType.FlatTire );
		}
		
		return null;
	}

	public static RemedyCard createRemedy( CardFamily cf ) {
		switch ( cf ) {
		case Gas: 
			return new RemedyCard( cf, CardType.Gasoline );
		case GoStop: 
			return new RemedyCard( cf, CardType.GoRoll);
		case Speed: 
			return new RemedyCard( cf, CardType.EndOfLimit );
		case StateOfCar: 
			return new RemedyCard( cf, CardType.Repairs );
		case Tire: 
			return new RemedyCard( cf, CardType.SpareTire );
		}
		
		return null;
	}

	public static DistanceCard createDistance( int value ) {
		switch ( value ) {
		case 25: 
			return new DistanceCard( 25 );
		case 50: 
			return new DistanceCard( 50 );
		case 75: 
			return new DistanceCard( 75 );
		case 100: 
			return new DistanceCard( 100 );
		case 200: 
			return new DistanceCard( 200 );
		}
		
		return null;
	}

	public static SafetyCard createSafety( CardFamily cf ) {
		switch ( cf ) {
		case Gas: 
			return new SafetyCard( cf, CardType.ExtraTank );
		case GoStop: 
			SafetyCard sc1 = new SafetyCard( cf, CardType.RightOfWay );
			sc1.addFamily( CardFamily.Speed );
			return sc1;
		case Speed: 
			SafetyCard sc2 = new SafetyCard( cf, CardType.RightOfWay );
			sc2.addFamily( CardFamily.GoStop );
			return sc2;
		case StateOfCar: 
			return new SafetyCard( cf, CardType.DrivingAce );
		case Tire: 
			return new SafetyCard( cf, CardType.PunctureProof );
		}
		
		return null;
	}
}
