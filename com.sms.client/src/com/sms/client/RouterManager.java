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
		User tempUser = UserManager.getSingleton().retrieveUserByUser(user);
		if(tempUser == null)
			return false;
		return true;
	}
	public static int tryRegister(User user,Application app) {
		if(user.name.length() == 0 && user.username.length() == 0 && user.password.length() == 0)
			return SystemCodes.SIGN_IN_INVALID;
		if(user.password.length() < 6)
			return SystemCodes.SIGN_IN_INVALID;
		if(UserManager.getSingleton().isExist(user.username))
			return SystemCodes.SIGN_IN_EXIST;
		UserManager.getSingleton().addUser(user);
		return SystemCodes.SIGN_OK;
	}
}
