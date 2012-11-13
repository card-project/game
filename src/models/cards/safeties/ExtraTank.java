package models.cards.safeties;


public class ExtraTank extends SafetyCard {
	private ExtraTank() {
	}

	private static class CisternHolder {
		private static final ExtraTank INSTANCE = new ExtraTank();
	}

	public static ExtraTank getInstance() {
		return CisternHolder.INSTANCE;
	}
	
	public String toString() {
		return "Extra Tank";
	}
}
