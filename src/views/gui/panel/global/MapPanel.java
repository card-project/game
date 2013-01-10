package views.gui.panel.global;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GridLayout;

import javax.swing.JLabel;

import views.gui.panel.BasicPanel;

import controller.MainController;

public class MapPanel extends BasicPanel {

	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = -6818573712213264495L;
	
	// ------------ CONSTRUCTORS ------------ //
	public MapPanel(GraphicsDevice c, MainController controller) {
		super(c, controller);
	}
	
	// ------------ METHODS ------------ //
	@Override
	public void initElements() {
		this.setBackground(Color.WHITE);
		this.setLayout(new GridLayout(1,1));
		this.add(new JLabel("The map will be here"));
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
