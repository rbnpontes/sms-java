package business;

public class MessageManager{
	private static MessageManager instance;
	public MessageManager(){
		instance =this;
	}
	public static MessageManager getSingleton() {
		return instance;
	}
}
