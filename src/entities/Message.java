package entities;

public class Message {
	public int id;
	/// Source Id User, used for fetch data in DB
	public int id_src;
	/// Destination Id user, used for fetch data in DB
	public int id_dst;
	private User source;
	private User destination;
	
	public User getSource() {
		return source;
	}
	public User getDestination() {
		return destination;
	}
	public void setSource(User user) {
		if(source == null)
			source = user;
	}
	public void setDestination(User user) {
		if(destination == null)
			destination = user;
	}
}
