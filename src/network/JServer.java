package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class JServer extends JSocket {
	private ServerSocket mSocket;
	private Thread serverThread;
	private JSocketCallback callOnConnected = null;
	private JSocketCallback callOnDisconnect = null;
	private JSocketCallback callOnResponse = null;
	public void setOnConnected(JSocketCallback callback) {
		callOnConnected = callback;
	}
	public void setOnDisconnect(JSocketCallback callback) {
		callOnDisconnect = callback;
	}
	public void setOnResponse(JSocketCallback callback) {
		callOnResponse= callback;
	}
	public void connect() throws UnknownHostException, IOException {
		this.mSocket = new ServerSocket(this.mPort);
		serverThread = new Thread(()-> this.update());
		serverThread.start();
	}
	@Override
	protected void update() {
		try {
			while(this.isConnected || !this.stop) {
				Socket socket = mSocket.accept();
				Thread thread = new Thread(()-> {
					JClient client = new JClient();
					client.handle = socket;
					client.thread = Thread.currentThread();
					OnConnect(client);
					try {
						while(!client.stop) {
							DataInputStream in = new DataInputStream(socket.getInputStream());
							String msg = in.readUTF();
							System.out.println(msg);
							OnResponse(client,msg);
						}
					}catch(IOException e) {
						e.printStackTrace();
						OnDisconnect(client);
					}
				});
				thread.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void OnConnect(JClient socket) {
		// TODO Auto-generated method stub
		if(callOnConnected != null)
		{
			JSocketArgs args = new JSocketArgs();
			args.socket = socket;
			callOnConnected.run(this, args);
		}
	}
	@Override
	protected void OnDisconnect(JClient socket) {
		// TODO Auto-generated method stub
		if(callOnDisconnect != null)
		{
			JSocketArgs args = new JSocketArgs();
			args.socket = socket;
			callOnDisconnect.run(this, args);
		}
	}

	@Override
	protected void OnResponse(JClient socket,String message) {
		if(callOnResponse != null)
		{
			JSocketArgs args = new JSocketArgs();
			args.socket = socket;
			args.message = message;
			callOnResponse.run(this, args);
		}
	}
}
