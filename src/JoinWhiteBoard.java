
public class JoinWhiteBoard {
	
	public static void main(String[] args) {
		WhiteBoardGUI window=new WhiteBoardGUI();
		window.initialize();
		Client client=new Client(args,window);
		window.setClient(client);
		
		window.setIsServer(false);
		
		window.setClientInformation("Sorry! You are client.\nYou have no authority to manage client!");
		
	}
}
