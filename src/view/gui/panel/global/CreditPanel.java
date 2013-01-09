package view.gui.panel.global;

import java.awt.Color;
import java.awt.GraphicsDevice;

import view.gui.panel.BasicPanel;

import controller.MainController;

public class CreditPanel extends BasicPanel {

	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = -6818573712213264495L;
	
	// ------------ CONSTRUCTORS ------------ //
	public CreditPanel(GraphicsDevice c, MainController basic) {
		super(c, basic);
	}
	
	// ------------ METHODS ------------ //
	@Override
	public void initElements() {
		this.setBackground(Color.black);
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
