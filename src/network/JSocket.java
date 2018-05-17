package network;

import java.net.Socket;

public abstract class JSocket {
	protected String mHost;
	protected int mPort;
	protected boolean stop=false;
	protected boolean isConnected=false;
	
	public String getHost() {return mHost;}
	public int getPort() {return mPort;}
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
	protected abstract void update();
	protected abstract void OnConnect(JClient socket);
	protected abstract void OnDisconnect(JClient socket);
	protected abstract void OnResponse(JClient socket,String message);
}
