package com.sms.business;

import java.sql.SQLException;
import java.util.List;

import com.sms.entities.*;
import com.sms.repository.IDBModel;

public class ContactManager {
	private static ContactManager instance;

	public ContactManager() {
		instance = this;
	}

	public static ContactManager getSingleton() {
		return instance;
	}

	public void addNewContact(Contact contact) throws SQLException {
		GlobalManager.getDatabase().exec(
				"INSERT INTO contact(id_target,id_owner) VALUES(" + contact.id_target + "," + contact.id_owner + ")");

	}

	public void deleteContactById(int id) throws SQLException {
		GlobalManager.getDatabase().exec("DELETE FROM contact WHERE id=" + id);

	}

	public Contact[] getContacts(int userId) throws SQLException {
		/// Executa a Query onde ira trazer todos os Usuarios registrados no Banco
		List<IDBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM contact WHERE id_owner="+ userId,Contact.class);
		/// Cria um Vetor com o tamanho do resultado das informa��es trazidas no banco
		Contact contact[] = new Contact[results.size()];
		/// Copia todos os dados para o Vetor
		for (int i = 0; i < results.size(); i++)
			contact[i] = (Contact) results.get(i); /// Converte de DBModel para User
		return contact;
		}
	public Contact getContactById(int id) throws SQLException {
		List<IDBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM contacts WHERE id="+id, Contact.class);
		/// Verifica-se a quantidade de resultados retornado do banco � maior que zero
		if(results.size() == 0)
			return null;
		return (Contact)results.get(0);
	}
	public Contact[] getUserContacts(User user) {
		try {
			List<IDBModel> results = GlobalManager.getDatabase().executeQuery(
					"SELECT * FROM contact\r\n" + 
					"INNER JOIN users ON users.id = contact.id_target\r\n" + 
					"WHERE contact.id_owner="+user.id, User.class);
			Contact[] users = new Contact[results.size()];
			/// Fill Models
			for(int i=0;i<users.length;i++)
			{
				User tempUser = (User)results.get(i);
				users[i] = new Contact();
				users[i].id_owner = user.id;
				users[i].id_target = tempUser.id;
				users[i].owner = user;
				users[i].target = tempUser;
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Contact[0];
	}
}
