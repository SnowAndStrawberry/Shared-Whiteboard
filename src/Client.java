import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
	
	private int port=8127;
	private String IPAddress="127.0.0.1";
	private String username="no name";
	private WhiteBoardGUI window=new WhiteBoardGUI();
	private Socket socket;
	private String path="";
	private String clientInformation="";
	private String sendText;
	private SimpleDateFormat simpleDateFormat;
	private String time;
	private PrintWriter printWriter;
	private String text;
//	private JSONObject receiveTextJson;
	private String receiveText;
	private BufferedReader bufferedReader;
	private String usernameString;
	private int clientNumber;
	private JOptionPane waiting=new JOptionPane();
	private SuccessfulCheck successfulCheck;
	
	/**
	 * Constructor to get server setting and create connection
	 */
	public Client(String []args,WhiteBoardGUI window) {
		
		getSetting(args);
		this.window=window;
		
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
				successfulCheck = ClientInputError(args);
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
	
	private SuccessfulCheck ClientInputError(String[] args) {
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
		// Initiate window and set the manange window(Client cannot manage window)

		try {
			// Request Connection
			socket = new Socket(IPAddress, port);
			System.out.println("connected!");
			Runtime.getRuntime().addShutdownHook(new ClientShutDownWork(this));
			sentClientName();
			
			receiveText();
			
			
			// Connection failed and show the error message.
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Create socket failed: Port Number is not same as the server!" + "\nPlease exit and try again.", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Create socket failed: " + e.getMessage() + "\nPlease exit and try again.", "Close Information",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
				}
	}
	public void sentClientName() {
		
			String command="initialClient";
			String usernameString="";
			try {
				printWriter = new PrintWriter(socket.getOutputStream());
				
				usernameString=username;
				
				JSONObject sendTextJson = new JSONObject();
				sendTextJson.put("command", command);
				sendTextJson.put("username", usernameString);
				
				printWriter.write(sendTextJson.toString() + "\n");
				printWriter.flush();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			waiting.showMessageDialog(null, "Waiting for Server's permission...");
	}
	
	public void sendUndo() {
		String command="undo";
		
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", command);
			
			
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void sendRedo() {
		String command="redo";
		
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", command);
			
			
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void sendText(String text) {
		String command="chat";
		
		String sendText="";
		SimpleDateFormat simpleDateFormat=null;
		String time="";
		String usernameString="";
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
			time = simpleDateFormat.format(new Date());
			usernameString=username;
			sendText = usernameString + " " + time + ": " + text;
			window.setChatInputTextField();
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", command);
			sendTextJson.put("username", usernameString);
			sendTextJson.put("time", time);
			sendTextJson.put("text", text);
			sendTextJson.put("clientNumber", "_client "+String.valueOf(clientNumber));
			
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void sendUpdateDraw(ShapeList shaplist,int x,int y) {
		PrintWriter printWriter;
    	String shapeName,shapeText;
    	int shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY,shapeWidth;
    	String shapeColor;
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			shapeInitialX = shaplist.shapeInitialX;
			shapeInitialY= shaplist.shapeInitialY;
			shapeFinalX = shaplist.shapeFinalX;
			shapeFinalY = shaplist.shapeFinalY;
			shapeWidth = shaplist.shapeWidth;
			shapeName = shaplist.shapeName;
			shapeText = shaplist.shapeText;
			DrawMethod d = new DrawMethod();
			shapeColor = d.colorToHexValue(shaplist.shapeColor);
			System.out.println("color before send:"+shapeColor);
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", "updateDraw");
			sendTextJson.put("shapeInitialX", shapeInitialX);
			sendTextJson.put("shapeInitialY", shapeInitialY);
			sendTextJson.put("shapeFinalX", shapeFinalX);
			sendTextJson.put("shapeFinalY", shapeFinalY);
			sendTextJson.put("shapeWidth", shapeWidth);
			sendTextJson.put("shapeText", shapeText);
			sendTextJson.put("shapeColor", shapeColor);
			sendTextJson.put("shapeName", shapeName);
			sendTextJson.put("changeX", x);
			sendTextJson.put("changeY", y);
			sendTextJson.put("clientNumber", this.clientNumber);
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void sendDraw(ShapeList shaplist) {
    	PrintWriter printWriter;
    	String shapeName,shapeText;
    	int shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY,shapeWidth,shapeFrameWidth,shapeFontWidth;
    	String shapeColor,shapeStyle;
    	
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			shapeInitialX = shaplist.shapeInitialX;
			shapeInitialY= shaplist.shapeInitialY;
			shapeFinalX = shaplist.shapeFinalX;
			shapeFinalY = shaplist.shapeFinalY;
			shapeWidth = shaplist.shapeWidth;
			shapeName = shaplist.shapeName;
			shapeText = shaplist.shapeText;
			
			shapeFrameWidth = shaplist.shapeFrameWidth;
		    shapeFontWidth =shaplist.shapeFontWidth;
		    shapeStyle =shaplist.shapeStyle;
		    
			DrawMethod d = new DrawMethod();
			shapeColor = d.colorToHexValue(shaplist.shapeColor);
			System.out.println("color before send:"+shapeColor);
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", "draw");
			sendTextJson.put("shapeInitialX", shapeInitialX);
			sendTextJson.put("shapeInitialY", shapeInitialY);
			sendTextJson.put("shapeFinalX", shapeFinalX);
			sendTextJson.put("shapeFinalY", shapeFinalY);
			sendTextJson.put("shapeWidth", shapeWidth);
			sendTextJson.put("shapeText", shapeText);
			sendTextJson.put("shapeColor", shapeColor);
			sendTextJson.put("shapeName", shapeName);
			sendTextJson.put("isFill", shaplist.isFill);
			
			sendTextJson.put("shapeFrameWidth", shapeFrameWidth);
			sendTextJson.put("shapeFontWidth", shapeFontWidth);
			sendTextJson.put("shapeStyle", shapeStyle);
			
			sendTextJson.put("clientNumber", this.clientNumber);
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void sendDisconnected() {
		PrintWriter printWriter;
	    
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			
			
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", "disconnected");
			sendTextJson.put("disconnect",true);
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void sendNewPage() {
		PrintWriter printWriter;
	    
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			
			
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", "newpage");
			
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void receiveText() {
		String command="";
		String time="";
		String text="";
		String usernameString="";
		String receiveText="";
		String line="";
		String receivingClientNumber;
		boolean disconnect;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			while(true) {
				line=bufferedReader.readLine();
				
				if(line !=null) {
					
					JSONObject receiveTextJson = new JSONObject(line);
					command=receiveTextJson.getString("command");
					switch(command) {
					case "chat":
						usernameString = receiveTextJson.getString("username");
						time = receiveTextJson.getString("time");
						text = receiveTextJson.getString("text");
						receivingClientNumber=receiveTextJson.getString("clientNumber");
						receiveText = usernameString+receivingClientNumber + " " + time + ":\n " + text;
						
						window.setChatInfoTextArea(receiveText);
						break;
					case "initial":
						waiting.setVisible(false);
						clientNumber=receiveTextJson.getInt("clientNumber");
						JOptionPane.showMessageDialog(null, "Join Draw Board successful");
						window.setUsernameAndClientNumber(username,String.valueOf(clientNumber));
						break;
					case "draw":
						receiveDraw(receiveTextJson);
						break;
					case "disconnect":
						disconnect=receiveTextJson.getBoolean("disconnect");
						if(disconnect) {
							JOptionPane.showMessageDialog(null, "Server has disconnected with you!");
							
							System.exit(0);
						}
						break;
					case "editingClients":
						String editingClients=receiveTextJson.getString("editingClients");
						window.setEditInfoTextArea(editingClients);
						break;
					case "newpage":
						window.clearBoard();
						window.clearMemory();
						break;
					case "close":
						window.clearBoard();
						JOptionPane.showMessageDialog(null, "server close the current file.");
						break;
					case "exit":
						window.clearBoard();
						JOptionPane.showMessageDialog(null, "server exit.");
						break;
						
					
					default:break;
					}
					
				}}
				
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveDraw(JSONObject receiveDrawJson) {
		
		String shapeName,shapeText,shapeStyle;
		Color shapeColor;
		int shapeInitialX,shapeInitialY,shapeFinalX, shapeFinalY,shapeWidth,shapeFrameWidth,shapeFontWidth;
		//BufferedReader bufferedReader;
		boolean isFill;
		try {
			if (receiveDrawJson != null) {
				shapeName = receiveDrawJson.getString("shapeName");
				shapeText = receiveDrawJson.getString("shapeText");
				DrawMethod d = new DrawMethod();
				shapeColor = d.StrToColor(receiveDrawJson.getString("shapeColor"));
				//System.out.println("color string after send:"+receiveDrawJson.getString("shapeColor"));
				System.out.println("receive draw");
				shapeInitialX =receiveDrawJson.getInt("shapeInitialX"); 
				shapeInitialY = receiveDrawJson.getInt("shapeInitialY");
				shapeFinalX = receiveDrawJson.getInt("shapeFinalX");
				shapeFinalY = receiveDrawJson.getInt("shapeFinalY");
				shapeWidth = receiveDrawJson.getInt("shapeWidth"); 
				isFill=receiveDrawJson.getBoolean("isFill");
				
			    shapeFrameWidth = receiveDrawJson.getInt("shapeFrameWidth");
			    shapeFontWidth =receiveDrawJson.getInt("shapeFontWidth");
			    shapeStyle =receiveDrawJson.getString("shapeStyle");
			    
				ShapeList shapelist = new ShapeList(shapeInitialX,shapeInitialY,shapeFinalX, shapeFinalY,shapeName,shapeColor,shapeWidth,shapeText,shapeFrameWidth,shapeFontWidth,shapeStyle);
				shapelist.isFill=isFill;
				window.getDrawMethod().listShape.add(shapelist);
				shapelist.drawShape((Graphics2D)window.getWhiteBoardPanel().getGraphics(),window);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
