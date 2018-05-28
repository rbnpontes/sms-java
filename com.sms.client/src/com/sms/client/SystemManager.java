package com.sms.client;

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
		Console.write("Opção: ");
	}
	private void drawSignIn() {
		Console.clear();
		Console.writeLine("##### ENTRAR #####");
		
		/// Prepare Object and Collect Data
		User user = new User();
		user.username = getLabel("Usuário");
		user.password = getLabel("Senha");
		if(RouterManager.tryLogin(user, mApp))
		{
			Console.writeLine("Usuário Autenticado com Sucesso");
			Console.readKey();
			ProfileEngine engine = new ProfileEngine(user);
			engine.run();
		}
		else{
			Console.writeLine("Houve um Error ao se autenticar, verifique se o usuário e senha estão corretos");
			Console.readKey();			
		}
	}
	private void drawSignUp() {
		Console.clear();
		Console.writeLine("##### CADASTRAR #####");
		
		User user = new User();
		
		user.name = getLabel("Nome");
		user.username = getLabel("Usuário");
		user.password = getLabel("Senha");
		if(RouterManager.tryRegister(user, mApp))
		{
			Console.writeLine("Cadastro feito com Sucesso");
			Console.pause();
			ProfileEngine engine = new ProfileEngine(user);
			engine.run();
		}
		else{
			Console.readKey();			
			Console.writeLine("Houve um Error ao registrar o usuário, tente novamente mais tarde!");
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
				Debug.error("Opção Invalida, digite uma das opções listadas.");
				Console.pause();
			}
			break;
		}
		return !stop;
	}
}
