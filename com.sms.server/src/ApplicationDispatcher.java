import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import com.sms.business.UserManager;
import com.sms.entities.Response;
import com.sms.entities.ResponseClient;
import com.sms.entities.User;
import com.sms.network.JObject;
import com.sms.network.JServer;

import models.ClientCodes;
import models.ClientHolder;

public final class ApplicationDispatcher {
	private static Object loginUser(String response,ClientHolder client) {
		Gson gson = new Gson();
		Response<User> result = gson.fromJson(response, new TypeToken<Response<User>>() {}.getType());
		User user = result.data;
		return UserManager.getSingleton().tryLogin(user);
	}
	private static Object registerUser(String response, ClientHolder client) {
		Gson gson = new Gson();
		Response<User> result = gson.fromJson(response, new TypeToken<Response<User>>() {}.getType());
		User user = result.data;
		return UserManager.getSingleton().tryRegister(user);
	}
	/// Este metodo ficara encarregado de despachar os codigos enviado
	/// pelos clients, para cada codigo irá redirecionar para uma regra de negocio.
	/// a regra de negocio deve retornar um objeto do tipo Response para que seja
	/// enviado ao client como Handshake.
	public static void HandleResponse(String jsonResponse, JObject client, JServer server) {
		ClientHolder holder = ClientManager.getSingleton().getClient(client);
		Gson gson = new Gson();
		ResponseClient response = null;
		try {
		response = gson.fromJson(jsonResponse, ResponseClient.class);
		}catch(JsonSyntaxException e) {
			e.printStackTrace();
			return;
		}
		if(response == null)
			return;
		Object result=null;
		switch(response.code) {
			case ClientCodes.LOGIN:
					result = loginUser(jsonResponse,holder);
				break;
			case ClientCodes.REGISTER:
				break;
			case ClientCodes.LIST_CONTACTS:
				break;
			case ClientCodes.LIST_MESSAGES:
				break;
			case ClientCodes.ADD_CONTACT:
				break;
			case ClientCodes.REMOVE_CONTACT:
				break;
			case ClientCodes.SEND_MSG:
				break;
			case ClientCodes.ADD_STATUS:
				break;
			case ClientCodes.EDIT_USER:
				break;
		}
		if(result == null)
			return;
		/// Transforma o resultado em JSON e envia para o cliente
		String raw = gson.toJson(result);
		server.send(raw,client); // Envia ao Cliente
	}
}
