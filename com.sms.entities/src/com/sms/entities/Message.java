package com.sms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.sms.repository.IDBModel;

public class Message implements IDBModel{
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
			id = rs.getInt("id");
			data = rs.getString("msg");
			dateSended = rs.getDate("date_sended");
			
			User userSrc = new User();
			userSrc.id = rs.getInt("src_id");
			userSrc.name = rs.getString("src_name");
			userSrc.username = rs.getString("src_username");
			
			User userDst = new User();
			userDst.id = rs.getInt("dst_id");
			userDst.name = rs.getString("dst_name");
			userDst.username = rs.getString("dst_username");
			
			id_src = userSrc.id;
			id_dst = userDst.id;
			source = userSrc;
			destination = userDst;
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
