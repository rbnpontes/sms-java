package business;

public class ContactManager {
	private static ContactManager instance;
	public ContactManager(){
		instance =this;
	}
	public static ContactManager getSingleton() {
		return instance;
	}
}
