package com.sms.client;

import com.sms.common.Console;
/// Class Base para as View do Sistema
/// Todo o funcionamento do sistema das views � em forma de Pipeline
/// Onde uma View Instancia outra View e nisto a view anterior ficar� 
/// Em espera at� que a View instanciada pela pai seja finalizada.
/// *A unica maneira de n�o funcionar em pipeline � se, uma View Instanciar uma "Thread"
/// que chama outra View*
public class View {
	/// --------------------------------
	/// Fun��es auxiliares para as Views
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
		Console.writeLine("Op��o Inv�lida");
		Console.readKey();
	}
	/// ---------------------------------
}
