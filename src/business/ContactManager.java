package business;

public class ContactManager extends Singleton {
	public ContactManager() throws Exception {
		super();
	}
	public static ContactManager getSingleton() {
		return (ContactManager)singleton;
	}
}
