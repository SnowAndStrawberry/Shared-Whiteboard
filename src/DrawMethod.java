import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.GeneralPath;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.json.JSONObject;

import com.sun.glass.events.KeyEvent;

public class DrawMethod extends MouseAdapter implements ActionListener{
    public int initialX,initialY, finalX, finalY;  // define the initial location and final location to record the mouse event.
    public Graphics2D graphics;  // define the pencil.
    public String shape = "line";  //define the shape need to draw.
    public String changeText = "noEdit";
    public Color color=Color.BLACK;  //define the color.
    public int width = 1;
    public int pencilWidth = 1;
    public int erasingWidth = 20;
    public String textcontent;
    public int frameWidth = 1;
    public int fontWidth =10;
    public String style = "TimesRoman";
    
    public boolean fillFlag = false;
    
    public JPanel whiteBoard;
    public List<ShapeList> listShape = new ArrayList<ShapeList>();
    public List<JTextArea> cleanText = new ArrayList<JTextArea>(); 
    public boolean isServer=true;
    private WhiteBoardGUI window;
    public String path="";
    public String fileType="";
    private MyKeyListener listener;
    private List a = new ArrayList();
    public DrawMethod(JPanel whiteBoardPanel,WhiteBoardGUI window) {
    	whiteBoard = whiteBoardPanel;
    	graphics=(Graphics2D) whiteBoard.getGraphics();
    	this.window=window;
    	listener=new MyKeyListener(selectedShape,this,window);
    	
    }
    public DrawMethod() {
    	listener=new MyKeyListener(selectedShape,this,window);
    }
    
   
    
    public void setListShape(List<ShapeList> listShape){
    	this.listShape=listShape;
    }
    // get the shape from UI button.
    // @param mouse event
    public void actionPerformed(ActionEvent e) {
       
    }
    
    // get the initial location when press the mouse.
    // @param mouse event
    Color originalColor;
    ShapeList selectedShape;
    public void mousePressed(MouseEvent e) {
    	graphics=(Graphics2D) whiteBoard.getGraphics();
    	graphics.setColor(color);   
        initialX = e.getX();
        initialY = e.getY();
        
        if(shape.equals("chooseshape")) {
        	if(selectedShape!=null) {
        		selectedShape.setColor(originalColor);
        		this.drawAllItems(graphics, window);
        		window.getServer().sendNewFile();
        	}
        	for(ShapeList i:this.listShape) {
        		
        		if(!i.shapeName.equals("freehandDrawing") &&!i.shapeName.equals("erasing")) {
        			if(i.containLocation(initialX, initialY)) {
        				window.getChooseShapeButton().addKeyListener(listener);
        				selectedShape=i;
        				originalColor=i.getColor();
        				
        				i.setColor(Color.red);
        				this.drawAllItems(graphics, window);
        				listener.setSelectedShape(selectedShape);
        				break;
        			}
        		}
        	}
        }
        
    }
    
    // get the final location when release the mouse. draw the different shape.
    // @param mouse event
    public void mouseReleased(MouseEvent e) {
        finalX = e.getX();
        finalY = e.getY();     
        graphics.setStroke(new BasicStroke(width));
        window.getWhiteBoardPanel().setLayout(null);
        window.clearBoard();
        a = new ArrayList();
    	this.drawAllItems(graphics, window);
    	graphics.setColor(color);
        if (shape.equals("line")) {
        	//System.out.println("this is drawing line");        
            graphics.drawLine(initialX, initialY, finalX, finalY);
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            listShape.add(shaplist1);
            sendDraw(shaplist1);
        } else if (shape.equals("circle")) {
        	//System.out.println(graphics);
        	
        	if (fillFlag) {
     
        		graphics.fillOval(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalX - initialX));
        	}else {
        		graphics.drawOval(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalX - initialX));
        	}            
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            shaplist1.isFill=fillFlag;
            listShape.add(shaplist1);
            sendDraw(shaplist1);
        } else if (shape.equals("rectangle")) {
        	if (fillFlag) {
        		graphics.fillRect(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
        	}else {
        		graphics.drawRect(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
        	}
            
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            shaplist1.isFill=fillFlag;
            listShape.add(shaplist1);
            sendDraw(shaplist1);
        } else if (shape.equals("oval")) {
        	if (fillFlag) {
        		graphics.fillOval(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
        	}else {
        		graphics.drawOval(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
        	}
            
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            shaplist1.isFill=fillFlag;
            listShape.add(shaplist1);
            sendDraw(shaplist1);
        }else if (shape.equals("text")) {
        	window.textArea = new JTextArea();
        	window.textArea.getDocument().addDocumentListener(new Swing_OnValueChanged());
        	window.textArea.setFont(new Font(style,Font.PLAIN,fontWidth));
        	window.textArea.setBounds(initialX, initialY, Math.abs(finalX - initialX),
					Math.abs(finalY - initialY));
        	window.textArea.setBorder(BorderFactory.createLineBorder(Color.gray, frameWidth));
        	window.textArea.setVisible(true);
        	window.textArea.setEditable(true);
        	window.textArea.setLineWrap(true); 
        	window.textArea.setWrapStyleWord(true); 
			ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY, shape, color, width, textcontent,frameWidth,fontWidth,style);
			cleanText.add(window.textArea);
			listShape.add(shaplist1);
			window.getWhiteBoardPanel().add(window.textArea);
			sendDraw(shaplist1);
			

        }else if (shape.equals("dashedLine")) {
        	//System.out.println("this is drawing line");   
        	float[] dash = { 2f, 0f, 2f };
        	BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
        	graphics.setStroke(bs);
            graphics.drawLine(initialX, initialY, finalX, finalY);
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            listShape.add(shaplist1);
            sendDraw(shaplist1);
			

        }else if (shape.equals("directionalConnector")) {
        	//System.out.println("this is drawing line"); 
        	drawAL(initialX, initialY, finalX, finalY, graphics);     
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            listShape.add(shaplist1);
            sendDraw(shaplist1);
        }else if (shape.equals("link")) {
        	//System.out.println("this is drawing line"); 
        	graphics.drawLine(initialX-4, initialY-4, finalX-4, finalY-4);  
        	graphics.drawLine(initialX+4, initialY+4, finalX+4, finalY+4); 
        	ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit",frameWidth,fontWidth,style);
            listShape.add(shaplist1);
            sendDraw(shaplist1);
        }

        window.setNeedSavingFlag(true);
    }
    
    /**
     *drag the mouse to draw the freehand drawing and erasing.
     *erasing: draw a white(same as background) line to cover the shape before 
     *@param mouse event
     */
    public void mouseDragged(MouseEvent e) {
    	
        finalX = e.getX();
        finalY = e.getY();
        if (shape.equals("freehandDrawing")){
        	graphics.setStroke(new BasicStroke(pencilWidth));
			graphics.drawLine(initialX, initialY, finalX, finalY);
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,pencilWidth,"noEdit",frameWidth,fontWidth,style);
            listShape.add(shaplist1);
            sendDraw(shaplist1);
			initialX = finalX;
			initialY = finalY;
        }else if (shape.equals("erasing")) {
        	graphics.setStroke(new BasicStroke(erasingWidth));
            graphics.setColor(Color.WHITE);
            graphics.drawLine(initialX, initialY, finalX, finalY);
            ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,erasingWidth,"noEdit",frameWidth,fontWidth,style);
            listShape.add(shaplist1);
            sendDraw(shaplist1);
            initialX = finalX;
            initialY = finalY;
        } else if (shape.equals("circle")) {
        	
        	window.clearBoard();
        	this.drawAllItems(graphics, window);
        	graphics.setColor(color); 
            graphics.drawOval(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalX - initialX));
            //int[] arr1 = {initialX,initialY,Math.abs(finalX - initialX),Math.abs(finalX - initialX)};
            //a.add(arr1);          
            //int[] arr2 = (int[]) a.get(a.size()-2);
            //graphics.setColor(new Color(0,0,0,0));
            //graphics.drawOval(arr2[0], arr2[1], arr2[2], arr2[3]);
            //graphics.setColor(color);
                   
           
        }else if (shape.equals("rectangle")) {
        	//System.out.println(graphics);
        	window.clearBoard();
        	this.drawAllItems(graphics, window);
        	graphics.setColor(color);
        	graphics.drawRect(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
            //int[] arr1 = {initialX,initialY,Math.abs(finalX - initialX),Math.abs(finalY - initialY)};
            //a.add(arr1);
            //int[] arr2 = (int[]) a.get(a.size()-2);
            //graphics.drawRect(arr2[0], arr2[1], arr2[2], arr2[3]);
            //graphics.setColor(color);
            
            
        }
        else if (shape.equals("oval")) {
        	//System.out.println(graphics);
        	window.clearBoard();
        	this.drawAllItems(graphics, window);
        	graphics.setColor(color);
        	graphics.drawOval(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
            //int[] arr1 = {initialX,initialY,Math.abs(finalX - initialX),Math.abs(finalY - initialY)};
            //a.add(arr1);
            //int[] arr2 = (int[]) a.get(a.size()-2);
            //graphics.drawOval(arr2[0], arr2[1], arr2[2], arr2[3]);
            //graphics.setColor(color);
            
            
        }
        else if (shape.equals("text")) {
        	//System.out.println(graphics);
        	window.clearBoard();
        	this.drawAllItems(graphics, window);
        	graphics.setColor(color);
        	graphics.drawRect(initialX, initialY, Math.abs(finalX - initialX), Math.abs(finalY - initialY));
            //int[] arr1 = {initialX,initialY,Math.abs(finalX - initialX),Math.abs(finalY - initialY)};
            //a.add(arr1);
            //int[] arr2 = (int[]) a.get(a.size()-2);
            //graphics.drawRect(arr2[0], arr2[1], arr2[2], arr2[3]);
            //graphics.setColor(color);
            
            
        }
        else if (shape.equals("line")) {
        	
        	window.clearBoard();
        	this.drawAllItems(graphics, window);
        	graphics.setColor(color);
        	graphics.drawLine(initialX, initialY, finalX, finalY);
            
            //int[] arr1 = {initialX, initialY, finalX, finalY};
            //a.add(arr1);
            //int[] arr2 = (int[]) a.get(a.size()-2);
            //graphics.drawLine(arr2[0], arr2[1], arr2[2], arr2[3]);
            //graphics.setColor(color);
        }
        window.setNeedSavingFlag(true);
    }
    
    
    
    public void cleanShape() {
    	listShape = new ArrayList<ShapeList>();
    }
    
    public void clearMemory() {
    	listShape = new ArrayList<ShapeList>();
    	path="";
    }
    
    public void clearAllItems(Graphics2D g) {
	 g.setStroke(new BasicStroke(800));
	 g.setColor(Color.WHITE);
     g.drawLine(0,0,whiteBoard.getWidth(),whiteBoard.getHeight());
     g.setStroke(new BasicStroke(1));
     g.setColor(Color.BLACK);
    	}
    public void clearBoard() {
    	this.window.clearBoard();
    }
    public void drawAllItems(Graphics2D g,WhiteBoardGUI window) {
    	
    	for(ShapeList i:listShape) {
    		//System.out.println("draw all items:"+i);
    		i.drawShape(g,window);
    	}
    	
    }
    public void drawAllItems() {
    	
    	for(ShapeList i:listShape) {
    		//System.out.println("draw all items:"+i);
    		i.drawShape(graphics,window);
    	}
    	
    }
    
    public void sendDraw(ShapeList shaplist) {
    	if(window.getIsServer()) {
    		window.getServer().sendDrawToAll(shaplist);
    	}
    	else {
    		window.getClient().sendDraw(shaplist);
    	}
	}
    	
    public String colorToHexValue(Color color) {
    	  return intToHexValue(color.getAlpha()) + intToHexValue(color.getRed()) + intToHexValue(color.getGreen()) + intToHexValue(color.getBlue());
    	 }
    	 
    public String intToHexValue(int number) {
    	  String result = Integer.toHexString(number & 0xff);
    	  while (result.length() < 2) {
    	   result = "0" + result;
    	  }
    	  return result.toUpperCase();
    	 }
    	 
    public Color StrToColor(String str) {
    	  String str1 = str.substring(0, 2);
    	  String str2 = str.substring(2, 4);
    	  String str3 = str.substring(4, 6);
    	  String str4 = str.substring(6, 8);
    	  int alpha = Integer.parseInt(str1, 16);
    	  int red = Integer.parseInt(str2, 16);
    	  int green = Integer.parseInt(str3, 16);
    	  int blue = Integer.parseInt(str4, 16);
    	  Color color = new Color(red, green, blue, alpha);
    	  return color;
    	 }
    
public void drawAL(int sx, int sy, int ex, int ey, Graphics2D g2)
	{
 
		double H = 10; // 绠ご楂樺害
		double L = 4; // 搴曡竟鐨勪竴鍗�
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 绠ご瑙掑害
		double arraow_len = Math.sqrt(L * L + H * H); // 绠ご鐨勯暱搴�
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)鏄涓�绔偣
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)鏄浜岀鐐�
		double y_4 = ey - arrXY_2[1];
 
		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// 鐢荤嚎
		g2.drawLine(sx, sy, ex, ey);
		//
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		//瀹炲績绠ご
		g2.fill(triangle);
		//闈炲疄蹇冪澶�
		//g2.draw(triangle);
 
	}
 
	// 璁＄畻
	public double[] rotateVec(int px, int py, double ang,
			boolean isChLen, double newLen) {
 
		double mathstr[] = new double[2];
		// 鐭㈤噺鏃嬭浆鍑芥暟锛屽弬鏁板惈涔夊垎鍒槸x鍒嗛噺銆亂鍒嗛噺銆佹棆杞銆佹槸鍚︽敼鍙橀暱搴︺�佹柊闀垮害
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}
	
    class Swing_OnValueChanged implements DocumentListener{

    	@Override
    	public void changedUpdate(DocumentEvent e) {
      		/**
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    	    		initialX = text.getX();
    	    		initialY = text.getY();
    	    		finalX = text.getWidth();
    	    		finalY = text.getHeight();
    	    		shape = "changeText";
    	    		changeText = text.getText();   		
    	    		ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY, shape, color, width, changeText);
    	    		listShape.add(shaplist1);
    	    		sendDraw(shaplist1);
    	    		//System.out.println("Attribute insert 2" + window.textArea.getBounds());
    			}
    		}
    		**/
    		int iX=0,iY=0,fX=0,fY=0;
    		JTextArea t = new JTextArea();
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    	    		iX = text.getX();
    	    		iY = text.getY();
    	    		fX = text.getWidth();
    	    		fY = text.getHeight();
    	    		t = text;
    	    		}
    			}
    		for(int i=0;i<listShape.size();i++) {
    			ShapeList s = listShape.get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				listShape.get(i).shapeText = t.getText();
    			}
    				
    		}
    		if(window.getIsServer()) {
    			window.getServer().sendNewFile();
    		}else {
    			ShapeList shapelist = new ShapeList(iX, iY, fX, fY, "changeText", color, width, t.getText(),frameWidth,fontWidth,style);
    			window.getClient().sendDraw(shapelist);
    		}
    		
    	}

    	@Override
    	public void insertUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
  		/**
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    	    		initialX = text.getX();
    	    		initialY = text.getY();
    	    		finalX = text.getWidth();
    	    		finalY = text.getHeight();
    	    		shape = "changeText";
    	    		changeText = text.getText();   		
    	    		ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY, shape, color, width, changeText);
    	    		listShape.add(shaplist1);
    	    		sendDraw(shaplist1);
    	    		//System.out.println("Attribute insert 2" + window.textArea.getBounds());
    			}
    		}
    		**/
    		int iX=0,iY=0,fX=0,fY=0;
    		JTextArea t = new JTextArea();
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    	    		iX = text.getX();
    	    		iY = text.getY();
    	    		fX = text.getWidth();
    	    		fY = text.getHeight();
    	    		t = text;
    	    		}
    			}
    		for(int i=0;i<listShape.size();i++) {
    			ShapeList s = listShape.get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				listShape.get(i).shapeText = t.getText();
    			}
    				
    		}
    		if(window.getIsServer()) {
    			window.getServer().sendNewFile();
    		}else {
    			ShapeList shapelist = new ShapeList(iX, iY, fX, fY, "changeText", color, width, t.getText(),frameWidth,fontWidth,style);
    			window.getClient().sendDraw(shapelist);
    		}
    	}

    	@Override
    	public void removeUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
      		/**
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    	    		initialX = text.getX();
    	    		initialY = text.getY();
    	    		finalX = text.getWidth();
    	    		finalY = text.getHeight();
    	    		shape = "changeText";
    	    		changeText = text.getText();   		
    	    		ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY, shape, color, width, changeText);
    	    		listShape.add(shaplist1);
    	    		sendDraw(shaplist1);
    	    		//System.out.println("Attribute insert 2" + window.textArea.getBounds());
    			}
    		}
    		**/
    		int iX=0,iY=0,fX=0,fY=0;
    		JTextArea t = new JTextArea();
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    	    		iX = text.getX();
    	    		iY = text.getY();
    	    		fX = text.getWidth();
    	    		fY = text.getHeight();
    	    		t = text;
    	    		}
    			}
    		for(int i=0;i<listShape.size();i++) {
    			ShapeList s = listShape.get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				listShape.get(i).shapeText = t.getText();
    			}
    				
    		}
    		if(window.getIsServer()) {
    			window.getServer().sendNewFile();
    		}else {
    			ShapeList shapelist = new ShapeList(iX, iY, fX, fY, "changeText", color, width, t.getText(),frameWidth,fontWidth,style);
    			window.getClient().sendDraw(shapelist);
    		}
    		
    	}

    }
    
    private Stack<ShapeList> st = new Stack<ShapeList>();
    public void undo() {
    	try {
    		st.add(listShape.get(listShape.size()-1));
    		this.listShape.remove(listShape.size()-1);
        	window.clearBoard();
        	System.out.println("undo");
        	this.drawAllItems(graphics, window);
    	}
    	catch(Exception e) {
    		
    	}
    }
    
    public void redo() {
    	try {
    		listShape.add(st.pop());
    		window.clearBoard();
        	System.out.println("redo");
        	this.drawAllItems(graphics, window);
    	}
    	catch(Exception e) {
    		
    	}
    }
    
    

}

