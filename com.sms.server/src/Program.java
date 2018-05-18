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
		if(!FactoryManager.initBusiness())
			return;
		/*JServer server = new JServer();
		try {
			server.setPort(3425);
			server.connect();
			server.setOnConnected((sender,arg)->{;
				Socket socket = arg.socket.handle;
				String msg = String.format("%s is Connected", socket.getLocalAddress().toString());
				System.out.println(msg);
			});
			server.setOnResponse((sender,arg)->{
				String msg = String.format("Incoming Message: %s", arg.message);
				System.out.println(msg);
			});
			server.setOnDisconnect((sender,arg)->{
				String msg = String.format("%s has Disconnected", arg.socket.handle.getLocalAddress().toString());
				System.out.println(msg);
			});
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		return;
	}
}
