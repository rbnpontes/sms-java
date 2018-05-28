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
	private User mLogged;
	private Contact[] mContactList;
	public ProfileEngine(User user) {
		mLogged= UserManager.getSingleton().getUserbyUsername(user.username); /// Fetch user in database
		Debug.log("Carregando Lista de Contatos");
		mContactList = ContactManager.getSingleton().getUserContacts(mLogged);
	}
	private void drawLastMessage() {
		Message msg = MessageManager.getSingleton().getLastMessage(mLogged);
		if(msg == null)
			return;
		Console.writeLine(String.format("Ultima Mensagem - (%s): %s", msg.getSource().name,msg.data));
	}
	private void drawProfile() {
		Console.writeLine("###################################");
		Console.writeLine(String.format("- %s -", mLogged.name));
		drawLastMessage();
		Console.writeLine("0 - Nova Mensagem");
		Console.writeLine("1 - Contatos");
		Console.writeLine("2 - Stories");
		Console.writeLine("3 - Editar Perfil");
		Console.writeLine("4 - Logout");
		Console.writeLine("###################################");
		Console.write("Opção: ");
	}
	private void handleEditProfile() {
		Console.clear();
		Console.writeLine(String.format("Nome atual (%s)", mLogged.name));
		String name = labelText("Digite um novo Nome");
		UserManager.getSingleton().updateUserName(name, mLogged.id);
		mLogged.name = name;
		Console.writeLine("Nome modificado com Sucesso!!!");
	}
	public void run() {
		boolean stop = false;
		while(!stop) {
			Console.clear();
			drawProfile();
			int option = Console.readInt();
			switch(option) {
				case SystemCodes.PROFILE_NEW_MSG:
				case SystemCodes.PROFILE_CONTACT:
				{
					ContactEngine engine = new ContactEngine(mLogged,mContactList);
					engine.run();
				}
					break;
				case SystemCodes.PROFILE_STORY:
				{
					StoryEngine engine = new StoryEngine(mLogged);
					engine.run();
				}
					break;
				case SystemCodes.PROFILE_EDIT:
					handleEditProfile();
					break;
				case SystemCodes.PROFILE_LOGOUT:
				{
					stop = true;
					Console.clear();
					Console.writeLine("Fazendo Logout...");
					Console.pause();
				}
					break;
				default:
					invalidOption();
				break;
			}
		}
	}
}
