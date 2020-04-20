import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SaveFile {
	
	
	/**
	 * Wirte file.
	 */
	public static void writeNewFile(List<ShapeList> graphs,String address,JPanel window) {
		FileOutputStream fos;
		System.out.println("Start to write...");
		try {
			fos = new FileOutputStream(address);
			ObjectOutputStream os = new ObjectOutputStream(
					fos);
			os.writeObject(graphs);// 将User对象写进文件
			
			os.close();
			//JOptionPane.showMessageDialog(null, "Save successfully", "Save",JOptionPane.OK_OPTION); 
		} catch (FileNotFoundException e1) {
			
		} catch(Exception e) {
			
		}
		Point relativeLocation = window.getLocationOnScreen();

		final Rectangle currentScreenBounds = window.getGraphicsConfiguration().getBounds();

		relativeLocation.x -= currentScreenBounds.x;
		relativeLocation.y -= currentScreenBounds.y;
		Rectangle rect = new Rectangle(relativeLocation.x, relativeLocation.y, window.getWidth(), window.getHeight());

        // 截屏操作
        BufferedImage bufImage = null;
		try {
			bufImage = new Robot().createScreenCapture(rect);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 保存截取的图片
		System.out.println(address);
		try {
			String []types=address.split("\\.");
			
			String type=types[1];
	        try {
				ImageIO.write(bufImage, type, new File(address));
				JOptionPane.showMessageDialog(null, "Save successfully", "Save",JOptionPane.OK_OPTION); 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (Exception e){
			JOptionPane.showMessageDialog(null, "Save successfully", "Save",JOptionPane.OK_OPTION); 
		}
		
		
	}
	
	/**
	 * Wirte file according to file type.
	 */
	public static void writeNewFile(List<ShapeList> graphs,String address,String type,JPanel window) {
		FileOutputStream fos;
		System.out.println("Start to write...");
		try {
			fos = new FileOutputStream(address);
			ObjectOutputStream os = new ObjectOutputStream(
					fos);
			os.writeObject(graphs);// 将User对象写进文件
			os.close();
			
		} catch (FileNotFoundException e1) {
			
		} catch(Exception e) {
			
		}
		Point relativeLocation = window.getLocationOnScreen();

		final Rectangle currentScreenBounds = window.getGraphicsConfiguration().getBounds();

		relativeLocation.x -= currentScreenBounds.x;
		relativeLocation.y -= currentScreenBounds.y;
		Rectangle rect = new Rectangle(relativeLocation.x, relativeLocation.y, window.getWidth(), window.getHeight());

        // 截屏操作
        BufferedImage bufImage = null;
		try {
			bufImage = new Robot().createScreenCapture(rect);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        // 保存截取的图片
		String path=address+'.'+type;
        try {
			ImageIO.write(bufImage, type, new File(path));
			JOptionPane.showMessageDialog(null, "Save successfully", "Save",JOptionPane.OK_OPTION); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
