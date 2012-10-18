package models.cards.safeties;


public class RightOfWay extends SafetyCard {

	private RightOfWay() {
	}

	private static class EmergencyVehicleHolder {
		private static final RightOfWay INSTANCE = new RightOfWay();
	}

	public static RightOfWay getInstance() {
		return EmergencyVehicleHolder.INSTANCE;
	}
}
