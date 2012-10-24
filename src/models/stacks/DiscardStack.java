package models.stacks;

public class DiscardStack extends GameStack {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1047729087100435802L;

	private DiscardStack() {
	}

	private static class DiscardStackHolder {
		private static final DiscardStack INSTANCE = new DiscardStack();
	}

	public static DiscardStack getInstance() {
		return DiscardStackHolder.INSTANCE;
	}
}
