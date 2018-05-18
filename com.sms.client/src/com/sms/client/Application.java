package com.sms.client;
import com.sms.common.Debug;
import com.sms.network.*;
public class Application {
	private JClient mClient;
	public boolean hasStopped() {
		return mClient.hasStopped();
	}
	public void stop() {
		mClient.stop();
	}
	public void initialize() {
		mClient = new JClient();
		/// Setting Callbacks
		mClient.setOnConnected((x,y)->OnConnected());
		mClient.setOnDisconnect((x,y)->OnDisconnected());
		try {
			mClient.setPort(3425);
			mClient.connect();
		} catch (Exception e) {
			Debug.error("N�o foi possivel se Conectar ao Servidor, Verifique se voc� est� conectado a Rede!!!");
			e.printStackTrace();
			return;
		}
	}
	protected void OnConnected() {
		Debug.success("Conectado ao Servidor com Sucesso");
	}
	protected void OnDisconnected() {
		Debug.error("Voc� foi Desconectado do Servidor");
	}
}
