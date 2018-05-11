package business;

import repository.Database;

import java.sql.SQLException;
import java.util.List;

import repository.DBModel;
public class GlobalManager {
	public static int errorCode;
	private static Database mDatabase;
	public static Database getDatabase() {
		return mDatabase;
	}
	public static boolean initDatabase() {
		try {
			mDatabase = new Database();
			mDatabase.setDatabase("testdb");
			mDatabase.connect();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static List<DBModel> executeQuery(String query, Class<?> klass) throws SQLException{
		return getDatabase().executeQuery(query, klass);
	}
}
