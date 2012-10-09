package models.cards.specials;

public class AceDriver extends SpecialCard {
	private AceDriver() {
	}

	private static class AceDriverHolder {
		private static final AceDriver INSTANCE = new AceDriver();
	}

	public static AceDriver getInstance() {
		return AceDriverHolder.INSTANCE;
	}
}
