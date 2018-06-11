package com.sms.client;

import java.io.File;
import java.io.IOException;

public class Program {
	/// Ponto de Entrada do Programa
	public static void main(String[] args){
		/// Classe Responsavel por Iniciar o Programa
		Application app = new Application();
		app.initialize();
		app.run(); /// Run Program 
	}

}
