
public class RequestConnectionThread implements Runnable {
	private Client client;
	public RequestConnectionThread(Client client){
		this.client=client;
	}
	public void run() {
		client.createConnection();
	}
}
