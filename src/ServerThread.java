import java.net.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;

import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import org.json.JSONObject;

public class ServerThread implements Runnable {
	private Socket socket;
	private JTextArea logFiled;
	private int clientNumber;
	
	
	private String username;
	private boolean endThreadFlag = false;
	private int clientPort;
	private InetAddress clientAddress;
	private String clientInformation;
	private JRadioButton rdbtnAllowChangeDatabase;
	private WhiteBoardGUI window;
	private JSONObject receiveTextJsonCopy;
	private BufferedReader bufferedReader=null;
	private PrintWriter printWriter=null;
	private Server server;
	private Log log;

	public ServerThread(Socket socket, int clientNumber, String username,Log log, WhiteBoardGUI window,Server server) {
		this.socket = socket;
		this.username = username;
		this.clientNumber = clientNumber;
		this.logFiled = logFiled;
		this.clientPort = socket.getPort();
		this.clientAddress = socket.getInetAddress();
		this.clientInformation = "Client number: " + this.clientNumber + ", Port: " + this.clientPort + ", Address:"
				+ this.clientAddress;
		
		this.window = window;
		this.server=server;
		this.log=log;
	}

	public String getUserName() {
		return this.username;
	}
	
	public int getClientNumber() {
		return this.clientNumber;
	}
	
	public String getClientName() {
		return this.username+"_Client_" + this.clientNumber;
	}

	public String getClientInformation() {
		return username+"_Client number_" + this.clientNumber + ", Port: " + this.clientPort + ", Address:"
				+ this.clientAddress;
	}

	public void setExitStauts(boolean isExit) {
		this.endThreadFlag = isExit;
	}

	/*
	 * The thread operation
	 */

	public synchronized void run() {
		
			receiveAndSendText();

			
			try {
				if (printWriter != null)
					printWriter.close();
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException ex) {
				// System.out.println("I/O error!");
				ex.printStackTrace();
			}
		
	}
	public void sendCurrentDraw(DrawMethod dm) {
		for(ShapeList s:dm.listShape) {
			sendDraw(s);
		}
		
	}
	
	public void sendNewPage() {
		String command="newpage";
		
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
	public void sendClose() {
		String command="close";
		
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
	
	public void sendExit() {
		String command="exit";
		
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
	
	public void sendInitialParameter() {
		String command="initial";
		
		try {
			printWriter = new PrintWriter(socket.getOutputStream());

			//date


			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", command);
			sendTextJson.put("clientNumber", clientNumber);
			
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void receiveDraw(JSONObject receiveDrawJson) {
	
		String shapeName,shapeText,shapeStyle;
		Color shapeColor;
		int shapeInitialX,shapeInitialY,shapeFinalX, shapeFinalY,shapeWidth,shapeFrameWidth,shapeFontWidth;
		int sentClientNumber;
		boolean isFill;
		//BufferedReader bufferedReader;
		try {
			if (receiveDrawJson != null) {
				shapeName = receiveDrawJson.getString("shapeName");
				shapeText = receiveDrawJson.getString("shapeText");
				DrawMethod d = new DrawMethod();
				shapeColor = d.StrToColor(receiveDrawJson.getString("shapeColor"));
				shapeInitialX =receiveDrawJson.getInt("shapeInitialX"); 
				shapeInitialY = receiveDrawJson.getInt("shapeInitialY");
				shapeFinalX = receiveDrawJson.getInt("shapeFinalX");
				shapeFinalY = receiveDrawJson.getInt("shapeFinalY");
				shapeWidth = receiveDrawJson.getInt("shapeWidth"); 
				sentClientNumber=receiveDrawJson.getInt("clientNumber");
				log.recordLog("Client "+clientNumber+" draw a "+shapeName);
				window.updateLog(log);
				isFill=receiveDrawJson.getBoolean("isFill");
				
				shapeFrameWidth = receiveDrawJson.getInt("shapeFrameWidth");
			    shapeFontWidth =receiveDrawJson.getInt("shapeFontWidth");
			    shapeStyle =receiveDrawJson.getString("shapeStyle");
			    
				ShapeList shapelist = new ShapeList(shapeInitialX,shapeInitialY,shapeFinalX, shapeFinalY,shapeName,shapeColor,shapeWidth,shapeText,shapeFrameWidth,shapeFontWidth,shapeStyle);
				shapelist.isFill=isFill;
				window.getDrawMethod().listShape.add(shapelist);
				shapelist.drawShape((Graphics2D)window.getWhiteBoardPanel().getGraphics(),window);
				window.getServer().sendDrawToAll(shapelist,sentClientNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void receiveUpdateDraw(JSONObject receiveDrawJson) {
		
		String shapeName,shapeText;
		Color shapeColor;
		int shapeInitialX,shapeInitialY,shapeFinalX, shapeFinalY,shapeWidth,changeX,changeY;
		int sentClientNumber;
		System.out.println("receiving update draw");
		//BufferedReader bufferedReader;
		try {
			if (receiveDrawJson != null) {
				shapeName = receiveDrawJson.getString("shapeName");
				shapeText = receiveDrawJson.getString("shapeText");
				DrawMethod d = new DrawMethod();
				shapeColor = d.StrToColor(receiveDrawJson.getString("shapeColor"));
				shapeInitialX =receiveDrawJson.getInt("shapeInitialX"); 
				shapeInitialY = receiveDrawJson.getInt("shapeInitialY");
				shapeFinalX = receiveDrawJson.getInt("shapeFinalX");
				shapeFinalY = receiveDrawJson.getInt("shapeFinalY");
				shapeWidth = receiveDrawJson.getInt("shapeWidth"); 
				sentClientNumber=receiveDrawJson.getInt("clientNumber");
				changeX=receiveDrawJson.getInt("changeX");
				changeY=receiveDrawJson.getInt("changeY");
				log.recordLog("Client "+clientNumber+" update a "+shapeName);
				window.updateLog(log);
				ShapeList selectedShape=null;
				for(ShapeList i:window.getDrawMethod().listShape) {
					System.out.println(i);
					if(i.shapeName==shapeName&&i.shapeInitialX==shapeInitialX&&
							i.shapeInitialY==shapeInitialY&&i.shapeFinalX==shapeFinalX
							&&i.shapeFinalY==shapeFinalY) {
						System.out.println("update shape location");
						selectedShape=i;
						
						break;
					}
				}
				try {
					selectedShape.changeLocation(changeX, changeY);
				}
				catch(Exception e) {
					
				}
				
				window.clearBoard();
				window.getDrawMethod().drawAllItems();
				
				window.getServer().sendNewFile(sentClientNumber);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void receiveAndSendText() {
		String command="";
		String time="";
		String text="";
		String usernameString="";
		String receiveText="";
		String line="";
		String receivingClientNumber;
		
		JSONObject receiveTextJson=null;
		
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			printWriter = new PrintWriter(socket.getOutputStream());
			while(true) {
			line=bufferedReader.readLine();		
			if(line != null) {
				receiveTextJson = new JSONObject(line);
				command=receiveTextJson.getString("command");
				switch(command) {
				case "chat":
					usernameString = receiveTextJson.getString("username");
					time = receiveTextJson.getString("time");
					text = receiveTextJson.getString("text");
					receivingClientNumber=receiveTextJson.getString("clientNumber");
					receiveText = usernameString +receivingClientNumber+" " + time + ": \n" + text;
					window.setChatInfoTextArea(receiveText);
					server.sendMessage(receiveTextJson);
					break;
				case "initial":
					clientNumber=receiveTextJson.getInt("clientNumber");
					
					break;
				case "draw":
					receiveDraw(receiveTextJson);
					break;
				case "initialClient":
					
					this.username = receiveTextJson.getString("username");
					int tips=JOptionPane.showConfirmDialog(null, this.username+" requests join, do you allow?", "Tips",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(tips==JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(null, this.getClientName()+" joined.");
						this.sendInitialParameter();
						this.sendCurrentDraw(window.getDrawMethod());
						log.recordLog("Client "+clientNumber+" join.");
						window.updateLog(log);
						server.sendEditingClientToAll();
					}
					else {
						this.sendDisconnect();
					}
					break;
				case "disconnected":
					server.removeThread(clientNumber);
					log.recordLog("Client "+clientNumber+" leave.");
					break;
				case "newpage":
					window.clearBoard();
					server.sendNewPage();
					log.recordLog("Client "+clientNumber+" start a new page");
					window.updateLog(log);
				case "undo":
					System.out.println("******");
					window.getDrawMethod().undo();
					server.sendNewFile();
					break;
				case "redo":
					window.getDrawMethod().redo();
					server.sendNewFile();
					break;
				case "updateDraw":
					receiveUpdateDraw(receiveTextJson);
					break;
				default:break;
				}
			}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDisconnect() {
		PrintWriter printWriter;
    
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			
			
			JSONObject sendTextJson = new JSONObject();
			sendTextJson.put("command", "disconnect");
			sendTextJson.put("disconnect",true);
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
    	boolean isFill;
		try {
			printWriter = new PrintWriter(socket.getOutputStream());
			shapeInitialX = shaplist.shapeInitialX;
			shapeInitialY= shaplist.shapeInitialY;
			shapeFinalX = shaplist.shapeFinalX;
			shapeFinalY = shaplist.shapeFinalY;
			shapeWidth = shaplist.shapeWidth;
			shapeName = shaplist.shapeName;
			shapeText = shaplist.shapeText;
			isFill=shaplist.isFill;
			DrawMethod d = new DrawMethod();
			shapeColor = d.colorToHexValue(shaplist.shapeColor);
			
			shapeFrameWidth = shaplist.shapeFrameWidth;
		    shapeFontWidth =shaplist.shapeFontWidth;
		    shapeStyle =shaplist.shapeStyle;
			
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
			sendTextJson.put("isFill", isFill);
			
			sendTextJson.put("shapeFrameWidth", shapeFrameWidth);
			sendTextJson.put("shapeFontWidth", shapeFontWidth);
			sendTextJson.put("shapeStyle", shapeStyle);
			
			printWriter.write(sendTextJson.toString() + "\n");
			printWriter.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void sendText(JSONObject receiveTextJson) {
		
		printWriter.write(receiveTextJson.toString() + "\n");
		printWriter.flush();
	}

}