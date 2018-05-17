package network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

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
	private byte[] appendBuffer(byte[] buffer, byte value) {
		byte[] result = new byte[buffer.length+1];
		System.arraycopy(buffer, 0,result , 0, buffer.length);
		result[buffer.length] = value;
		return buffer;
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
						int offset = -1;
						byte buffer[] = new byte[0];
						while(!client.stop) {
							//BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
							StringBuilder builder = new StringBuilder();
							int value = 0;
							/// Why Greater 1, because receive data string is null terminated
							while((value = socket.getInputStream().read()) >= 1)
								builder.append((char)value);
							OnResponse(client,builder.toString());
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
