package models.cards.safeties;

public class DrivingAce extends SafetiesCard {
	private DrivingAce() {
	}

	private static class AceDriverHolder {
		private static final DrivingAce INSTANCE = new DrivingAce();
	}

	public static DrivingAce getInstance() {
		return AceDriverHolder.INSTANCE;
	}
}
