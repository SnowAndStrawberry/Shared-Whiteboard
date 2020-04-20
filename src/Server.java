import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

import org.json.JSONObject;


public class Server {
	
	//initial socket and bind socket 8080, or get paramaters from command line.
	private int port=8127;
	private String IPAddress="127.0.0.1";
	private String username="no name";
	private WhiteBoardGUI window;
	private ArrayList<ServerThread> threadList=new ArrayList<ServerThread>();
	private Socket socket;
	private String path="";
	private String clientInformation="";
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	private Log log;
	private SuccessfulCheck successfulCheck;
	/**
	 * Constructor to get server setting 
	 */
	public Server(String []args,WhiteBoardGUI window) {
		
		getSetting(args);
		this.window=window;
		this.log=new Log();
		
	}
	
	private void getSetting(String []args) {
		//Obtain information from console
				// if(args.length>0) {
				// 	IPAddress=args[0];
				// 	port=Integer.valueOf(args[1]);
				// 	username=args[2];
				// }
				if (args.length == 3) {
			if (args[0] != null && args[1] != null && args[2] != null) {
				successfulCheck = ServerInputError(args);
				if (successfulCheck.isSuccessful == true) {
					IPAddress=args[0];
					port=Integer.valueOf(args[1]);
					username=args[2];
				} else {
					if (successfulCheck.errorDescription.equals("portnumber")) {
						JOptionPane.showMessageDialog(null, "Port number input error!", "Error",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} else if (successfulCheck.errorDescription.equals("IPaddress")) {
						JOptionPane.showMessageDialog(null, "IP address input error!", "Error",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} else if (successfulCheck.errorDescription.equals("portnumber&IPaddress")) {
						JOptionPane.showMessageDialog(null, "Port number and IP address input error!", "Error",JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Arguments input error!", "Error",JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "The number of arguments are error!", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private SuccessfulCheck ServerInputError(String[] args) {
		String IPAddress = args[0];
		String portNumberString = args[1];
		SuccessfulCheck successfulCheckServer = new SuccessfulCheck();
		boolean isIP = IPCheck(IPAddress);
		boolean isPortNumber = portNumberCheck(portNumberString);
		if (isIP == true && isPortNumber == true) {
			SuccessfulCheck.isSuccessful = true;
		} else if (isIP == true && isPortNumber == false) {
			SuccessfulCheck.isSuccessful = false;
			SuccessfulCheck.errorDescription = "portnumber";
		} else if (isIP == false && isPortNumber == true) {
			SuccessfulCheck.isSuccessful = false;
			SuccessfulCheck.errorDescription = "IPaddress";
		} else if (isIP == false && isPortNumber == false) {
			SuccessfulCheck.isSuccessful = false;
			SuccessfulCheck.errorDescription = "portnumber&IPaddress";
		}
		return successfulCheckServer;
	}
	
	private static boolean portNumberCheck(String portNumberString) {
		int portNumber = Integer.parseInt(portNumberString);
		if (portNumber < 1025 || portNumber > 65534) {
			return false;
		} else {
			return true;
		}
	}
	
	public static boolean IPCheck(String IPaddress) {
		if (IPaddress.length() < 7 || IPaddress.length() > 15 || "".equals(IPaddress)) {
			return false;
		}
		String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		Pattern pattern = Pattern.compile(rexp);
		Matcher matcher = pattern.matcher(IPaddress);
		boolean isIPAddress = matcher.find();
		return isIPAddress;
	}

	
	public void createConnection() {
		try {
			ServerSocket server=new ServerSocket(port);
			//Clinet number, create one thread for each client.
			int clientNumber=0;
			//Waiting for connection from client
			System.out.println("Server is waiting for connect");
			socket=new Socket();
			System.out.println("-1");
			//Add exit hook, when server exits, the hood thread runs.
			Runtime.getRuntime().addShutdownHook(new ShutDownWork(socket,server,path,window,threadList));
			
			while(true) {
				
				
				socket = server.accept();
				// Create a new thread
				
				ServerThread serverThread = new ServerThread(socket, clientNumber, username,log, window,this);
				// Start a new thread
				threadList.add(serverThread);
				
				new Thread(serverThread).start();
				
				System.out.println("successfull connected");
				
				
				
				updateConnectedClients();
				clientNumber++;
				
				
			}
		}
		catch(IOException e) {
			System.out.println("Listen connection failed: fail to create thread!");
			
		}		
	}
	
	/**
	 * Update the client information of the manage window.
	 */
	private void updateConnectedClients() {
		clientInformation="";
		//window.setClientInformation(clientInformation);
		for(ServerThread thread:threadList) {
			clientInformation+=thread.getClientInformation()+"\n";
			
		}
		window.setClientInformation(clientInformation);
		window.setClientListTextArea(clientInformation);
		
		
	}
	
	/**
	 * close the client window.
	 */
	private void deleteTread(int disconnectedClient) {
		for(ServerThread thread:threadList) {
			if(thread.getClientNumber()==disconnectedClient) {
				thread.setExitStauts(true);
				threadList.remove(thread);
				break;
			}
		}
		JOptionPane.showMessageDialog(null, "Client "+
				disconnectedClient+ " has been disconnected.", "Remove Client",JOptionPane.ERROR_MESSAGE); 
		updateConnectedClients();
	}
	
	public void sendText(String text) {
		String command="chat";
		String sendText="";
		SimpleDateFormat simpleDateFormat=null;
		String time="";
		String usernameString="";
		int receivingClientNumber;
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			//date
			time = simpleDateFormat.format(new Date(0));
			usernameString=username;
			sendText = usernameString + "_server " + time + ": \n" + text;
			window.setChatInputTextField();
			window.setChatInfoTextArea(sendText);
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", command);
			sendTextJson.put("username", usernameString);
			sendTextJson.put("time", time);
			sendTextJson.put("text", text);
			sendTextJson.put("clientNumber", "_server");
			System.out.println(sendTextJson.toString());
			for (ServerThread thread : threadList) {
				thread.sendText(sendTextJson);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void sendEditingClientToAll() {
		String editingClients="";
		for (ServerThread thread : threadList) {
			editingClients+=thread.getClientName()+"\n";
        }
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command","editingClients");
			sendTextJson.put("editingClients", editingClients);
			
			
			
			for (ServerThread thread : threadList) {
				thread.sendText(sendTextJson);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void sendDrawToAll(ShapeList shaplist,int sentClientNumber) {
    	PrintWriter printWriter;
    	String shapeName,shapeText;
    	int shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY,shapeWidth;
    	Color shapeColor;
    	for (ServerThread thread : threadList) {
			if(thread.getClientNumber()!=sentClientNumber)
				thread.sendDraw(shaplist);
        }
		
	}
	public void sendDrawToAll(ShapeList shaplist) {
    	PrintWriter printWriter;
    	String shapeName,shapeText;
    	int shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY,shapeWidth;
    	Color shapeColor;
    	for (ServerThread thread : threadList) {
			thread.sendDraw(shaplist);
        }
		
	}
	
	public void sendMessage(JSONObject receiveTextJson) {
		for (ServerThread thread : threadList) {
			thread.sendText(receiveTextJson);
        }
	}
	
	public void disconnectWithClient(int clientNumber) {
		for(ServerThread thread:threadList) {
			if(clientNumber==thread.getClientNumber()) {
				thread.sendDisconnect();
				this.sendEditingClientToAll();
			}
		}
	}
	public void removeThread(int clientNumber) {
		ServerThread removedThread=null;
		for(ServerThread thread:threadList) {
			if(clientNumber==thread.getClientNumber()) {
				removedThread=thread;
				
			}
		}
		threadList.remove(removedThread);
		
		sendEditingClientToAll();
		updateConnectedClients();
		
	}
	
	public void sendNewPage() {
		for (ServerThread thread : threadList) {
			thread.sendNewPage();
        }
	}
	
	public void sendNewFile() {
		sendNewPage();
		for (ServerThread thread : threadList) {
			thread.sendCurrentDraw(window.getDrawMethod());
        }
		
	}
	public void sendNewFile(int clientNumber) {
		for (ServerThread thread : threadList) {
			if(thread.getClientNumber()!=clientNumber)
				thread.sendCurrentDraw(window.getDrawMethod());
        }
	}
	public void sendNewPage(int clientNumber) {
		for (ServerThread thread : threadList) {
			if(thread.getClientNumber()!=clientNumber)
				thread.sendNewPage();
        }
	}
	public void sendCloseFile() {
		for (ServerThread thread : threadList) {
			thread.sendClose();
        }
	}
	public void sendExit() {
		for (ServerThread thread : threadList) {
			thread.sendExit();
        }
	}
}
