package org.fyf.backend;

public class Main {

	public static void main(String[] args) {
		Thread servermanager = new ServerManager(9000,1000);
		//servermanager.start();
	}

}
