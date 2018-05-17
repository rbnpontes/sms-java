package views;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import business.FactoryManager;
import business.GlobalManager;
import business.IOManager;
import business.UserManager;
import entities.User;
import network.JServer;
import repository.DBModel;
import samples.Example;

public class Program {
	public static void main(String[] args) {
		/// Caso queira rodar a Classe exemplo, descomente o codigo abaixo
		/// Example.Run();
		if(!FactoryManager.initBusiness())
			return;
		JServer server = new JServer();
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
		}
		return;
	}
}
