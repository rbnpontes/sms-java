package com.sms.client;
public class Program {

	public static void main(String[] args) {
		Application app = new Application();
		app.initialize();
		while(!app.hasStopped());
	}

}
