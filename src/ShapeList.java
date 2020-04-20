import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.awt.BasicStroke;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;




public class ShapeList implements Serializable{
    public String shapeName,shapeText;
    public Color shapeColor;
    public int shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY,shapeWidth;
    private WhiteBoardGUI window;
    public boolean isFill=false;
    public int shapeFrameWidth = 1;
    public int shapeFontWidth =10;
    public String shapeStyle = "TimesRoman";
    
    public ShapeList(int x1, int y1, int x2, int y2, String name, Color color,int width, String text,int FrameWidth,int FontWidth,String Style) {
    	shapeInitialX = x1;
    	shapeInitialY = y1;
    	shapeFinalX = x2;
    	shapeFinalY = y2;
    	shapeName = name;
    	shapeColor = color;
    	shapeWidth = width;
    	shapeText = text;
    	shapeFrameWidth = FrameWidth;
    	shapeFontWidth = FontWidth;
    	shapeStyle = Style;
    	
    }
    public boolean containLocation(int x,int y) {
    	switch(shapeName) {
    	case "line":
    		if((y-this.shapeInitialY)/(this.shapeFinalY-this.shapeInitialY)==(x-this.shapeInitialX)/(this.shapeFinalX-this.shapeInitialX))
    			return true;
    		else
    			return false;
    		
    	case "circle":
    		if((y<=this.shapeFinalY && y>=this.shapeInitialY)&&(x<=this.shapeFinalX && x>=this.shapeInitialX))
    			return true;
    		else
    			return false;
    	case "oval":
    		if((y<=this.shapeFinalY && y>=this.shapeInitialY)&&(x<=this.shapeFinalX && x>=this.shapeInitialX))
    			return true;
    		else
    			return false;
    	case "rectangle":
    		//if((y==this.shapeInitialY ||y==this.shapeFinalY)&&(x<=this.shapeFinalX && x>=this.shapeInitialX)||
    		//(x==this.shapeInitialX ||x==this.shapeFinalX)&&(y<=this.shapeFinalY && y>=this.shapeInitialY))
    		if((y<=this.shapeFinalY && y>=this.shapeInitialY)&&(x<=this.shapeFinalX && x>=this.shapeInitialX))
    			return true;
    		else
    			return false;
    		
    	default:
    		return false;
    	}
    }
    public void drawShape(Graphics2D g, WhiteBoardGUI win) {
    	window = win;
    	
    	switch (shapeName) {
        case "line":
        	g.setStroke(new BasicStroke(shapeWidth));
            g.setColor(shapeColor);
            g.drawLine(shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY);
            break;

        case "rectangle":
        	g.setStroke(new BasicStroke(shapeWidth));
            g.setColor(shapeColor);
            
        	if(isFill) {
        		g.fillRect(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX), Math.abs(shapeFinalY - shapeInitialY));
        	}
        	else {
        		g.drawRect(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX), Math.abs(shapeFinalY - shapeInitialY));
        	}
        	
            break;
            
        case "oval":
        	g.setStroke(new BasicStroke(shapeWidth));
            g.setColor(shapeColor);
            
        	if(isFill) {
        		g.fillOval(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX), Math.abs(shapeFinalY - shapeInitialY));
        	}
        	else {
        		g.drawOval(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX), Math.abs(shapeFinalY - shapeInitialY));
        	}
        	
            break;
            
        case "circle":
        	g.setStroke(new BasicStroke(shapeWidth));
            g.setColor(shapeColor);
            
        	if(isFill) {
        		g.fillOval(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX),Math.abs(shapeFinalX - shapeInitialX));
        	}
        	else {
        		g.drawOval(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX),Math.abs(shapeFinalX - shapeInitialX));
        	}
        	
            
            break;
            
        case "freehandDrawing":
        	g.setStroke(new BasicStroke(shapeWidth));
            g.setColor(shapeColor);
            g.drawLine(shapeInitialX,shapeInitialY, shapeFinalX, shapeFinalY);
            break;
            
        case "erasing":
        	g.setStroke(new BasicStroke(shapeWidth));
            g.setColor(Color.WHITE);
            g.drawLine(shapeInitialX, shapeInitialY, shapeFinalX, shapeFinalY);
            shapeInitialX = shapeFinalX;
            shapeInitialY = shapeFinalY;
            break;
         
        case "text":
        	window.getWhiteBoardPanel().setLayout(null);
        	window.textArea = new JTextArea();
        	
        	window.textArea.setBounds(shapeInitialX, shapeInitialY, Math.abs(shapeFinalX - shapeInitialX),
					Math.abs(shapeFinalY - shapeInitialY));
        	window.textArea.setBorder(BorderFactory.createLineBorder(Color.gray, shapeFrameWidth));
        	window.textArea.setFont(new Font(shapeStyle,Font.PLAIN,shapeFontWidth));
        	window.textArea.setVisible(true);
        	if (shapeText!=null) {
        		window.textArea.setText(shapeText);
        	}
        	window.textArea.getDocument().addDocumentListener(new Swing_OnValueChanged());
        	window.textArea.setEditable(true);
        	window.textArea.setLineWrap(true); 
        	window.textArea.setWrapStyleWord(true); 
        	//window.textArea.setText("test");
			window.getCleanText().add(window.textArea);
			window.getWhiteBoardPanel().add(window.textArea);
            break;
            
        case "changeText":
    		int iX=0,iY=0,fX=0,fY=0;
    		JTextArea t = new JTextArea();
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getX()==shapeInitialX && text.getY()==shapeInitialY) {
    	    		iX = text.getX();
    	    		iY = text.getY();
    	    		fX = text.getWidth();
    	    		fY = text.getHeight();
    	    		t = text;
    	    		}
    			}
    		for(int i=0;i<window.getListShape().size();i++) {
    			ShapeList s = window.getListShape().get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				window.getListShape().get(i).shapeText = shapeText;
    				window.getListShape().remove(window.getListShape().get(window.getListShape().size()-1));
    				if(window.getIsServer()) {
    					window.getServer().sendNewFile();
    				}
    			}
    				
    		}
        	break;
        case "dashedLine":
            	//System.out.println("this is drawing line");   
            	float[] dash = { 2f, 0f, 2f };
            	BasicStroke bs = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 1.0f, dash, 2f);
            	g.setStroke(bs);
                g.drawLine(shapeInitialX, shapeInitialY, shapeFinalX, shapeFinalY);
//                ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit");
//                listShape.add(shaplist1);
//                sendDraw(shaplist1);
                break;
    			

        case "directionalConnector":
            	//System.out.println("this is drawing line"); 
        	    DrawMethod m = new DrawMethod();
            	m.drawAL(shapeInitialX, shapeInitialY, shapeFinalX, shapeFinalY, g);     
//              ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit");
//              listShape.add(shaplist1);
//              sendDraw(shaplist1);
            	break;
        case "link" :
            	//System.out.println("this is drawing line"); 
            	g.drawLine(shapeInitialX-4, shapeInitialY-4, shapeFinalX-4, shapeFinalY-4);  
            	g.drawLine(shapeInitialX+4, shapeInitialY+4, shapeFinalX+4, shapeFinalY+4); 
//              ShapeList shaplist1 = new ShapeList(initialX, initialY, finalX, finalY,shape,color,width,"noEdit");
//              listShape.add(shaplist1);
//              sendDraw(shaplist1);
                break;
        }

    }
    class Swing_OnValueChanged implements DocumentListener{

    	@Override
    	public void changedUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
    		/**
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument() ) {
    				shapeInitialX = text.getX();
    				shapeInitialY = text.getY();
    				shapeFinalX = text.getWidth();
    				shapeFinalY = text.getHeight();
    	    		shapeName = "changeText";
    	    		shapeText = text.getText();   		
    	    		ShapeList shaplist1 = new ShapeList(shapeInitialX, shapeInitialY, shapeFinalX, shapeFinalY, shapeName, shapeColor, shapeWidth, shapeText);
    	    		window.getListShape().add(shaplist1);
    	    		window.getDrawMethod().sendDraw(shaplist1);
    	    		System.out.println("insert 2" + text.getBounds());
    			}
    		}
    		*/
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
    		for(int i=0;i<window.getListShape().size();i++) {
    			ShapeList s = window.getListShape().get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				window.getListShape().get(i).shapeText = t.getText();
    			}
    				
    		}
    		if(window.getIsServer()) {
    			window.getServer().sendNewFile();
    		}else {
    			ShapeList shapelist = new ShapeList(iX, iY, fX, fY, "changeText", shapeColor, shapeWidth, t.getText(),shapeFrameWidth,shapeFontWidth,shapeStyle);
    			window.getClient().sendDraw(shapelist);
    		}
    		
    	}

    	@Override
    	public void insertUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
    		/**
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument() ) {
    				shapeInitialX = text.getX();
    				shapeInitialY = text.getY();
    				shapeFinalX = text.getWidth();
    				shapeFinalY = text.getHeight();
    	    		shapeName = "changeText";
    	    		shapeText = text.getText();   		
    	    		ShapeList shaplist1 = new ShapeList(shapeInitialX, shapeInitialY, shapeFinalX, shapeFinalY, shapeName, shapeColor, shapeWidth, shapeText);
    	    		window.getListShape().add(shaplist1);
    	    		window.getDrawMethod().sendDraw(shaplist1);
    	    		System.out.println("insert 2" + text.getBounds());
    			}
    		}
    		*/
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
    		for(int i=0;i<window.getListShape().size();i++) {
    			ShapeList s = window.getListShape().get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				window.getListShape().get(i).shapeText = t.getText();
    			}
    				
    		}
    		if(window.getIsServer()) {
    			window.getServer().sendNewFile();
    		}
    		
    		else {
    			ShapeList shapelist = new ShapeList(iX, iY, fX, fY, "changeText", shapeColor, shapeWidth, t.getText(),shapeFrameWidth,shapeFontWidth,shapeStyle);
    			window.getClient().sendDraw(shapelist);
    		}
    		
    	}

    	@Override
    	public void removeUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
    		/**
    		for (JTextArea text:window.getCleanText()) {
    			if (text.getDocument() == e.getDocument()) {
    				shapeInitialX = text.getX();
    				shapeInitialY = text.getY();
    				shapeFinalX = text.getWidth();
    				shapeFinalY = text.getHeight();
    	    		shapeName = "changeText";
    	    		shapeText = text.getText();   		
    	    		ShapeList shaplist1 = new ShapeList(shapeInitialX, shapeInitialY, shapeFinalX, shapeFinalY, shapeName, shapeColor, shapeWidth, shapeText);
    	    		window.getListShape().add(shaplist1);
    	    		window.getDrawMethod().sendDraw(shaplist1);
    	    		System.out.println("remove 3" + text.getBounds());
    			}
    		}
    		*/
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
    		for(int i=0;i<window.getListShape().size();i++) {
    			ShapeList s = window.getListShape().get(i);
    			if(s.shapeInitialX==iX&&s.shapeInitialY==iY&&s.shapeName=="text") {
    				window.getListShape().get(i).shapeText = t.getText();
    			}
    				
    		} 
    		if(window.getIsServer()) {
    			window.getServer().sendNewFile();
    		}
    		else {
    			ShapeList shapelist = new ShapeList(iX, iY, fX, fY, "changeText", shapeColor, shapeWidth, t.getText(),shapeFrameWidth,shapeFontWidth,shapeStyle);
    			window.getClient().sendDraw(shapelist);
    		}
    		
    	}

    }
    public String toString() {
    	return this.shapeName+"*"+this.shapeColor+"*"+shapeInitialX+"*"+shapeInitialY+"*"+shapeFinalX+"*"+shapeFinalY;
    }
    public void setColor(Color color) {
    	this.shapeColor=color;
    }
    public Color getColor() {
    	return this.shapeColor;
    }
    
    public void changeLocation(int x,int y) {
    	this.shapeInitialX+=x;
    	this.shapeFinalX+=x;
    	this.shapeFinalY+=y;
    	this.shapeInitialY+=y;
    }
    
    
}


