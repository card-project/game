package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSlider;
import javax.swing.JTextField;

import controller.MainController;

public class InitializeGameListener implements ActionListener {

	// ------------ ATTRIBUTES ------------ //

	private MainController controller;
	private JTextField username;
	private JSlider slider;

	public InitializeGameListener ( MainController controller, JTextField usernameField, JSlider slider ) {
		this.controller = controller;
		this.username = usernameField;
		this.slider = slider;
	}

	@Override
	public void actionPerformed( ActionEvent arg0 ) {

		if ( !this.username.getText().isEmpty() ) {
			this.controller.initializeOnePlayerGame( this.username.getText(), this.slider.getValue() );
			this.controller.startGame();
		}

	}

}
