package samples;
import java.sql.SQLException;

/// Importar todos os Codigos de business
import business.*;
import entities.User;
import repository.Database;
public class Example {
	/// Método Estatico para rodar o programa Exemplo
	public static void Run() {
		/// Antes de Iniciar é necessario iniciar o FactoryManager
		/// O FactoryManager é utilizado para iniciar os codigos essenciais
		/// do projeto, é recomendado iniciar no inicio do programa
		FactoryManager.initBusiness();
		/// Após a Inicialização do FactoryManager, caso queira acessar
		/// um dos "singletons" de negocio,
		/// é só declarar o nome da classe + o metodo getSingleton()
		/// Exemplo: UserManager.getSingleton() ///
		UserManager.getSingleton();
		/// Consultando um Usuário no Banco de Dados
		/// *Aviso, esta função pode acabar gerando um erro
		/// ao tentar carregar as informações, pra isto precisamos trata-la 
		/// usando metodos try e catch
		/// Exemplo abaixo:
		try {
			/// Faz a tentativa de chamada na função
			UserManager.getSingleton().getUserById(1);
		}catch(SQLException e) {
			/// caso ocorra um error, este codigo será executado
			/// e uma exceção do tipo "SQLException" será passada como parametro
			e.printStackTrace(); /// Imprime o Error no Console
		}
		/// Para executar uma Query é bem simples, basta usar o singleton "GlobalManager"
		/// nele terá que pegar o objeto do banco de dados para efetuar os codigos Query
		/// Segue o Exemplo abaixo:
		Database db = GlobalManager.getDatabase(); /// Pegando objeto responsavel por tratar o banco de dados
		/// Caso queira fazer algum tipo de Select, é recomendavel usar o método "executeQuery", após isso
		/// você tem que passar o objeto que será convertido e preenchido.
		/// Neste exemplo, eu quero que todos os resultados do select sejam um VETOR de um objeto User
		try {
			// Novamente, como este método é critico, ele pode acabar retornando um error,
			// e precisariamos trata-lo.
			db.executeQuery("SELECT * FROM users", User.class);			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		/// não é necessario criar um variavel para armazenar o objeto Database
		/// só usei como exemplo.
		/// A função tambem pode ser utilizada desta maneira
		try {
			GlobalManager.getDatabase().executeQuery("SELECT * FROM users", User.class);			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		/// A classe GlobalManager tem um metodo rápido usado para executar uma query
		/// e que trabalha da mesma maneira que o anterior
		/// Exemplo:
		try {
			GlobalManager.executeQuery("SELECT * FROM users", User.class);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
}
