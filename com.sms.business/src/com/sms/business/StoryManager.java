package com.sms.business;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.sms.entities.Story;
import com.sms.entities.User;
import com.sms.repository.IDBModel;

///Gerenciamento das Stories
public class StoryManager{
	private static StoryManager instance;
	public StoryManager(){
		instance =this;
	}
	/// Adiciona Story
	public void addStory(Story story) {
		///Roda a Query que adiciona Story
		String query = "INSERT INTO stories(id_owner,message) VALUES (?,?)";
		try {
			PreparedStatement statement = GlobalManager.getDatabase().prepareQuery(query);
			statement.setInt(1, story.id_owner);
			statement.setString(2, story.message);
			GlobalManager.getDatabase().exec(statement);
		}catch(SQLException e) {}
	}
	/// Busca os Stories Recentes
	public Story[] getRecentStories() {
		///Roda a query que busca os stories recentes
		String query = "SELECT \r\n" + 
				"stories.id AS id,\r\n" + 
				"stories.message AS message,\r\n" + 
				"stories.post_date AS post_date,\r\n" + 
				"users.id AS userId,\r\n" + 
				"users.name AS name,\r\n" + 
				"users.username AS username\r\n" + 
				"FROM users\r\n" + 
				"LEFT JOIN stories ON stories.id_owner = users.id\r\n" + 
				"WHERE post_date = CURRENT_DATE\r\n" + 
				"ORDER BY id DESC";
		try {
			List<IDBModel> results = GlobalManager.getDatabase().executeQuery(query, Story.class);
			Story[] stories = new Story[results.size()];
			for(int i=0;i<results.size();i++)
				stories[i] = (Story)results.get(i);
			return stories;
		} catch (SQLException e) {
		}
		return new Story[0];
	}
	public static StoryManager getSingleton() {
		return instance;
	}
}
