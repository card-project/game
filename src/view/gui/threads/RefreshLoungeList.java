package view.gui.threads;

import view.gui.panel.global.NetworkPanel;

public class RefreshLoungeList implements Runnable {

	private NetworkPanel panel;
	private boolean stopRefresh = false;
	
	public RefreshLoungeList(NetworkPanel p) {
		
		this.panel = p;
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		
		while(!stopRefresh)
		{
			try {
				this.panel.editTreeStructure();
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void setStopRefresh(boolean stopRefresh) {
		this.stopRefresh = stopRefresh;
	}
	
}
