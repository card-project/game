package views.gui.panel.assets;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import views.gui.panel.Constant;

import controller.MainController;

public abstract class BlackLayer extends JPanel{

	// ------------ ATTRIBUTES ------------ //
	
	protected MainController controller;
	protected Dimension dimension;
	
	private int topLayer = 0;
	private JLayeredPane panel = new JLayeredPane();
	
	protected JPanel blackLayer;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public BlackLayer(int width, int height, MainController controller) {
		
		this.dimension = new Dimension(width, height);
		this.controller = controller;
		
		this.panel.setBounds(0, 0, width, height);
		this.setBounds(0, 0, width, height);
		this.setOpaque(false);
		
		
		blackLayer = new JPanel();
		blackLayer.setBackground(Constant.TRANSLUCIDE_BKDCOLOR);
		blackLayer.setBounds(0, 0, this.dimension.width, this.dimension.height);
		
		this.addLayer(blackLayer);
		
		this.setElements();
		this.add(this.panel);
		
	}
	
	// ------------ METHODS ------------ //
	
	protected void addLayer(JComponent item) {
		this.panel.add(item);
		this.panel.moveToFront(item);
		this.topLayer++;
	}
	
	protected void removeLayer(JComponent item) {
		this.panel.remove(item);
	}
	
	protected void hide(JComponent item) {
		this.panel.moveToBack(item);
	}
	
	protected void show(JComponent item) {
		this.panel.moveToFront(item);
	}
	
	protected abstract void setElements();
}
