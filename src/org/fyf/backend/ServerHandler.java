package org.fyf.backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class ServerHandler extends Thread {
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private Group group;
	private int boundary = 531441;
	
	ServerHandler(Socket s,Group g)
	{
		this.socket = s;
		this.group = g;
	}
	public void run()
	{
		setUp();
		//while(true)
		listen();
		shutdown();
	}
	private void shutdown() {
		// TODO Auto-generated method stub
		try {
			this.in.close();
			this.out.close();
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setUp() {
		try {
			this.out = new DataOutputStream(this.socket.getOutputStream());
			this.in = new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void listen()
	{
		try {
			String s = this.in.readUTF();
			String[] data = s.split(",");
			if("create".equals(data[0]))
			{
				//System.out.println("Server :recieved '"+s+"'");
				Random r = new Random();
				int index = r.nextInt(boundary);
				while(this.group.exists(index))
				{
					index = r.nextInt(boundary);
				}
				//System.out.println("Server :"+ this.getName() + ": Group created");
				if(this.group.add(new Group(Integer.valueOf(index))))
				{
					send(index);
				}else
				send(0);
			}else if("join".equals(data[0]))
			{
				//System.out.println("Adding member to group " + data[1]);
				if(this.group.exists(Integer.valueOf(data[1])))
				{
					//System.out.println("Group EXISTS");
					this.group.addMember(Integer.valueOf(data[1]), Integer.valueOf(data[2]));
					String msg = this.group.getMembers(Integer.valueOf(data[1]));
					System.out.println("Sending member:"+msg);
					send(msg);
				}else
					send("Group Does not exist");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void send(int i) {
		try {
			//System.out.println("Server "+getName()+": sending " + i);
			this.out.writeInt(i);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void send(String string) {
		// TODO Auto-generated method stub
		try {
			this.out.writeUTF(string);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
