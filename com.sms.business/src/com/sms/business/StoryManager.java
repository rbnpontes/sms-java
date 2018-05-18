package com.sms.business;

public class StoryManager{
	private static StoryManager instance;
	public StoryManager(){
		instance =this;
	}
	public static StoryManager getSingleton() {
		return instance;
	}
}
