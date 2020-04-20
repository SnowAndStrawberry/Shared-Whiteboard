import java.util.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.text.SimpleDateFormat;
public class Log {
	private ArrayList log=new ArrayList(); //The log is used to record every log message
	
	public ArrayList getLog() {
		return this.log;
	}
	/**
	 * Record log, the parameter is the message that you want to record.
	 * A timestamp will be added automatically
	 */
	public void recordLog(String logMessage) {
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("hh:mm:ss");
		log.add(dateFormat.format(date)+" "+logMessage);
	}
	
	/**
	 * Write log into file.
	 */
	public void writeLogToFile() {
		FileInputStream foi;
		ArrayList pastLog=new ArrayList();
		try {
			foi = new FileInputStream("log.xml");
			XMLDecoder e = new XMLDecoder(foi); 
			pastLog=(ArrayList) e.readObject();
			System.out.println("Read log successful!");
			e.close(); 
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Read past log file failed"+e1.getMessage());
		} 
		FileOutputStream fos;
		System.out.println("Start to write log...");
		pastLog.addAll(log);
		try {
			fos = new FileOutputStream("log.xml");
			XMLEncoder e = new XMLEncoder(fos); 
			e.writeObject(pastLog); 
			e.close(); 
			System.out.println("Write log successful!");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("Write log failed..."+e1.getMessage());
		} 
	}
	
	
}
