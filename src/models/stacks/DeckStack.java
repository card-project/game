package models.stacks;


public class DeckStack extends GameStack {
	private DeckStack() { }
	
	private static class DeckStackHolder {
		private final static DeckStack INSTANCE = new DeckStack();
	}
	
	public static DeckStack getInstance() {
		return DeckStackHolder.INSTANCE;
	}
}
