package business;

import entities.SystemErrors;

public class FactoryManager {
	public static boolean initBusiness() {
		try {
			new IOManager();
			new ContactManager();
			new MessageManager();
			new StoryManager();
			new UserManager();
			GlobalManager.initDatabase();
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			GlobalManager.errorCode=SystemErrors.E_NOT_INIT_BI;
			return false;
		}
	}
}
