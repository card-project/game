package view;

import java.util.ArrayList;

import org.apache.batik.gvt.text.ArabicTextHandler;

import server.instructions.ConnectedToLounge;

public interface Viewable {
	
	// ------------ METHODS ------------ //
	public abstract void displayMenuView();
	public abstract void displayOnePlayerView();
	public abstract void displayMultiplayerView();
	public abstract void displayGameView();
	public abstract void displayResultView();
	public abstract void displayWaitingRoom();
}
