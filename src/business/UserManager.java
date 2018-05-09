package business;

public class UserManager extends Singleton{
	public UserManager() throws Exception {
		super();
	}
	public static UserManager getSingleton() {
		return (UserManager)singleton;
	}
}
