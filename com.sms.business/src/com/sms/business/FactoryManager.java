package com.sms.business;

import com.sms.entities.SystemErrors;
/// Class Responsavel por iniciar negocios
public class FactoryManager {
	public static boolean initBusiness() {
		try {
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
