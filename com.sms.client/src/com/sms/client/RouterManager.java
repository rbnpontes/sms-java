package com.sms.client;

import com.sms.business.UserManager;
import com.sms.entities.User;

public class RouterManager {
	public static boolean tryLogin(User user, Application app) {
		if(user.username.length() == 0)
			return false;
		if(user.password.length() == 0)
			return false;
		if(user.password.length() < 6)
			return false;
		User tempUser = UserManager.getSingleton().getUserbyUsername(user.username);
		if(tempUser == null)
			return false;
		return tempUser.username.equalsIgnoreCase(user.username) 
				&& tempUser.password.equalsIgnoreCase(user.password);
	}
	public static boolean tryRegister(User user,Application app) {
		if(user.name.length() == 0 && user.username.length() == 0 && user.password.length() == 0)
			return false;
		if(user.password.length() < 6)
			return false;
		UserManager.getSingleton().addUser(user);
		return true;
	}
}
