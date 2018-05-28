package com.sms.business;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.sms.entities.*;
import com.sms.repository.IDBModel;
public class MessageManager{
	private static MessageManager instance;
	public MessageManager(){
		instance =this;
	}
	public void addMessage(Message msg) {
		String query = "INSERT INTO messages (id_src,id_dst,msg) VALUES (?,?,?)";
		try {
			PreparedStatement statement = GlobalManager.getDatabase().prepareQuery(query);
			statement.setInt(1, msg.id_src);
			statement.setInt(2, msg.id_dst);
			statement.setString(3, msg.data);
			GlobalManager.getDatabase().exec(statement);
		}catch(SQLException e) {}
	}
	public Message[] getConversation(User src, User dst) {
		String query = "SELECT\r\n" + 
				"messages.id AS id,\r\n" + 
				"messages.msg AS msg,\r\n" + 
				"messages.date_sended AS date_sended,\r\n" + 
				"src.id AS src_id,\r\n" + 
				"src.name AS src_name,\r\n" + 
				"src.username AS src_username,\r\n" + 
				"dst.id AS dst_id,\r\n" + 
				"dst.name AS dst_name,\r\n" + 
				"dst.username AS dst_username\r\n" + 
				"FROM messages\r\n" + 
				"JOIN users AS src ON messages.id_src = src.id\r\n" + 
				"JOIN users AS dst ON messages.id_dst = dst.id\r\n" + 
				"WHERE\r\n" + 
				"(id_src = "+src.id+" AND id_dst="+dst.id+")\r\n" + 
				"OR\r\n" + 
				"(id_src = "+dst.id+" AND id_dst="+src.id+")\r\n" + 
				"ORDER BY date_sended ASC";
		try {
			List<IDBModel> results = GlobalManager.getDatabase().executeQuery(query, Message.class);
			Message[] messages = new Message[results.size()];
			for(int i=0;i<results.size();i++)
				messages[i] = (Message)results.get(i);
			return messages;
		}catch(SQLException e) {}
		return new Message[0];
	}
	public static MessageManager getSingleton() {
		return instance;
	}
}
