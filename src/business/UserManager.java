package business;

import java.sql.SQLException;
import java.util.List;

import entities.User;
import repository.DBModel;

public class UserManager{
	private static UserManager instance;
	public UserManager(){
		instance =this;
	}
	public User[] getUsers() throws SQLException{
		/// Executa a Query onde ira trazer todos os Usuarios registrados no Banco
		List<DBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users", User.class);
		/// Cria um Vetor com o tamanho do resultado das informações trazidas no banco
		User users[] = new User[results.size()];
		/// Copia todos os dados para o Vetor
		for(int i=0;i<results.size();i++)
			users[i] = (User)results.get(i); /// Converte de DBModel para User
		return users;
	}
	public User getUserById(int id) throws SQLException {
		List<DBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users WHERE id="+id, User.class);
		/// Verifica-se a quantidade de resultados retornado do banco é maior que zero
		if(results.size() == 0)
			return null;
		return (User)results.get(0);
	}
	public static UserManager getSingleton() {
		return instance;
	}
}
