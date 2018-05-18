package com.sms.network;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class JServer extends JSocket {
	private ServerSocket mSocket;
	private Thread serverThread;
	private List<JObject> clients = new ArrayList<JObject>();
	public List<JObject> getConnectedClients() {
		return clients;
	}
	public void connect() throws UnknownHostException, IOException {
		this.mSocket = new ServerSocket(this.mPort);
		serverThread = new Thread(()-> {
			try {
				this.update();				
			}catch(IOException e) {
				e.printStackTrace();
			}
		});
		serverThread.start();
	}
	/// Send without specific socket will send for all Clients Connected
	public void send(String buffer) {
		buffer += '\0';
		for(int i=0;i<clients.size();i++) {
			if(clients.get(i).handle.isConnected())
				this.send(buffer,clients.get(i));
		}
	}
	public void send(String buffer,JObject client) {
		buffer += '\0';
		try {
			client.handle.getOutputStream().write(buffer.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private byte[] appendBuffer(byte[] buffer, byte value) {
		byte[] result = new byte[buffer.length+1];
		System.arraycopy(buffer, 0,result , 0, buffer.length);
		result[buffer.length] = value;
		return buffer;
	}
	@Override
	protected void update() throws IOException{
		while(this.isConnected || !this.stop) {
			Socket socket = mSocket.accept();
			Thread thread = new Thread(()-> {
				JObject client = new JObject();
				client.handle = socket;
				client.thread = Thread.currentThread();
				OnConnect(client);
				try {
					while(!client.stop) {
						//BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						StringBuilder builder = new StringBuilder();
						int value = 0;
						/// Why Greater 1, because receive data string is null terminated
						boolean stillConnected = false;
						boolean hasPing = false;
						/// if connection is still connected, socket will wait Client response
						/// if not, the code will bypass this loop
						while((value = socket.getInputStream().read()) > 0)
						{
							/// if value is new line and stored values is greater than zero, its a ping request
							stillConnected = true;									
							if((char)value == '\n' && builder.length() == 0)
							{
								hasPing = true;
								break;
							}
							builder.append((char)value);
						}
						/// if ping, ignore loop
						if(hasPing)
							continue;
						/// Sending ping request
						if(!stillConnected) 
							socket.getOutputStream().write((byte)'\n');
						OnResponse(client,builder.toString());
					}
				}catch(IOException e) {
					e.printStackTrace();
					OnDisconnect(client);
				}
			});
			thread.start();
		}
	}
	@Override
	protected void OnConnect(JObject socket) {
		// TODO Auto-generated method stub
		if(callOnConnected != null)
		{
			JSocketArgs args = new JSocketArgs();
			args.socket = socket;
			callOnConnected.run(this, args);
		}
	}
	@Override
	protected void OnDisconnect(JObject socket) {
		// TODO Auto-generated method stub
		if(callOnDisconnect != null)
		{
			JSocketArgs args = new JSocketArgs();
			args.socket = socket;
			callOnDisconnect.run(this, args);
		}
	}

	@Override
	protected void OnResponse(JObject socket,String message) {
		if(callOnResponse != null)
		{
			JSocketArgs args = new JSocketArgs();
			args.socket = socket;
			args.message = message;
			callOnResponse.run(this, args);
		}
	}
}
