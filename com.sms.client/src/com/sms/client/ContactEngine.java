package com.sms.client;

import java.sql.SQLException;

import com.sms.business.ContactManager;
import com.sms.business.UserManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Contact;
import com.sms.entities.User;

public class ContactEngine extends View{
	private User mLogged;
	private Contact[] mContacts;
	public ContactEngine(User logged, Contact[] contacts) {
		mLogged = logged;
		mContacts = contacts;
	}
	private String getLabel(String label) {
		Console.write(label + ": ");
		return Console.readLine();
	}
	private void refreshContacts() {
		Debug.log("Carregando Contatos");
		mContacts = ContactManager.getSingleton().getUserContacts(mLogged);
	}
	private void drawContacts() {
		Console.clear();
		Console.writeLine("#################################");
		for(Contact item : mContacts) {
			Console.writeLine(String.format("%s :: %s",item.target.name,item.target.username));
		}
		Console.writeLine("#################################");
		Console.writeLine("1 - Adicionar | 2 - Remover | 3 - Nova Mensagem | 4 - Voltar");
		Console.write("Opção: ");
	}
	private void handleNewMessage() {
		String username = labelText("Digite o Nome do Usuário");
		User target = UserManager.getSingleton().getUserbyUsername(username);
		if(target == null) {
			Console.clear();
			Console.writeLine("Usuário não encontrado!");
			Console.pause();
			return;
		}
		MessageEngine engine = new MessageEngine(mLogged,target);
		engine.run();
	}
	private void handleNewContact() {
		Console.clear();
		Console.writeLine("###### Adicionar Novo Contato #######");
		String username = labelText("Digite o Nome do Usuário");
		User tempUser = UserManager.getSingleton().getUserbyUsername(username);
		if(tempUser == null)
		{
			Console.clear();
			Console.writeLine("Usuário não Encontrado");
			Console.pause();
			return;
		}
		Contact cnt = new Contact();
		cnt.id_owner = mLogged.id;
		cnt.id_target = tempUser.id;
		
		for(Contact item : mContacts) {
			if(item.id_target == cnt.id_target) {
				Console.clear();
				Console.writeLine("Você já adicionou este Usuário");
				Console.pause();
				return;
			}
		}
		try {
			ContactManager.getSingleton().addNewContact(cnt);
			Console.writeLine("Contato adicionado com Sucesso!!!");
			refreshContacts();
			Console.pause();
		} catch (SQLException e) {
			Debug.error("Aconteceu um Error ao Adicionar um Usuário, tente novamente mais tarde!");
		}
	}
	private void handleRemoveContact() {
		Console.clear();
		Console.writeLine("####### Remover Usuário #######");
		String username = labelText("Digite o Nome do Usuário");
		User tempUser = UserManager.getSingleton().getUserbyUsername(username);
		if(tempUser == null)
		{
			Console.clear();
			Console.writeLine("Usuário não Encontrado");
			Console.pause();
			return;
		}
		Contact cnt = null;
		for(Contact item : mContacts) {
			if(item.id_target == tempUser.id)
			{
				cnt = item;
				break;
			}
		}
		if(cnt == null) {
			Console.clear();
			Console.writeLine("Você não possuí este contato em sua lista!");
			Console.pause();
			return;
		}
		try {
			ContactManager.getSingleton().deleteContact(cnt);
			Console.writeLine("Contato removido com Sucesso!!!");
			refreshContacts();
			Console.pause();
		} catch (SQLException e) {
			Debug.error("Aconteceu um Error interno ao Remover este Contato, tente novamente mais tarde!");
		}
	}
	public void run() {
		boolean stop = false;
		while(!stop) {
			drawContacts();
			int code = Console.readInt();
			switch(code) {
			case SystemCodes.CONTACT_ADD:
				handleNewContact();
				break;
			case SystemCodes.CONTACT_REMOVE:
				handleRemoveContact();
				break;
			case SystemCodes.CONTACT_NEW_MSG:
				handleNewMessage();
				break;
			case SystemCodes.CONTACT_BACK:
					stop = true;
				break;
				default:
					invalidOption();
				break;
			}
		}
	}
}
