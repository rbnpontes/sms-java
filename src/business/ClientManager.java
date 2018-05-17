package business;
import entities.User;

import java.util.ArrayList;
import java.util.List;

import entities.ClientHolder;
public class ClientManager {
	private List<ClientHolder> holders;
	private static ClientManager instance;
	public ClientManager() {
		this.instance = this;
		holders = new ArrayList<ClientHolder>();
	}
	public static ClientManager getSingleton() {
		return instance;
	}
}
