package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import repository.DBModel;

public class Message extends DBModel{
	public int id;
	/// Source Id User, used for fetch data in DB
	public int id_src;
	/// Destination Id user, used for fetch data in DB
	public int id_dst;
	public String data;
	public Date dateSended = new Date();
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
	@Override
	public void fillObject(ResultSet rs) {
		/// Map this Object
		try {
			id_src = rs.getInt("id_src");
			id_dst = rs.getInt("id_dst");
			data = rs.getString("data");
			dateSended = rs.getDate("date_sended");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
