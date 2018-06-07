package com.sms.client;

import com.sms.business.FactoryManager;
import com.sms.business.GlobalManager;
import com.sms.common.Debug;

public class Application {
	private SystemManager mSystem;
	public void initialize() {
		Debug.log("Iniciando Programa");
		/// Inicia o Gerenciamento do Sistema
		mSystem = new SystemManager(this);
		/// Inicia Negocio
		FactoryManager.initBusiness();
	}
	public void run() {
		/// Verifica se o Ponto de Entrada do Sistema é falso, caso seja, a aplicação será finalizada
		while(mSystem.entryPoint());
		Debug.log("Fim de Execução");
	}
}
