
public class ClientShutDownWork extends Thread{
	private Client client;
	public ClientShutDownWork(Client client) {
		this.client=client;
	}
	
	public void run() {
		client.sendDisconnected();
	}
}
