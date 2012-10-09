package models.cards.specials;

public class Cistern extends SpecialCard {
	private Cistern() {
	}

	private static class CisternHolder {
		private static final Cistern INSTANCE = new Cistern();
	}

	public static Cistern getInstance() {
		return CisternHolder.INSTANCE;
	}
}
