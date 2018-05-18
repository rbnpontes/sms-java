import com.sms.entities.User;
import com.sms.network.JObject;

import models.ClientHolder;

import java.util.ArrayList;
import java.util.List;

public class ClientManager {
	private List<ClientHolder> holders;
	private static ClientManager instance;
	public ClientManager() {
		instance = this;
		holders = new ArrayList<ClientHolder>();
	}
	public void addClient(JObject socket) {
		ClientHolder holder = new ClientHolder();
		holder.socket = socket;
		
		this.holders.add(holder);
	}
	public int findClientIndex(JObject socket) {
		for(int i=0;i<holders.size();i++) {
			if(holders.get(i).socket == socket)
				return i;
		}
		return -1;
	}
	public ClientHolder getClient(JObject socket) {
		int index = findClientIndex(socket);
		if(index == -1)
			return null;
		return holders.get(index);
	}
	public void dropClient(JObject socket) {
		int index = findClientIndex(socket);
		if(index == -1)
			return;
		holders.remove(index);
	}
	public static ClientManager getSingleton() {
		if(instance == null)
			instance = new ClientManager();
		return instance;
	}
}
