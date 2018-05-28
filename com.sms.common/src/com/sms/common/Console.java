package com.sms.common;

import java.io.IOException;
import java.util.Scanner;

public class Console {
	public static void write(String str) {
		System.out.print(str);
	}
	public static void writeLine(String str) {
		System.out.println(str);
	}
	public static String readLine() {
		Scanner scann = new Scanner(System.in);
		return scann.nextLine();
	}
	public static int readInt() {
		Scanner scann = new Scanner(System.in);
		return scann.nextInt();
	}
	public static float readFloat() {
		Scanner scann = new Scanner(System.in);
		return scann.nextFloat();
	}
	public static int readKey() {
		try {
			return System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public static void exec(String command) {
		try {
			new ProcessBuilder("cmd", "/c", command).inheritIO().start().waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void clear() {
		exec("cls");
	}
	public static void pause() {
		exec("pause");
	}
}
