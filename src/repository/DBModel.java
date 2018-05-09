package repository;

import java.sql.ResultSet;

public abstract class DBModel {
	/// this is used by Database context for Fill fetched data
	/// from Database
	public abstract void fillObject(ResultSet rs);
}
