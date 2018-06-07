package com.sms.client;

import com.sms.business.MessageManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.User;

/// Primeiro Sistema de Rotas
/// Nesta classe fica responsavel todo o sistema principal do programa
/// Onde o usu�rio poder� navegar para "Cadastro e Login"
/// � nesta classe que tambem esta implementado as Views
public class SystemManager extends View {
	private Application mApp;
	public SystemManager(Application app) {
		this.mApp = app;
	}
	/// Desenha a View Principal
	private void drawMain() {
		Console.clear();
		Console.writeLine("###########################");
		Console.writeLine("0 - Entrar");
		Console.writeLine("1 - Cadastrar");
		Console.writeLine("2 - Sair");
		Console.writeLine("###########################");
		Console.write("Op��o: ");
	}
	/// Desenha a View de Login
	private void drawSignIn() {
		Console.clear();
		Console.writeLine("##### ENTRAR #####");
		
		/// Aqui vamos preparar a coleta de dados para fazer a tentativa de login
		/// no sistema
		User user = new User();
		user.username = labelText("Usu�rio");
		user.password = labelText("Senha");
		/// Faz a tentativa de Login, caso seja sucesso, instancie a View de Perfil
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
	/// Desenha a View de Cadastro
	private void drawSignUp() {
		Console.clear();
		Console.writeLine("##### CADASTRAR #####");
		
		/// Prepara a Coleta de Dados necessaria para o Cadastro
		User user = new User();
		
		user.name = labelText("Nome");
		user.username = labelText("Usu�rio");
		user.password = labelText("Senha");
		/// Faz a tentativa de cadastro, caso ocorra algum erro, um codigo de sa�da sera gerado
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
				/// Caso n�o ocorra nenhumn erro, redirecione o usu�rio para a View de Perfil
				Console.writeLine("Cadastro feito com Sucesso");
				Console.pause();
				ProfileEngine engine = new ProfileEngine(user);
				engine.run();
			}
				break;
		}
	}
	/// Ponto de Entra da primeira View
	public boolean entryPoint() {
		boolean stop = false;
		drawMain(); /// Desenhe a Tela Inicial
		int code = Console.readInt(); /// Ler as Op��es
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
