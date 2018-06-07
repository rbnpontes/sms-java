package com.sms.client;

import com.sms.business.ContactManager;
import com.sms.business.MessageManager;
import com.sms.business.UserManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Contact;
import com.sms.entities.Message;
import com.sms.entities.User;

public class ProfileEngine extends View{
	/// Usuario Logado, utilizado para não fazer multiplas requisições ao Banco
	private User mLogged;
	/// Lista de Contatos do Usuário logado
	private Contact[] mContactList;
	public ProfileEngine(User user) {
		/// Procura o Usuário autenticado no Sistema
		mLogged= UserManager.getSingleton().getUserbyUsername(user.username);
		Debug.log("Carregando Lista de Contatos");
		/// Carregue a sua Lista de Contatos
		mContactList = ContactManager.getSingleton().getUserContacts(mLogged);
	}
	/// Função usada para Desenhar a ultima mensagem enviada para este Usuário
	private void drawLastMessage() {
		Message msg = MessageManager.getSingleton().getLastMessage(mLogged);
		if(msg == null) /// Caso não tenha nenhuma mensagem, não desenhe nada
			return;
		Console.writeLine(String.format("Ultima Mensagem - (%s): %s", msg.getSource().name,msg.data));
	}
	/// Desenha a View de Perfil
	private void drawProfile() {
		Console.writeLine("###################################");
		Console.writeLine(String.format("- %s -", mLogged.name)); /// Mostra o Nome do Usuário autenticado
		drawLastMessage();
		Console.writeLine("0 - Nova Mensagem");
		Console.writeLine("1 - Contatos");
		Console.writeLine("2 - Stories");
		Console.writeLine("3 - Editar Perfil");
		Console.writeLine("4 - Logout");
		Console.writeLine("###################################");
		Console.write("Opção: ");
	}
	/// Função utilizada para Tratar a Edição deste Usuário
	private void handleEditProfile() {
		Console.clear();
		Console.writeLine(String.format("Nome atual (%s)", mLogged.name));
		String name = labelText("Digite um novo Nome");
		/// Atualiza o Nome do Usuário
		UserManager.getSingleton().updateUserName(name, mLogged.id);
		mLogged.name = name;
		Console.writeLine("Nome modificado com Sucesso!!!");
	}
	public void run() {
		boolean stop = false;
		/// Ponto de Entrada da View Profile
		while(!stop) {
			Console.clear();
			drawProfile(); /// Desenha a View
			int option = Console.readInt();
			switch(option) {
				case SystemCodes.PROFILE_NEW_MSG:
				case SystemCodes.PROFILE_CONTACT:
				{
					/// Redireciona para a View de Contatos
					ContactEngine engine = new ContactEngine(mLogged,mContactList);
					engine.run(); /// Roda a View de Contatos
				}
					break;
				case SystemCodes.PROFILE_STORY:
				{
					/// Redireciona para a View StoryEngine
					StoryEngine engine = new StoryEngine(mLogged);
					engine.run(); /// Roda a View StoryEngine
				}
					break;
				case SystemCodes.PROFILE_EDIT:
					handleEditProfile(); /// Redireciona para a Edição do Perfil
					break;
				case SystemCodes.PROFILE_LOGOUT:
				{
					/// Faz o Logout do Usuário
					stop = true;
					Console.clear();
					Console.writeLine("Fazendo Logout...");
					Console.pause();
				}
					break;
				default:
					/// Chama a Função auxiliar de opção Invalida
					invalidOption();
				break;
			}
		}
	}
}
