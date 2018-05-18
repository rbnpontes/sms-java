import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import com.sms.business.FactoryManager;
import com.sms.business.GlobalManager;
import com.sms.business.IOManager;
import com.sms.business.UserManager;
import com.sms.entities.Response;
import com.sms.entities.User;
import com.sms.network.JServer;
import com.sms.repository.IDBModel;
import samples.Example;

public class Program {
	public static void main(String[] args) {
		/// Caso queira rodar a Classe exemplo, descomente o codigo abaixo
		/// Example.Run();
		Application application = new Application();
		application.initialize();
		return;
	}
}
