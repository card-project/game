package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.BasicController;

public class MultiplayerChosenMenuListener implements ActionListener {

	private BasicController c;

	public MultiplayerChosenMenuListener ( BasicController c ) {
		this.c = c;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		this.c.displayMultiplayerView();
	}
}
