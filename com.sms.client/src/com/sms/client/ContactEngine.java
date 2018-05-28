package com.sms.client;

import com.sms.business.UserManager;
import com.sms.common.Console;
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
	public void run() {
		boolean stop = false;
		while(!stop) {
			drawContacts();
			int code = Console.readInt();
			switch(code) {
			case SystemCodes.CONTACT_ADD:
				break;
			case SystemCodes.CONTACT_REMOVE:
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
