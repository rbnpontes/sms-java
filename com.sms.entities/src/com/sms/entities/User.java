package com.sms.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sms.repository.IDBModel;

public class User implements IDBModel{
	public int id;
	public String name;
	public String username;
	public String ipAddress;
	@Override
	public void fillObject(ResultSet rs) {
		try {
			id = rs.getInt("id");
			name = rs.getString("name");
			username = rs.getString("username");
			ipAddress = rs.getString("ip_address");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
