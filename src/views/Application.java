package views;
import network.*;
public class Application {
	protected JServer server;
	public void initialize() {
		server = new JServer();
		server.setOnConnected((sender,args)->{OnConnected(sender,args);});
		server.setOnDisconnect((sender,args)->{OnConnected(sender,args);});
		server.setOnResponse((sender,args)->{OnConnected(sender,args);});
		try {
			server.setPort(3425);
			server.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	protected void OnConnected(JSocket sender, JSocketArgs args) {
		
	}
	protected void OnDisconnected(JSocket sender, JSocketArgs args) {
		
	}
	protected void OnResponse(JSocket sender, JSocketArgs args) {
		
	}
}
