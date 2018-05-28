package com.sms.client;

import com.sms.common.Console;

public class View {
	public void label(String str) {
		Console.writeLine(str + ": ");
	}
	public int labelInt(String str) {
		label(str);
		return Console.readInt();
	}
	public String labelText(String str) {
		label(str);
		return Console.readLine();
	}
	public void invalidOption() {
		Console.writeLine("Opção Inválida");
		Console.readKey();
	}
}
