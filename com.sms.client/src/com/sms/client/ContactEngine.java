package com.sms.client;

import java.sql.SQLException;

import com.sms.business.ContactManager;
import com.sms.business.UserManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Contact;
import com.sms.entities.User;

/// View responsavel para Gerenciamento de Contatos
public class ContactEngine extends View{
	private User mLogged; /// Usuário Logado
	private Contact[] mContacts; /// Lista de Contatos em Cache
	public ContactEngine(User logged, Contact[] contacts) {
		mLogged = logged;
		mContacts = contacts;
	}
	/// Atualiza os Contatos da View
	private void refreshContacts() {
		Debug.log("Carregando Contatos");
		/// Faz a Busca pelos contatos do Usuário Logado e armazena em Cache
		mContacts = ContactManager.getSingleton().getUserContacts(mLogged);
	}
	/// Desenha a View de Contatos
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
	/// Função responsavel para tratar uma nova conversa com um Contato
	private void handleNewMessage() {
		String username = labelText("Digite o Nome do Usuário");
		User target = UserManager.getSingleton().getUserbyUsername(username);
		if(target == null) {
			Console.clear();
			Console.writeLine("Usuário não encontrado!");
			Console.pause();
			return;
		}
		/// Instancia a View de Mensagens
		MessageEngine engine = new MessageEngine(mLogged,target);
		engine.run(); /// Roda a View de Mensagens
	}
	/// Faz o tratamento para adicionar um novo Contato
	private void handleNewContact() {
		Console.clear();
		Console.writeLine("###### Adicionar Novo Contato #######");
		String username = labelText("Digite o Nome do Usuário");
		/// Faz procura do usuario preenchido no sistema
		User tempUser = UserManager.getSingleton().getUserbyUsername(username);
		/// Faz o tratamento do resultado
		if(tempUser == null)
		{
			Console.clear();
			Console.writeLine("Usuário não Encontrado");
			Console.pause();
			return;
		}
		/// Prepara o Contato para que possa adicionar ao banco
		Contact cnt = new Contact();
		cnt.id_owner = mLogged.id;
		cnt.id_target = tempUser.id;
		/// Faz uma verificação se este usuário já adicionou este contato
		for(Contact item : mContacts) {
			if(item.id_target == cnt.id_target) {
				Console.clear();
				Console.writeLine("Você já adicionou este Usuário");
				Console.pause();
				return;
			}
		}
		try {
			/// Adiciona o Contato ao Banco de Dados
			ContactManager.getSingleton().addNewContact(cnt);
			Console.writeLine("Contato adicionado com Sucesso!!!");
			refreshContacts();
			Console.pause();
		} catch (SQLException e) {
			Debug.error("Aconteceu um Error ao Adicionar um Usuário, tente novamente mais tarde!");
		}
	}
	/// Faz o preparamento de remoção do usuário
	private void handleRemoveContact() {
		Console.clear();
		Console.writeLine("####### Remover Usuário #######");
		String username = labelText("Digite o Nome do Usuário");
		/// Busca o usuário no banco a qual quer remover
		User tempUser = UserManager.getSingleton().getUserbyUsername(username);
		if(tempUser == null)
		{
			Console.clear();
			Console.writeLine("Usuário não Encontrado");
			Console.pause();
			return;
		}
		Contact cnt = null;
		/// Busca no cache o Contato Adicionado
		for(Contact item : mContacts) {
			if(item.id_target == tempUser.id)
			{
				cnt = item;
				break;
			}
		}
		/// Caso já tenha removido o usuário, ou não tenha adicionado, apresente um erro na tela
		if(cnt == null) {
			Console.clear();
			Console.writeLine("Você não possuí este contato em sua lista!");
			Console.pause();
			return;
		}
		try {
			/// Remove o Contato do Banco
			ContactManager.getSingleton().deleteContact(cnt);
			Console.writeLine("Contato removido com Sucesso!!!");
			refreshContacts();
			Console.pause();
		} catch (SQLException e) {
			Debug.error("Aconteceu um Error interno ao Remover este Contato, tente novamente mais tarde!");
		}
	}
	/// Ponto de Entrada de Contatos
	public void run() {
		boolean stop = false;
		while(!stop) {
			/// Desenha Contatos
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
					invalidOption(); // Chama a função Auxiliar da View de Opção Inválida
				break;
			}
		}
	}
}
