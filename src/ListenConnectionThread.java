
public class ListenConnectionThread implements Runnable {
	private Server server;
	public ListenConnectionThread(Server server){
		this.server=server;
	}
	public void run() {
		server.createConnection();
	}
}
