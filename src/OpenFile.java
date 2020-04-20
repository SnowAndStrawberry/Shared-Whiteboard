import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;

public class OpenFile {
	
	/**
	 * Read hashtable from the XML file.
	 */
	public static List<ShapeList> readHistoryFile(String address) {
		FileInputStream foi;
		List<ShapeList> graphs=new ArrayList<ShapeList>();
		try {
			foi = new FileInputStream(address);
			ObjectInputStream is = new ObjectInputStream(foi);
			
			graphs = (List<ShapeList>) is.readObject();
		} catch (FileNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "File not found", "Open File",JOptionPane.ERROR_MESSAGE); 
			
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Open File",JOptionPane.ERROR_MESSAGE); 
		}
		return graphs;
	}
	
	
}
