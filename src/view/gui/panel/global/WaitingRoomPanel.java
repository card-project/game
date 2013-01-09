package view.gui.panel.global;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;

import javax.swing.JLabel;

import view.gui.panel.BasicPanel;
import controller.MainController;

public class WaitingRoomPanel extends BasicPanel {
	
	// ------------ CONSTRUCTORS ------------ //
	public WaitingRoomPanel(GraphicsDevice c, MainController controller) {
		super(c, controller);
		
		this.initElements();
	}
	
	// ------------ METHODS ------------ //
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void initElements() {
		this.add(new JLabel("Waiting for some players ..."), BorderLayout.CENTER);
	}
	
	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		
	}

}
