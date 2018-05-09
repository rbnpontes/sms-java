package entities;

public class Contact {
	public int id;
	/// Used for Fetch data in DB
	public int id_owner;
	/// Used for Fetch data in DB
	public int id_target;
	private User owner;
	private User target;
	
	public User getOwner() {
		return owner;
	}
	public User getTarget() {
		return target;
	}
}
