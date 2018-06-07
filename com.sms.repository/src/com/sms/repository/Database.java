package com.sms.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/// Classe responsavel para Administrar uma Conexão ao banco de Dados 
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
	/// --------------------------------------------------
	/// Funções get & setters da classe
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
	/// --------------------------------------------
	/// Cria a Connection string com os campos deste objeto preenchido
	public String getConnectionString() {
		return String.format("jdbc:%s://%s:%s/%s", 
				mDriver,
				mHost,
				mPort,
				mDatabase);
	}
	/// Faz a tentativa de Conexão ao Banco
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
	/// Fecha a Conexão com o Banco
	public void close() throws Exception {
		if(!isConnected)
			throw new Exception("Can't Close connection because Database has Closed or not Initialized");
		if(mConnection != null)
			mConnection.close();
	}
	/// Prepara uma Query para posteriormente ser executada
	/// O metodo retorna um PreparedStatement, objeto nativo do java
	public PreparedStatement prepareQuery(String query) throws SQLException {
		mConnection.setAutoCommit(false);
		return mConnection.prepareStatement(query);
	}
	/// Executa uma Query ao Banco
	public boolean exec(String query) throws SQLException {
		Statement st = mConnection.createStatement();
		return st.execute(query);
	}
	/// Executa uma Query usando um PreparedStatement
	public boolean exec(PreparedStatement statement){
		try {
			statement.execute();
			mConnection.commit();
			return true;
		}catch(SQLException e) {
			return false;
		}	
	}
	/// Executa uma Query com resultado serializado usando como base o parametro "klass"
	public List<IDBModel> executeQuery(String query, Class<?> klass) throws SQLException {
		/// Instancia o Vetor dinamico
		List<IDBModel> models = new ArrayList<IDBModel>();
		/// Cria o Statement usado para Executar a query
		Statement st = mConnection.createStatement();
		/// Executa a Query e usa o ResultSet para Serializar os Dados
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			try {
				/// Instancia a Class passada pelos parametros
				/// Como padrão, a classe passada pelo parametro implementa uma interface do tipo
				/// IDBModel
				IDBModel obj = (IDBModel)klass.newInstance();
				obj.fillObject(rs); /// Executa o Metodo da Interface responsavel por preencher o objeto usando o result set
				models.add(obj); /// Adiciona ao vetor o objeto criado
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return models;
	}
	/// Executa uma Query usando um PreparedStatement e o resultado é serializado usando como base o parametro "klass"
	public List<IDBModel> executeQuery(PreparedStatement statement, Class<?> klass) throws SQLException{
		List<IDBModel> models = new ArrayList<IDBModel>();
		
		//Statement st = mConnection.createStatement();
		ResultSet rs = statement.executeQuery();
		while(rs.next()) {
			try {
				/// Instancia a Class passada pelos parametros
				/// Como padrão, a classe passada pelo parametro implementa uma interface do tipo
				/// IDBModel
				IDBModel obj = (IDBModel)klass.newInstance();
				obj.fillObject(rs); /// Executa o Metodo da Interface responsavel por preencher o objeto usando o result set
				models.add(obj); /// Adiciona ao vetor o objeto criado
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return models;
	}
}
