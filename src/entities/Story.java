package entities;

public class Story {
	public int id;
	public String message;
	/// Used for Fetch data in DB
	public int id_owner;
	/// Save Owner Instance
	private User owner;
	public User getOwner() {
		return owner;
	}
	public void setOwner(User user) {
		if(owner == null)
			owner = user;
	}
}
