package com.sms.entities;

import java.sql.ResultSet;
import java.util.Date;

import com.sms.repository.IDBModel;

public class Story implements IDBModel{
	public int id;
	public String message;
	/// Used for Fetch data in DB
	public int id_owner;
	public Date postDate;
	/// Save Owner Instance
	private User owner;
	public User getOwner() {
		return owner;
	}
	public void setOwner(User user) {
		if(owner == null)
			owner = user;
	}
	@Override
	public void fillObject(ResultSet rs) {
		try {
			this.id = rs.getInt("id");
			this.message = rs.getString("message");
			this.id_owner = rs.getInt("userid");
			this.postDate = rs.getDate("post_date");
			User user = new User();
			user.id = this.id_owner;
			user.name = rs.getString("name");
			user.username = rs.getString("username");
			this.owner = user;
		}catch(Exception e) {}
	}
}
