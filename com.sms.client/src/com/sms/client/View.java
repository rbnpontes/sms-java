package com.sms.client;

import com.sms.common.Console;
/// Class Base para as View do Sistema
/// Todo o funcionamento do sistema das views é em forma de Pipeline
/// Onde uma View Instancia outra View e nisto a view anterior ficará 
/// Em espera até que a View instanciada pela pai seja finalizada.
/// *A unica maneira de não funcionar em pipeline é se, uma View Instanciar uma "Thread"
/// que chama outra View*
public class View {
	/// --------------------------------
	/// Funções auxiliares para as Views
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
	/// ---------------------------------
}
