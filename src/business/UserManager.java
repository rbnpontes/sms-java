package business;

import java.sql.SQLException;
import java.util.List;

import entities.User;
import repository.DBModel;

public class UserManager{
	private static UserManager instance;
	public UserManager(){
		instance =this;
	}
	public User getUserById(int id) throws SQLException {
		List<DBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users WHERE id="+id, User.class);
		return (User)results.get(0);
	}
	public static UserManager getSingleton() {
		return instance;
	}
}
