package com.sms.client;

import com.sms.business.FactoryManager;
import com.sms.business.GlobalManager;
import com.sms.common.Debug;

public class Application {
	private SystemManager mSystem;
	public void initialize() {
		Debug.log("Iniciando Programa");
		mSystem = new SystemManager(this);
		FactoryManager.initBusiness();
	}
	public void run() {
		while(mSystem.entryPoint());
	}
}
