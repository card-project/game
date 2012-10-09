package models.cards.specials;


public class EmergencyVehicle {
	private static final EmergencyVehicle SINGLETON = new EmergencyVehicle();

	private EmergencyVehicle() {
		if ( SINGLETON != null ) {
			throw new IllegalStateException( "Already instantiated" );
		}
	}
	
	public static EmergencyVehicle getInstance() {
		return SINGLETON;
	}
}
