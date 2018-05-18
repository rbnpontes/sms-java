package com.sms.client;
import com.sms.network.*;
public class Application {
	private JClient mClient;
	public void initialize() {
		mClient = new JClient();
		try {
			mClient.setPort(3425);
			mClient.connect();
		} catch (Exception e) {
			e.printStackTrace();
			
			return;
		}
	}
}
