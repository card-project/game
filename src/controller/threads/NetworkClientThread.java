package controller.threads;

import controller.MainController;

public class NetworkClientThread implements Runnable {
	
	// ------------ ATTRIBUTES ------------ //
	
	private Thread thread;
	
	// ------------ CONSTRUCTORS ------------ //
	
	public NetworkClientThread(MainController controller) {

		this.thread = new Thread(this);
		this.thread.run();
	}
	
	// ------------ METHODS ------------ //
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
