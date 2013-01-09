package view.gui.panel;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.util.ArrayList;

import javax.swing.JPanel;

import view.gui.threads.Animation;
import controller.MainController;

public abstract class BasicPanel extends JPanel{

	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = -5256587972682982525L;
	private Dimension screenDimension;
	protected MainController controller = null;
	
	protected ArrayList<Animation> animationList;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public BasicPanel(GraphicsDevice graphicInformations, MainController basicController) {
		this.controller = basicController;
		this.animationList = new ArrayList<Animation>();
		
		this.screenDimension = new Dimension(graphicInformations.getDisplayMode().getWidth(), graphicInformations.getDisplayMode().getHeight());
		
		//this.initElements();
		this.setDoubleBuffered(true);
	}
	
	// ------------ METHODS ------------ //
	
	public abstract void initElements();
	public abstract void animate();
	public abstract void setListeners();
	
	public void stopAnimation() {
		for (Animation anim : this.animationList) {
			anim.stopAnimation();
		}
	}
	
	public void addAnimation(Animation animation) {
		this.animationList.add(animation);
	}

	// ------------ GETTERS ------------ //
	public Dimension getScreenDimension() {
		return this.screenDimension;
	}
	
	public MainController getController() {
		return controller;
	}
	
}
