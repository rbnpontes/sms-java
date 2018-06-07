package com.sms.business;

import java.sql.SQLException;
import java.util.List;

import com.sms.entities.*;
import com.sms.repository.IDBModel;
///Contact Manager Gerencia os Contatos
public class ContactManager {
	private static ContactManager instance;
	
	public ContactManager() {
		instance = this;
	}
	
	public static ContactManager getSingleton() {
		return instance;
	}
	/// Adiciona os contatos
	public void addNewContact(Contact contact) throws SQLException {
		///Roda a query de inserir contatos
		GlobalManager.getDatabase().exec(
				"INSERT INTO contact (id_target,id_owner)\r\n" + 
				"VALUES\r\n" + 
				"("+contact.id_target+","+contact.id_owner+"),\r\n" + 
				"("+contact.id_owner+","+contact.id_target+")");
	}
	/// Remove os contatos pelo id
	public void deleteContactById(int id) throws SQLException {
		///Roda a query de deletar contatos pelo id
		GlobalManager.getDatabase().exec("DELETE FROM contact WHERE id=" + id);
	}
	///Remove os contatos pelo objeto
	public void deleteContact(Contact cnt) throws SQLException{
		///Roda a query de deletar contatos pelo objeto
		String query = "DELETE FROM contact\r\n" + 
				"WHERE\r\n" + 
				"(id_target = "+cnt.id_target+" AND id_owner = "+cnt.id_owner+")\r\n" + 
				"OR\r\n" + 
				"(id_target = "+cnt.id_owner+" AND id_owner= "+cnt.id_target+")";
		GlobalManager.getDatabase().exec(query);
	}/// Busca contatos pelo id de um Usuário
	public Contact[] getContacts(int userId) throws SQLException {
		/// Executa a Query onde ira trazer todos os Usuarios registrados no Banco
		List<IDBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM contact WHERE id_owner="+ userId,Contact.class);
		/// Cria um Vetor com o tamanho do resultado das informações trazidas no banco
		Contact contact[] = new Contact[results.size()];
		/// Copia todos os dados para o Vetor
		for (int i = 0; i < results.size(); i++)
			contact[i] = (Contact) results.get(i); /// Converte de DBModel para User
		return contact;
		}/// Buscar contatos pelo proprio id
	public Contact getContactById(int id) throws SQLException {
		List<IDBModel> results = GlobalManager.getDatabase().executeQuery("SELECT * FROM contacts WHERE id="+id, Contact.class);
		/// Verifica-se a quantidade de resultados retornado do banco é maior que zero
		if(results.size() == 0)
			return null;
		return (Contact)results.get(0);
	}/// Busca contatos de um Usuário
	public Contact[] getUserContacts(User user) {
		// Roda a Query para buscar contatos de um usuário
		String query = "SELECT\r\n" + 
				"contact.id AS id,\r\n" + 
				"owner.id AS owner_id,\r\n" + 
				"owner.name AS owner_name,\r\n" + 
				"owner.username AS owner_username,\r\n" + 
				"target.id AS target_id,\r\n" + 
				"target.name AS target_name,\r\n" + 
				"target.username AS target_username\r\n" + 
				"FROM contact\r\n" + 
				"INNER JOIN users AS target ON target.id = contact.id_target\r\n" + 
				"INNER JOIN users AS owner ON owner.id = contact.id_owner\r\n" + 
				"WHERE\r\n" + 
				"owner.id = "+user.id;
		try {
			//Consulta e preenche os valores no objeto
			List<IDBModel> results = GlobalManager.getDatabase().executeQuery(query, ContactInfo.class);
			Contact[] contacts = new Contact[results.size()];
			/// Fill Models
			for(int i=0;i<results.size();i++)
			{
				ContactInfo cntInfo = (ContactInfo)results.get(i);
				contacts[i] = new Contact();
				contacts[i].id = cntInfo.id;
				contacts[i].id_owner = cntInfo.owner.id;
				contacts[i].id_target = cntInfo.target.id;
				contacts[i].owner = cntInfo.owner;
				contacts[i].target = cntInfo.target;
			}
			return contacts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Contact[0];
	}
}
