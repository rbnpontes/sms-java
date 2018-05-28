package com.sms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sms.repository.IDBModel;

public class ContactInfo implements IDBModel {
	public int id;
	public User owner;
	public User target;
	@Override
	public void fillObject(ResultSet rs) {
		// TODO Auto-generated method stub
		try {
			id = rs.getInt("id");
			owner = new User();
			owner.id = rs.getInt("owner_id");
			owner.name = rs.getString("owner_name");
			owner.username = rs.getString("owner_username");
			
			target = new User();
			target.id = rs.getInt("target_id");
			target.name = rs.getString("target_name");
			target.username = rs.getString("target_username");
		}catch(SQLException e) {
		}
	}

}
