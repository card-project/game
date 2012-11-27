package models.stacks.game;


public class DiscardStack extends GameStack {
	
	private DiscardStack() {
	}

	private static class DiscardStackHolder {
		private static final DiscardStack INSTANCE = new DiscardStack();
	}

	public static DiscardStack getInstance() {
		return DiscardStackHolder.INSTANCE;
	}
}
