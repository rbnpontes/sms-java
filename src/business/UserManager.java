package business;

public class UserManager{
	private static UserManager instance;
	public UserManager(){
		instance =this;
	}
	public static UserManager getSingleton() {
		return instance;
	}
}
