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
	public boolean hasStopped() {
		return mStop;
	}
	public void connect() throws UnknownHostException, IOException {
		mSocket = new Socket(this.mHost,this.mPort);
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
				stop();
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
		if(this.callOnConnected != null)
			this.callOnConnected.run(this, null);
	}

	@Override
	protected void OnDisconnect(JObject socket) {
		if(this.callOnDisconnect != null)
			this.callOnDisconnect.run(this, null);
	}

	@Override
	protected void OnResponse(JObject socket, String message) {
		if(this.callOnResponse != null)
			this.callOnResponse.run(this, null);
	}

}
