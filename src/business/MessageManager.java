package business;
import entities.*;
public class MessageManager{
	private static MessageManager instance;
	public MessageManager(){
		instance =this;
	}
	public void insertMessage(Message message) {
		
	}
	public Message[] getUserMessages(User user) {
		
	}
	public static MessageManager getSingleton() {
		return instance;
	}
}
