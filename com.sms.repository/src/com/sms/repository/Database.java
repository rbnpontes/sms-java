package com.sms.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
	private int mPort=5432;
	private String mHost="127.0.0.1";
	private String mUser="postgres";
	private String mPass="123456";
	private String mDatabase="sms";
	private String mDriver="postgresql";
	private String mDriverClass="org.postgresql.Driver";
	private Connection mConnection;
	private boolean isConnected=false;
	public int getPort() {return mPort;}
	public void setPort(int value) throws Exception {
		if(isConnected)
			throw new Exception("Can't change port value, because Database has Connected");
		mPort = value;
	}
	public String getHost() {return mHost;}
	public void setHost(String value) throws Exception {
		if(isConnected)
			throw new Exception("Can't change host value, because Database has Connected");
		mHost = value;
	}
	public String getUser() {
		return mUser;
	}
	public void setUser(String value) throws Exception {
		if(isConnected)
			throw new Exception("Can't change user value, because Database has Connected");
		mUser = value;
	}
	public String getPassword() {
		return this.mPass;
	}
	public void setPassword(String value) throws Exception {
		if(isConnected)
			throw new Exception("Can't change password value, because Database has Connected");
		mPass = value;
	}
	public String getDatabase() {
		return this.mDatabase;
	}
	public void setDatabase(String database) throws Exception {
		if(isConnected)
			throw new Exception("Can't change database value, because Database has Connected");
		mDatabase = database;
	}
	public String getDriver() {
		return this.mDriver;
	}
	public void setDriver(String value) throws Exception {
		if(isConnected)
			throw new Exception("Can't change Driver value, because Database has Connected");
		this.mDriver = value;
	}
	public String getDriverClass() {
		return this.mDriverClass;
	}
	public void setDriverClass(String value) throws Exception {
		if(isConnected)
			throw new Exception("Can't change Driver value, because Database has Connected");
		this.mDriverClass = value;
	}
	
	public String getConnectionString() {
		return String.format("jdbc:%s://%s:%s/%s", 
				mDriver,
				mHost,
				mPort,
				mDatabase);
	}
	public void connect() throws Exception {
		if(isConnected)
			throw new Exception("Can't Connect because Database has Connected.");
		Class.forName(mDriverClass);
		mConnection = DriverManager.getConnection(
				getConnectionString(), 
				getUser(), 
				getPassword());
		isConnected = true;
	}
	public void close() throws Exception {
		if(!isConnected)
			throw new Exception("Can't Close connection because Database has Closed or not Initialized");
		if(mConnection != null)
			mConnection.close();
	}
	public PreparedStatement prepareQuery(String query) throws SQLException {
		mConnection.setAutoCommit(false);
		return mConnection.prepareStatement(query);
	}
	public boolean exec(String query) throws SQLException {
		Statement st = mConnection.createStatement();
		return st.execute(query);
	}
	public boolean exec(PreparedStatement statement){
		try {
			statement.execute();
			mConnection.commit();
			return true;
		}catch(SQLException e) {
			return false;
		}	
	}
	public List<IDBModel> executeQuery(String query, Class<?> klass) throws SQLException {
		List<IDBModel> models = new ArrayList<IDBModel>();
		
		Statement st = mConnection.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			try {
				IDBModel obj = (IDBModel)klass.newInstance();
				obj.fillObject(rs);
				models.add(obj);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return models;
	}
	public List<IDBModel> executeQuery(PreparedStatement statement, Class<?> klass) throws SQLException{
List<IDBModel> models = new ArrayList<IDBModel>();
		
		//Statement st = mConnection.createStatement();
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			try {
				IDBModel obj = (IDBModel)klass.newInstance();
				obj.fillObject(rs);
				models.add(obj);
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return models;
	}
}
