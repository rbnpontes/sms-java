package com.sms.client;

import com.sms.business.StoryManager;
import com.sms.common.Console;
import com.sms.common.Debug;
import com.sms.entities.Story;
import com.sms.entities.User;

public class StoryEngine extends View {
	private User mLogged;
	private Story[] mStories;
	public StoryEngine(User logged) {
		mLogged= logged;
		refreshStories();
	}
	private void refreshStories() {
		Debug.log("Carregando Stories...");
		mStories = StoryManager.getSingleton().getRecentStories();
	}
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
	private void makeStory() {
		Story story = new Story();
		story.id_owner = mLogged.id;
		Console.clear();
		Console.writeLine("####################");
		story.message = labelText("Oque você esta pensando ?\n");
		StoryManager.getSingleton().addStory(story);
		Console.clear();
		Console.writeLine("Publicado com Sucesso!!!");
		Console.readKey();
		refreshStories();
	}
	public void run() {
		boolean stop = false;
		while(!stop) {
			drawStory();
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
				invalidOption();
				break;
			}
		}
	}
}
