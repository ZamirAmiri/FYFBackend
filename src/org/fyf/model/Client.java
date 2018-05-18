package org.fyf.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	private Socket s;
	private int port;
	private DataInputStream in;
	private DataOutputStream out;
	private int name;
	Client(int port,int name)
	{
		this.port = port;
		this.name = name;
	}
	public void run() {
		setup();
		work();
		shutdown();
	}
	private void setup()
	{
		try {
			this.s = new Socket(InetAddress.getByName("127.0.0.1"),this.port);
			this.in = new DataInputStream(s.getInputStream());
			this.out = new DataOutputStream(s.getOutputStream());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void work()
	{
		try {
			//System.out.println("Client "+this.getName()+ ": sending data");
			this.out.writeUTF("create,".concat(String.valueOf(this.name)));
			this.out.flush();
			//System.out.println("Before");
			int i = this.in.readInt();
			//System.out.println("after" );
			//System.out.println("Client "+ this.getName() + ": " + i);
			this.shutdown();
			this.setup();
			String msg = "join,".concat(String.valueOf(i)).concat(",").concat(String.valueOf(this.name));
			//System.out.println("Client "+ this.getName() + ": " + msg);
			this.out.writeUTF(msg);
			this.out.flush();
			String mess = this.in.readUTF();
			//System.out.println("after" );
			System.out.println("Client "+ this.getName() + ": "+mess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void shutdown() {
		try {
			this.in.close();
			this.out.close();
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
