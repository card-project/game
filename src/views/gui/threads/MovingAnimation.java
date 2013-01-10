package views.gui.threads;

import java.awt.Point;

import views.gui.panel.assets.CarAsset;
import views.gui.panel.global.GamePanel;
import controller.MainController;

public class MovingAnimation extends Animation {

	// ------------ ATTRIBUTES ------------ //
	
	private CarAsset car;
	private int goal;
	private MainController controller;
	private Thread runner;
	private GamePanel panel;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public MovingAnimation(GamePanel gamePanel, CarAsset asset, int distance, MainController controller) {
		
		this.panel = gamePanel;
		this.car = asset;
		this.goal = distance;
		this.controller = controller;
		
		this.runner = new Thread(this);
		this.runner.start();
	}
	
	@Override
	public void run() {
		
		while(this.car.getLocation().x < this.goal)
		{
			Point newPosition = this.car.getLocation();
			newPosition.x++;
			this.car.setLocation(newPosition);
			
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		this.controller.nextPlayer();
		
	}
	
	@Override
	public void stopAnimation() {
		// TODO Auto-generated method stub
		
	}
	
}
