package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.gui.panel.global.NetworkPanel;

public class RefreshNetworkViewListener implements ActionListener{

	private NetworkPanel panel;
	
	public RefreshNetworkViewListener(NetworkPanel panel) {
		this.panel = panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		panel.setTrees();
	}
	
}