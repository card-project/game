package models.cards.specials;

public class EmergencyVehicle extends SpecialCard {

	private EmergencyVehicle() {
	}

	private static class EmergencyVehicleHolder {
		private static final EmergencyVehicle INSTANCE = new EmergencyVehicle();
	}

	public static EmergencyVehicle getInstance() {
		return EmergencyVehicleHolder.INSTANCE;
	}
}
