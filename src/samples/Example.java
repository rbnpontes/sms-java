package samples;
import java.sql.SQLException;

/// Importar todos os Codigos de business
import business.*;
import entities.User;
import repository.Database;
public class Example {
	/// M�todo Estatico para rodar o programa Exemplo
	public static void Run() {
		/// Antes de Iniciar � necessario iniciar o FactoryManager
		/// O FactoryManager � utilizado para iniciar os codigos essenciais
		/// do projeto, � recomendado iniciar no inicio do programa
		FactoryManager.initBusiness();
		/// Ap�s a Inicializa��o do FactoryManager, caso queira acessar
		/// um dos "singletons" de negocio,
		/// � s� declarar o nome da classe + o metodo getSingleton()
		/// Exemplo: UserManager.getSingleton() ///
		UserManager.getSingleton();
		/// Consultando um Usu�rio no Banco de Dados
		/// *Aviso, esta fun��o pode acabar gerando um erro
		/// ao tentar carregar as informa��es, pra isto precisamos trata-la 
		/// usando metodos try e catch
		/// Exemplo abaixo:
		try {
			/// Faz a tentativa de chamada na fun��o
			UserManager.getSingleton().getUserById(1);
		}catch(SQLException e) {
			/// caso ocorra um error, este codigo ser� executado
			/// e uma exce��o do tipo "SQLException" ser� passada como parametro
			e.printStackTrace(); /// Imprime o Error no Console
		}
		/// Para executar uma Query � bem simples, basta usar o singleton "GlobalManager"
		/// nele ter� que pegar o objeto do banco de dados para efetuar os codigos Query
		/// Segue o Exemplo abaixo:
		Database db = GlobalManager.getDatabase(); /// Pegando objeto responsavel por tratar o banco de dados
		/// Caso queira fazer algum tipo de Select, � recomendavel usar o m�todo "executeQuery", ap�s isso
		/// voc� tem que passar o objeto que ser� convertido e preenchido.
		/// Neste exemplo, eu quero que todos os resultados do select sejam um VETOR de um objeto User
		try {
			// Novamente, como este m�todo � critico, ele pode acabar retornando um error,
			// e precisariamos trata-lo.
			db.executeQuery("SELECT * FROM users", User.class);			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		/// n�o � necessario criar um variavel para armazenar o objeto Database
		/// s� usei como exemplo.
		/// A fun��o tambem pode ser utilizada desta maneira
		try {
			GlobalManager.getDatabase().executeQuery("SELECT * FROM users", User.class);			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		/// A classe GlobalManager tem um metodo r�pido usado para executar uma query
		/// e que trabalha da mesma maneira que o anterior
		/// Exemplo:
		try {
			GlobalManager.executeQuery("SELECT * FROM users", User.class);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
