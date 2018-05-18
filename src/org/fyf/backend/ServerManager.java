package org.fyf.backend;

import java.util.Vector;

public class ServerManager extends Thread {
	private int port;
	private static Group group;
	private Vector<Server> servers;
	private int nServer;
	ServerManager (int port,int nServer){
		this.port = port;
		this.group = new Group(265720);
		this.nServer = nServer;
		this.servers = new Vector<>();
		this.init();
		
	}
	private void init() {
		for(int i=0;i<this.nServer;i++)
		{
			Thread s = new Server(this.port+i,this.group);
			s.start();
		}
		System.out.println("Server is up and running");
	}
	public void run()
	{	
		while(true)
		{
			try {
				this.sleep(3000);
				System.out.println("Printing all groups");
				this.group.print();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
