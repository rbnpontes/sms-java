package com.sms.business;

import com.sms.repository.Database;
import java.sql.SQLException;
import java.util.List;
import com.sms.repository.IDBModel;
/// CLass com função global
public class GlobalManager {
	public static int errorCode;
	private static Database mDatabase;
	public static Database getDatabase() {
		return mDatabase;
	}
	///Faz a inicialização do banco
	public static boolean initDatabase() {
		try {
			mDatabase = new Database();
			mDatabase.setDatabase("sms");
			mDatabase.connect();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public static List<IDBModel> executeQuery(String query, Class<?> klass) throws SQLException{
		return getDatabase().executeQuery(query, klass);
	}
}
