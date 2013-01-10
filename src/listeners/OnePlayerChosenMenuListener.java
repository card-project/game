package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.BasicController;

public class OnePlayerChosenMenuListener implements ActionListener {

	private BasicController c;

	public OnePlayerChosenMenuListener ( BasicController c ) {
		this.c = c;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		this.c.displayOnePlayerView();
	}
}
