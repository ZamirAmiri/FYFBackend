package org.fyf.backend;

import java.io.IOException;
import java.net.ServerSocket;

public class Server extends Thread {
	private ServerSocket socket;
	private int port;
	private Group group;
	
	Server(int port,Group group){
		this.port = port;
		this.group = group;
	}
	public void run()
	{
		startup();
		while(true)
		{
			listen();
		}
	}
	private void startup()
	{
		try {
			//System.out.println("Server "+this.port +": Created");
			this.socket = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listen()
	{
		
		try {
			Thread handler = new ServerHandler(this.socket.accept(),this.group);
			//System.out.println("Server "+this.port +": Connection established");
			handler.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
