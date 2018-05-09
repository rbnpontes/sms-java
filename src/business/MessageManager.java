package business;

public class MessageManager extends Singleton{
	public MessageManager() throws Exception {
		super();
	}
	public static MessageManager getSingleton() {
		return (MessageManager)singleton;
	}
}
