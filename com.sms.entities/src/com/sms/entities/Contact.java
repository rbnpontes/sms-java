package com.sms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sms.repository.IDBModel;

public class Contact implements IDBModel {
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

	@Override
	public void fillObject(ResultSet rs) {
		try {
			id = rs.getInt("id");
			id_target = rs.getInt("id_target");
			id_owner = rs.getInt("id_owner");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
