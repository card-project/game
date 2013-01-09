package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.BasicController;

public class BackToMenuListener implements ActionListener {

	// ------------ ATTRIBUTES ------------ //
	
	private BasicController controller;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public BackToMenuListener(BasicController b) {
		this.controller = b;
	}
	
	// ------------ METHODS ------------ //
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.controller.displayMenuView();
	}
	
}
