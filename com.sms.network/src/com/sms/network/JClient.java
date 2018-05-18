package com.sms.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class JClient extends JSocket {
	
	protected Socket mSocket;
	protected Thread mThread;
	protected boolean mStop=false;
	protected JObject mJobject = new JObject();
	public void stop() {mStop = true;}
	public void connect() {
		try {
			mSocket = new Socket(this.mHost,this.mPort);
		}catch(Exception e){
			e.printStackTrace();
		}
		mThread = new Thread(()->{
			mJobject.handle = mSocket;
			mJobject.thread = mThread;
			OnConnect(mJobject);
			try {
				while(!mStop) {
					update();
				}
			}catch(IOException e) {
				e.printStackTrace();
				OnDisconnect(mJobject);
			}
		});
		mThread.start();
	}
	@Override
	public void send(String str) {
		str += '\0';
		try {
			mSocket.getOutputStream().write(str.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void update() throws IOException{
		StringBuilder builder = new StringBuilder();
		int value = 0;
		boolean stillConnected = false;
		boolean hasPing = false;
		
		while((value = mSocket.getInputStream().read()) > 0) {
			stillConnected = true;
			if((char)value=='\n' && builder.length() == 0) {
				hasPing = true;
				break;
			}
			builder.append((char)value);
		}
		if(hasPing)
			return;
		if(!stillConnected)
			mSocket.getOutputStream().write(0x00000);
		OnResponse(mJobject,builder.toString());
	}

	@Override
	protected void OnConnect(JObject socket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnDisconnect(JObject socket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnResponse(JObject socket, String message) {
		// TODO Auto-generated method stub
		
	}

}
