package models.cards;

/**
 * Type a {@link Card} can have.
 * 
 * Exclusively used to get the card name
 * 
 * @author Simon RENOULT
 * @version 1.0
 */
public enum CardType {
	// Distances
	Distance25, Distance50, Distance75, Distance100, Distance200, 
	// Hazards
	Accident, FlatTire, OutOfGas, SpeedLimit, Stop,
	// Remedies
	GoRoll, EndOfLimit, Gasoline, Repairs, SpareTire,
	// Safeties
	DrivingAce, ExtraTank, PunctureProof, RightOfWay;
}
