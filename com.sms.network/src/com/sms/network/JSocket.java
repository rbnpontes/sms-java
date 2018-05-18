package com.sms.network;

import java.io.IOException;
import java.net.Socket;

public abstract class JSocket {
	protected String mHost;
	protected int mPort;
	protected boolean stop=false;
	protected boolean isConnected=false;
	protected int bufferSize = 1024;
	protected JSocketCallback callOnConnected = null;
	protected JSocketCallback callOnDisconnect = null;
	protected JSocketCallback callOnResponse = null;
	public void setOnConnected(JSocketCallback callback) {
		callOnConnected = callback;
	}
	public void setOnDisconnect(JSocketCallback callback) {
		callOnDisconnect = callback;
	}
	public void setOnResponse(JSocketCallback callback) {
		callOnResponse= callback;
	}
	public String getHost() {return mHost;}
	public int getPort() {return mPort;}
	public int getBufferSize() {return bufferSize;}
	public void setBufferSize(int value) {bufferSize = value;}
	public void setHost(String address) throws Exception {
		if(isConnected)
			throw new Exception("Could not change host, because socket has Initialized");
		this.mHost = address;
	}
	public void setPort(int port) throws Exception {
		if(isConnected)
			throw new Exception("Could not change port, because socket has Initialized");
		this.mPort = port;
	}
	public abstract void send(String str);
	protected abstract void update() throws IOException ;
	protected abstract void OnConnect(JObject socket);
	protected abstract void OnDisconnect(JObject socket);
	protected abstract void OnResponse(JObject socket,String message);
}
