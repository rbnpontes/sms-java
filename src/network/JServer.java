package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class JServer extends JSocket {
	private ServerSocket mSocket;
	private Thread serverThread;
	public void connect() throws UnknownHostException, IOException {
		this.mSocket = new ServerSocket(this.mPort);
		serverThread = new Thread(()-> this.Update());
		serverThread.start();
	}
	@Override
	protected void Update() {
		try {
			while(this.isConnected || !this.stop) {
				Socket socket = mSocket.accept();
				OnConnect(socket);
				Thread thread = new Thread(()-> {
					JClient client = new JClient();
					client.socket = socket;
					client.thread = Thread.currentThread();
					client.OnConnect();
					try {
						while(!client.stop) {
							PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
							BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));						
						}
					}catch(IOException e) {
						e.printStackTrace();
						client.OnDisconnect();
					}
				});
				thread.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void OnConnect(Socket socket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnDisconnect(Socket socket) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void OnResponse(String message) {
		// TODO Auto-generated method stub
		
	}
}
