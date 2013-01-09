package view.gui.panel.global;

import java.awt.Color;
import java.awt.GraphicsDevice;

import javax.swing.JLabel;

import view.gui.panel.BasicPanel;

import controller.MainController;

public class WaitingPanel extends BasicPanel {

	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = -6818573712213264495L;
	
	// ------------ CONSTRUCTORS ------------ //
	public WaitingPanel(GraphicsDevice c, MainController basicController) {
		super(c, basicController);
		
		this.initElements();
	}
	
	// ------------ METHODS ------------ //
	@Override
	public void initElements() {
		
		this.setBackground(Color.darkGray);
		this.add(new JLabel("Waiting screen"));
	}
	
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setListeners() {
		// TODO Auto-generated method stub
		
	}

}
