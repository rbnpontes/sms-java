package entities;

import java.sql.ResultSet;
import java.sql.SQLException;

import repository.DBModel;

public class User extends DBModel{
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
