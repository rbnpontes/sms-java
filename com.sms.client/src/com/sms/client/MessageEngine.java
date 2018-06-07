package com.sms.client;

import com.sms.business.MessageManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Message;
import com.sms.entities.User;
/// View responsavel para Tratar as Conversar entre um Contato e Outro
public class MessageEngine extends View{
	private User mLogged; /// Usuário Logado
	private User mTarget; /// Usuário na qual iniciou ou retornou a conversa
	private Message[] mMessages; /// Mensagens Arquivadas
	public MessageEngine(User logged, User target) {
		mLogged = logged;
		mTarget = target;
		refreshMessages();
	}
	/// Função utilizada para Recarregar as Mensagens
	private void refreshMessages() {
		Debug.log("Carregando Mensagens!!!");
		/// Carrega as Mensagens e deixa elas em cache
		mMessages = MessageManager.getSingleton().getConversation(mLogged, mTarget);
	}
	/// Função Usada para Desenhar a View de Mensagens
	private void drawMessages() {
		Console.clear();
		Console.writeLine("###################################");
		if(mMessages.length > 0)
		{
			for(Message item : mMessages) {
				String sender = item.id_src == mLogged.id ? "Eu":item.getSource().name;
				Console.writeLine(String.format("%s: %s", sender,item.data));
			}
		}else {
			Console.writeLine("Você não possuí nenhuma mensagem");
		}
		Console.writeLine("Comandos: ");
		Console.writeLine("/e - Sair | /r - Recarregar Mensagens");
		Console.writeLine("----------------------------------------");
	}
	/// Faz a interpretação do comando, caso o usuário deseje sair da View ou recarregar as mensagens
	/// retorna um codigo de saída para gerenciamento terceiro
	private int interpretCommand(String cmd) {
		if(cmd.equals("/e"))
			return SystemCodes.MSG_BACK;
		else if(cmd.equals("/r"))
			return SystemCodes.MSG_REFRESH;
		else
			return SystemCodes.MSG_CHAT;
	}
	/// Faz o envio da mensagem para o usuário
	private void pushMessage(String msg) {
		Message message = new Message();
		message.id_src = mLogged.id;
		message.id_dst = mTarget.id;
		message.data = msg;
		MessageManager.getSingleton().addMessage(message);
	}
	/// Ponto de Entrada da View
	public void run() {
		boolean stop = false;
		while(!stop) {
			drawMessages(); // Desenha a View
			String raw = labelText("Digite Algo");
			int code = interpretCommand(raw);
			if(raw.length() == 0)
				continue;
			switch(code) {
				case SystemCodes.MSG_CHAT:
				{
					pushMessage(raw);
					refreshMessages();
				}
					break;
				case SystemCodes.MSG_REFRESH:
				{
					Console.clear();
					refreshMessages();
				}
					break;
				case SystemCodes.MSG_BACK:
					stop = true;
					break;
			}
		}
	}
}
