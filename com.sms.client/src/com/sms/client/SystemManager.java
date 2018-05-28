package com.sms.client;

import com.sms.business.MessageManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.User;

public class SystemManager {
	private Application mApp;
	public SystemManager(Application app) {
		this.mApp = app;
	}
	private String getLabel(String label) {
		Console.write(label + ": ");
		return Console.readLine();
	}
	private void drawMain() {
		Console.clear();
		Console.writeLine("###########################");
		Console.writeLine("0 - Entrar");
		Console.writeLine("1 - Cadastrar");
		Console.writeLine("2 - Sair");
		Console.writeLine("###########################");
		Console.write("Op��o: ");
	}
	private void drawSignIn() {
		Console.clear();
		Console.writeLine("##### ENTRAR #####");
		
		/// Prepare Object and Collect Data
		User user = new User();
		user.username = getLabel("Usu�rio");
		user.password = getLabel("Senha");
		if(RouterManager.tryLogin(user, mApp))
		{
			Console.writeLine("Usu�rio Autenticado com Sucesso");
			Console.readKey();
			ProfileEngine engine = new ProfileEngine(user);
			engine.run();
		}
		else{
			Console.writeLine("Houve um Error ao se autenticar, verifique se o usu�rio e senha est�o corretos");
			Console.readKey();			
		}
	}
	private void drawSignUp() {
		Console.clear();
		Console.writeLine("##### CADASTRAR #####");
		
		User user = new User();
		
		user.name = getLabel("Nome");
		user.username = getLabel("Usu�rio");
		user.password = getLabel("Senha");
		int code = RouterManager.tryRegister(user, mApp);
		switch(code) {
			case SystemCodes.SIGN_IN_INVALID:
			{
				Console.writeLine("Houve um Error ao registrar o usu�rio, verifique se os campos foram preenchidos corretamento e a senha n�o pode menos que 6 caracteres!");
				Console.readKey();			
			}
				break;
			case SystemCodes.SIGN_IN_EXIST:
			{
				Console.writeLine("N�o foi possivel registrar um novo usu�rio, porque este usu�rio j� est� cadastrado!");
				Console.readKey();
			}
				break;
			case SystemCodes.SIGN_OK:
			{
				Console.writeLine("Cadastro feito com Sucesso");
				Console.pause();
				ProfileEngine engine = new ProfileEngine(user);
				engine.run();
			}
				break;
		}
	}
	public boolean entryPoint() {
		boolean stop = false;
		drawMain();
		int code = Console.readInt();
		Console.clear();
		switch(code) {
			case SystemCodes.MAIN_SIGN_IN:
				drawSignIn();
				break;
			case SystemCodes.MAIN_SIGN_UP:
				drawSignUp();
				break;
			case SystemCodes.MAIN_EXIT:
			{
				stop = true;
				Console.pause();
			}
				break;
			default:
			{
				Debug.error("Op��o Invalida, digite uma das op��es listadas.");
				Console.pause();
			}
			break;
		}
		return !stop;
	}
}
