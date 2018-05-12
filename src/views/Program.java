package views;

import java.sql.SQLException;
import java.util.List;

import business.FactoryManager;
import business.GlobalManager;
import business.IOManager;
import business.UserManager;
import entities.User;
import repository.DBModel;
import samples.Example;

public class Program {
	public static void main(String[] args) {
		/// Caso queira rodar a Classe exemplo, descomente o codigo abaixo
		/// Example.Run();
		if(!FactoryManager.initBusiness())
			return;
		List<DBModel> results;
		System.out.println("");
		try {
			results = GlobalManager.getDatabase().executeQuery("SELECT * FROM users", User.class);
			if(results.size() > 0)
			IOManager.getSingleton().print(((User)results.get(0)).name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}
}
