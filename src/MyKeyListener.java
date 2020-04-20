import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MyKeyListener implements KeyListener{
	public int key;
	ShapeList selectedShape;
	DrawMethod dm;
	WhiteBoardGUI window;
	public MyKeyListener(ShapeList selectedShape, DrawMethod dm,WhiteBoardGUI window) {
		this.selectedShape=selectedShape;
		this.dm=dm;
		this.window=window;
	}
	public void keyPressed(KeyEvent e) {
		
		int action=e.getKeyChar();
		
		if(window.getIsServer()) {
			switch(action) {
			case 119:
				this.selectedShape.changeLocation(0, -1);
				break;
			case 97:
				this.selectedShape.changeLocation(-1, -0);
				break;
			case 115:
				this.selectedShape.changeLocation(0, 1);
				break;
			case 100:
				this.selectedShape.changeLocation(1, 0);
				break;
			case 8:
				dm.listShape.remove(this.selectedShape);
				break;
			default:
				break;
			}
			window.getServer().sendNewFile();
		}
		else {
			System.out.println(selectedShape);
			switch(action) {
			case 119:
				window.getClient().sendUpdateDraw(selectedShape, 0, -1);
				this.selectedShape.changeLocation(0, -1);
				break;
			case 97:
				window.getClient().sendUpdateDraw(selectedShape, -1, 0);
				this.selectedShape.changeLocation(-1, 0);
				break;
			case 115:
				window.getClient().sendUpdateDraw(selectedShape, 0, 1);
				this.selectedShape.changeLocation(0, 1);
				break;
			case 100:
				window.getClient().sendUpdateDraw(selectedShape, 1, 0);
				this.selectedShape.changeLocation(1, 0);
				break;
			case 8:
				dm.listShape.remove(this.selectedShape);
				break;
			default:
				break;
			}
			
		}
		
		dm.clearBoard();
		dm.drawAllItems();
		
		
	}
 
	public void keyReleased(KeyEvent e) {
		//System.out.println("键盘" + KeyEvent.getKeyText(e.getKeyCode()) + "键松开\n");
	}
 
	public void keyTyped(KeyEvent e) {
		//System.out.println("输入的内容是" + e.getKeyChar() + "\n");
	}
	
	public void setSelectedShape(ShapeList selectedShape) {
		this.selectedShape=selectedShape;
		this.dm=dm;
	}
}