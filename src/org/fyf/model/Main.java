package org.fyf.model;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i=0;i<1000;i++)
		{
			Thread c = new Client(9000+(i%1000),i);
			c.start();
		}

	}

}
