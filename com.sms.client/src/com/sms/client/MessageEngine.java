package com.sms.client;

import com.sms.business.MessageManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Message;
import com.sms.entities.User;

public class MessageEngine extends View{
	private User mLogged;
	private User mTarget;
	private Message[] mMessages;
	public MessageEngine(User logged, User target) {
		mLogged = logged;
		mTarget = target;
		refreshMessages();
	}
	private void refreshMessages() {
		Debug.log("Carregando Mensagens!!!");
		mMessages = MessageManager.getSingleton().getConversation(mLogged, mTarget);
	}
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
	private int interpretCommand(String cmd) {
		if(cmd.equals("/e"))
			return SystemCodes.MSG_BACK;
		else if(cmd.equals("/r"))
			return SystemCodes.MSG_REFRESH;
		else
			return SystemCodes.MSG_CHAT;
	}
	private void pushMessage(String msg) {
		Message message = new Message();
		message.id_src = mLogged.id;
		message.id_dst = mTarget.id;
		message.data = msg;
		MessageManager.getSingleton().addMessage(message);
	}
	public void run() {
		boolean stop = false;
		while(!stop) {
			drawMessages();
			String raw = labelText("Digite Algo");
			int code = interpretCommand(raw);
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
					Console.pause();
				}
					break;
				case SystemCodes.MSG_BACK:
					stop = true;
					break;
			}
		}
	}
}
