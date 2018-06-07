package com.sms.business;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.sms.entities.Response;
import com.sms.entities.User;
import com.sms.repository.IDBModel;
/// Gerenciamento de Usuário
public class UserManager{
	private static UserManager instance;
	public UserManager(){
		instance =this;
	}
	/// Esta função ira testar as credenciais
	/// Caso a função seja um sucesso, retorne um response object
	/// que será enviado para o server
	public Response<User> tryLogin(User user){
		Response<User> result = new Response<User>();
		/// Não implementado
		return result;
	}
	/// Esta função ira tentar registrar um Usuário
	/// caso a função seja um sucesso, retorne um response object
	/// que será enviado para o server.
	public Response<User> tryRegister(User user){
		Response<User> result = new Response<User>();
		/// Não implementado
		return result;
	}
	public boolean isExist(int id) {
		try {
			return getUserById(id) != null;
		}catch(SQLException e) {
			return false;
		}
	}
	public boolean isExist(String username) {
		User user = getUserbyUsername(username);
		return user != null;
	}
	/// Atualiza o Nome do Usuário
	public void updateUserName(String name,int userid) {
		/// Roda a Query que atualiza o nome do Usuário
		String query = "UPDATE users SET name=? WHERE id=?";
		try {
			PreparedStatement statement = GlobalManager.getDatabase().prepareQuery(query);
			statement.setString(1, name);
			statement.setInt(2, userid);
			GlobalManager.getDatabase().exec(statement);
		}catch(SQLException e) {
		}
	}
	///Procurar usuários usando seu username e senha
	public User retrieveUserByUser(User user) {
		/// Roda a Query pra procurar usuários usando seu username e senha
		String query = "SELECT * FROM users\r\n" + 
				"WHERE\r\n" + 
				"username = ? AND password = MD5(?)";
		try {
			PreparedStatement statement = GlobalManager.getDatabase().prepareQuery(query);
			statement.setString(1, user.username);
			statement.setString(2, user.password);

			List<IDBModel> results = GlobalManager.getDatabase().executeQuery(statement, User.class);
			/// Verifica-se a quantidade de resultados retornado do banco é maior que zero
			if(results.size() > 0)
				return (User)results.get(0);
		}catch(SQLException e)
		{
		}
		return null;
	}
	///Adiciona um Novo usuários
	public void addUser(User user) {
		///Roda a Query que adiciona um novo usuário
		String query = "INSERT INTO users (name,username,password) VALUES (?,?,MD5(?))";
		try {
			PreparedStatement statement = GlobalManager.getDatabase().prepareQuery(query);
			statement.setString(1, user.name);
			statement.setString(2, user.username);
			statement.setString(3, user.password);
			GlobalManager.getDatabase().exec(statement);
		}catch(SQLException e)
		{
		}
	}
	/// Busca todos os Usuários cadastrados
	public User[] getUsers() throws SQLException{
		/// Executa a Query onde ira trazer todos os Usuarios registrados no Banco
		List<IDBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users", User.class);
		/// Cria um Vetor com o tamanho do resultado das informações trazidas no banco
		User users[] = new User[results.size()];
		/// Copia todos os dados para o Vetor
		for(int i=0;i<results.size();i++)
			users[i] = (User)results.get(i); /// Converte de DBModel para User
		return users;
	}
	/// Busca o Usuário através do id
	public User getUserById(int id) throws SQLException {
		List<IDBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users WHERE id="+id, User.class);
		/// Verifica-se a quantidade de resultados retornado do banco é maior que zero
		if(results.size() == 0)
			return null;
		return (User)results.get(0);
	}
	///Busca o Usuário através do username
	public User getUserbyUsername(String username) {
		List<IDBModel> results;
		try {
			results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users WHERE username='"+username+"'", User.class);
			/// Verifica-se a quantidade de resultados retornado do banco é maior que zero
			if(results.size() == 0)
				return null;
			return (User)results.get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static UserManager getSingleton() {
		return instance;
	}
}
