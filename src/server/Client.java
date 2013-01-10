package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import server.instructions.ConnectedToLounge;
import server.instructions.DefineYourPlayer;
import server.instructions.GameStarted;
import server.instructions.Instruction;
import server.instructions.JoinLounge;
import server.instructions.LoungeNoLongerExists;
import server.instructions.YourTurn;
import controller.NetworkGameController;

public class Client implements Runnable {

	// ------------ ATTRIBUTES ------------ //

	private NetworkGameController controller;
	private Socket socketClient = null;
	private Thread thread;

	private boolean stopClient = false;

	// ------------ CONSTRUCTORS ------------ //

	/**
	 * Constructor for the client object.
	 */
	public Client ( Socket client, NetworkGameController controller ) {
		this.socketClient = client;
		this.controller = controller;

		this.thread = new Thread( this );
	}

	public void joinLounge( String loungeName ) {
		this.sendToServer( new JoinLounge( loungeName ) );
		this.thread.start();
	}

	/**
	 * Defining the running method.
	 */
	@Override
	public void run() {

		while ( !stopClient ) {
			Instruction catchInstruction = this.listen();

			if ( catchInstruction instanceof ConnectedToLounge )
				this.controller.displayWaitingRoom( ( ConnectedToLounge ) catchInstruction );
			else if ( catchInstruction instanceof LoungeNoLongerExists )
				System.out.println( "Lounge no longer exists" );
			else if ( catchInstruction instanceof GameStarted )
				this.controller.gameStarted( ( ( GameStarted ) catchInstruction ).getModel() );
			else if ( catchInstruction instanceof DefineYourPlayer )
				this.controller.getMainController().setPlayer( ( ( DefineYourPlayer ) catchInstruction ).getPlayer() );
			else if ( catchInstruction instanceof YourTurn )
				this.controller.startDrawingStep();
			else
				System.out.println( catchInstruction.getClass() );
		}
	}

	/**
	 * The client is waiting for {@link Instruction} from the server.
	 * 
	 * @return {@link Instruction}
	 */
	public Instruction listen() {
		ObjectInputStream input;
		Instruction instruction = null;
		try {
			input = new ObjectInputStream( socketClient.getInputStream() );
			instruction = ( Instruction ) input.readObject();
		} catch ( IOException e ) {

			System.out.println( "Server is now unreachable." );
			this.stopClient();
			e.printStackTrace();

		} catch ( ClassNotFoundException e ) {
			e.printStackTrace();
		}

		return instruction;
	}

	/**
	 * Send an {@link Instruction} to the server.
	 * 
	 * @param instruction
	 */
	public void sendToServer( Instruction instruction ) {
		try {
			ObjectOutputStream output = new ObjectOutputStream( this.socketClient.getOutputStream() );
			output.writeObject( instruction );
			output.flush();
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public void stopClient() {
		this.stopClient = true;
	}

}
