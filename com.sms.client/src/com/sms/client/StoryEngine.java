package com.sms.client;

import com.sms.business.StoryManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Story;
import com.sms.entities.User;
/// View responsavel por gerenciar o sistema de Story
public class StoryEngine extends View {
	private User mLogged; /// Usuário Logado
	private Story[] mStories; /// Cache dos Stories
	public StoryEngine(User logged) {
		mLogged= logged;
		refreshStories();
	}
	private void refreshStories() {
		Debug.log("Carregando Stories...");
		/// Faz a busca das Storys recentes no dia
		mStories = StoryManager.getSingleton().getRecentStories();
	}
	/// Desenha a View de Story
	private void drawStory() {
		Console.clear();
		Console.writeLine("#######################");
		for(Story item : mStories) {
			Console.writeLine(String.format("%s: %s", item.getOwner().name,item.message));
		}
		Console.writeLine("#######################");
		Console.writeLine("1 - Postar Algo | 2 - Recarregar | 3 - Voltar");
		Console.writeLine("Opção: ");
	}
	/// Faz a preparação para a criação de uma nova Story
	private void makeStory() {
		Story story = new Story();
		story.id_owner = mLogged.id;
		Console.clear();
		Console.writeLine("####################");
		story.message = labelText("Oque você esta pensando ?\n");
		StoryManager.getSingleton().addStory(story); /// Adiciona a Story ao Banco
		Console.clear();
		Console.writeLine("Publicado com Sucesso!!!");
		Console.readKey();
		refreshStories();
	}
	/// Ponto de Entrada da View
	public void run() {
		boolean stop = false;
		while(!stop) {
			drawStory(); // Desenha a View
			int code = Console.readInt();
			switch(code) {
			case SystemCodes.STORY_ADD:
				makeStory();
				break;
			case SystemCodes.STORY_REFRESH:
				refreshStories();
				break;
			case SystemCodes.STORY_BACK:
				stop=true;
				break;
			default:
				invalidOption(); /// Chama a função auxiliar de opção inválida da View
				break;
			}
		}
	}
}
