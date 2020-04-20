
public class CreateWhiteBoard {
	
	public static void main(String[] args) {
		WhiteBoardGUI window=new WhiteBoardGUI();
		window.initialize();
		Server server=new Server(args,window);
		Boolean isServer=true;
		window.setIsServer(isServer);
		window.setServer(server);
	}
}
