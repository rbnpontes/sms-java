package business;

import repository.Database;

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
}
