package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.MainController;

public class StartGameListener implements ActionListener {

	private MainController controller;
	
	public StartGameListener(MainController controller) {
		this.controller = controller;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.controller.startGame();
	}

}
