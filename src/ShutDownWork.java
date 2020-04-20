/**
 * This class is used to execute the operation when the system is exist
 * the operation includes: saving,close socket sately, record log file
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;

import java.net.ServerSocket;
import java.net.Socket;


public class ShutDownWork extends Thread{
	//this is the class the run the operation before close
	private Socket socket;             //Prepare for future implementation
	private ServerSocket server;	   //Prepare  for future implementation
	private List<ShapeList> graphs;		   //The graphs such as lines, circles,
	private String address;			   //The path that to save file
	ArrayList<ServerThread> threadList;
	
	public ShutDownWork(Socket socket,ServerSocket server,String address,WhiteBoardGUI window,ArrayList<ServerThread> threadList) {
		this.socket=socket;
		this.server=server;
		this.graphs=graphs;
		this.address=address;
		this.threadList=threadList;
		
	}
	
	public synchronized void run() {
		System.out.println("start to close");
		
		closeSocket();
		closeThread();
		
		
		//askSaving();
		System.out.println("has asked");
		
		
		//SaveFile.writeNewFile(graphs, address);
		
		//Write log file
		//Log.writeLogToFile();
	}
	
	private void askSaving() {
		int tips = JOptionPane.showConfirmDialog(null, "Do you want to exit the Shared White Board?", "Tips",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (tips == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else {
			return;
		}
	}
	private void closeThread() {
		for(ServerThread thread:threadList) {
			try {
				thread.sendExit();
				//Send message to all clients to disconnect
				thread.sendDisconnect();;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void closeSocket() {
		try {
			socket.close();
			System.out.println("socket has closed");
			server.close();
		} catch (IOException e) {
			//Log.recordLog(e.getMessage());
		}
		catch (Exception e) {
			//Log.recordLog(e.getMessage());
		}
	}
}
