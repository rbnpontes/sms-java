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
			return true;
		}catch(Exception e) {
			GlobalManager.errorCode=SystemErrors.E_NOT_INIT_BI;
			return false;
		}
	}
}
