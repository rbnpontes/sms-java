package business;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import entities.*;
import repository.DBModel;
public class MessageManager{
	private static MessageManager instance;
	public MessageManager(){
		instance =this;
	}
	public void addMessage(Message message) throws SQLException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String query = String.format("INSERT INTO messages (id_src,id_dst,data,date_creation) VALUES (%s,%s,%s,%s)",
				message.id_src,
				message.id_dst,
				message.data,
				formatter.format(message.dateSended));
		GlobalManager.getDatabase().exec(query);
	}
	public Message[] getUserMessages(int userId) throws SQLException {
		/// Executa a Query onde ira trazer todos os Usuarios registrados no Banco
		List<DBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM messages", Message.class);
		/// Cria um Vetor com o tamanho do resultado das informações trazidas no banco
		Message users[] = new Message[results.size()];
		/// Copia todos os dados para o Vetor
		for(int i=0;i<results.size();i++)
			users[i] = (Message)results.get(i); /// Converte de DBModel para User
		return users;
	}
	public static MessageManager getSingleton() {
		return instance;
	}
}
