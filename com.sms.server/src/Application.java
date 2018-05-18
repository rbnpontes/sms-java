import com.sms.business.FactoryManager;
import com.sms.network.*;
public class Application {
	protected JServer server;
	public void initialize() {
		/// Init FactoryManager
		FactoryManager.initBusiness();
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
		ClientManager.getSingleton().addClient(args.socket);
	}
	protected void OnDisconnected(JSocket sender, JSocketArgs args) {
		ClientManager.getSingleton().dropClient(args.socket);
	}
	protected void OnResponse(JSocket sender, JSocketArgs args) {
		ApplicationDispatcher.HandleResponse(args.message,args.socket,server);
	}
}
