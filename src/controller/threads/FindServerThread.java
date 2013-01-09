package controller.threads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import listeners.LoungeListener;

import events.LoungeFoundEvent;

import view.BasicView;
import view.gui.GameWindow;

public class FindServerThread extends BasicControllerThread {
	
	private boolean keepFinding = true;
	private LoungeListener listener;
	
	public FindServerThread(LoungeListener view) {
		this.listener = view;
		
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		
		MulticastSocket socket;
		
		try {
			
			socket = new MulticastSocket(4446);
			InetAddress address = InetAddress.getByName("230.0.0.1");
			socket.joinGroup(address);
		 
		    DatagramPacket packet;
		     
		    while(this.keepFinding) {
		    	
		    	byte[] buf = new byte[256];
		    	packet = new DatagramPacket(buf, buf.length);
		        socket.receive(packet);
		 
		        String received[] = new String(packet.getData(), 0, packet.getLength()).split(":");
		        
		        if(received.length == 3)
		        	this.listener.loungeFound(new LoungeFoundEvent(this, received[0], received[1], received[2]));
		        
		    }
		 
		    socket.leaveGroup(address);
		    socket.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		this.keepFinding = false;
	}
	
}
