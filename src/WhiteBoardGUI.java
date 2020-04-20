import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.json.JSONObject;

import java.awt.Graphics2D;

public class WhiteBoardGUI extends JFrame{
	private Server server;
	private Client client;
	public boolean needSavingFlag=true;
	
	private DrawMethod dm=new DrawMethod(); 
	private String clientInformation="";
	private String receiveText="";
	private JFrame frame = new JFrame(); // home frame
	private JFrame chooseFileFrame; // choose file frame
	private JFrame helpFrame; // help frame
	private JFrame aboutFrame; // about frame
	private JFrame manageFrame; // the management frame of manager
	
	private JFrame colorFrame; // choose color frame
	private JFrame widthFrame;//eraser Frame to choose width

	private Font homePageButtonFont; // home page font
	private Font otherFont; // other pages font
	private String clientNumberID;
	private JPanel homePanel; // home panel
	private JPanel startPanel; // white board panel
	private JPanel whiteBoardPanel; // white board panel paint area
	private JPanel chooseFilePanel; // choose file panel
	private JPanel helpPanel; // help panel
	private JPanel aboutPanel; // about panel
	private JPanel managePanel; // the management panel of manager
	
	private JPanel pencilPanel; // choose pencil size panel
	private JPanel widthPanel; // choose eraser size panel
	private JPanel textPanel; // choose text style, font,size panel
	private JPanel colorPanel; // choose color panel

	private JMenuBar menuBar; // the menu bar of white board

	private JMenu fileMenu; // the menu "file" of white board
	private JMenu helpMenu; // the menu "help" of white board
	private JMenu undoAndRedoMenu;
	private JMenuItem newMenuItem; // the menu item "new file" in menu "file"
	private JMenuItem openMenuItem; // the menu item "open file" in menu "file"
	private JMenuItem saveMenuItem; // the menu item "save file" in menu "file"
	private JMenuItem saveAsMenuItem; // the menu item "save as file" in menu "file"
	private JMenuItem closeMenuItem; // the menu item "close file" in menu "file"
	private JMenuItem helpMenuItem; // the menu item "help" in menu "help"
	private JMenuItem aboutMenuItem; // the menu item "about" in menu "help"
	private JMenuItem undoMenu;
	private JMenuItem redoMenu;
	private JToolBar toolBar; // the tool bar of white board
	
	private JTextArea editInfoTextArea;
	private JTextArea tipsTextArea; // the tool function description of tool bar
	private JTextArea helpTextArea; // the help text area of the menu item "help" in menu "help"ÃƒÂ¯Ã‚Â¼Ã…â€™with the
											// description of function
	private JTextArea aboutTextArea; // the about text area of the menu item "about" in menu "help"ÃƒÂ¯Ã‚Â¼Ã…â€™with the
											// description of clients
	private JTextArea clientListTextArea; // clients information area
	private JTextArea clientOperationInfoTextArea; // in the manager management interface, clients operation
	
															// information area
	private JTextArea chatInfoTextArea; // in the clients chat interface, the chat record area
	private JTextField chatInputTextField; // in the clients chat interface, chat input area
	
	public JTextArea textArea;// insert the text in white board
	private JTextField widthRec;// insert the text in white board

	private JScrollPane tipsScrollPane; // tool function description scroll
	private JScrollPane clientListScrollPane; // in the manager management interface, clients information scroll
	private JScrollPane clientOperationInfoScrollPane; // in the manager management interface, clients operation
																// information scroll
	private JScrollPane chatInfoScrollPane; // in the clients chat interface, chat record scroll

	private JButton startButton; // in home panelÃƒÂ¯Ã‚Â¼Ã…â€™start button
	private JButton exitButton; // in home panelÃƒÂ¯Ã‚Â¼Ã…â€™exit button
	private JButton browseButton; // the menu item "open file" in menu "file"ÃƒÂ¯Ã‚Â¼Ã…â€™browse button
	private JButton manageButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™manage button
	private JButton connectButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™connect button
	private JButton newDrawingButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™new page button
	private JButton pencilButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™pencil button
	private JButton lineButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™line button
	private JButton circleButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™circle button
	private JButton rectangleButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™rectangle button
	private JButton ovalButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™oval button
	private JButton eraserButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™eraser button
	private JButton textButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™text button
	private JButton colorButton; // in tool barÃƒÂ¯Ã‚Â¼Ã…â€™color button
	private JButton stopServiceButton; // in the manager management interfaceÃƒÂ¯Ã‚Â¼Ã…â€™stop service button
	private JButton succeedConnectionButton; // in the clients connection interfaceÃƒÂ¯Ã‚Â¼Ã…â€™connect button
	private JButton disconnectButton; // in the clients connection interfaceÃƒÂ¯Ã‚Â¼Ã…â€™disconnect button
	private JButton chooseColorButton; // in the color choose interfaceÃƒÂ¯Ã‚Â¼Ã…â€™choose color button
	private JButton sendChatButton; // in the clients chat interfaceÃƒÂ¯Ã‚Â¼Ã…â€™send message button
	private JButton chooseShapeButton;
	private JButton saveFileButton;
	private JButton openFileButton;
	private JButton colorButton1;
	private JButton colorButton2;
	private JButton colorButton3;
	private JButton colorButton4;
	private JButton colorButton5;
	private JButton colorButton6;
	private JButton colorButton7;
	private JButton colorButton8;
	private JButton colorButton9;
	private JButton colorButton10;
	private JButton colorButton11;
	private JButton colorButton12;
	private JButton colorButton13;
	private JButton colorButton14;
	private JButton colorButton15;
	private JButton colorButton16;
	private JButton colorButton17;
	private JButton colorButton18;
	private JButton colorButton19;
	private JButton colorButton20;
	private JButton colorButton21;
	private JButton colorButton22;
	private JButton colorButton23;
	private JButton colorButton24;
	
	private JLabel filePath; // the menu item "open file" in menu "file"ÃƒÂ¯Ã‚Â¼Ã…â€™file path input tips
	private JLabel help; // the menu item "help" in menu "help"ÃƒÂ¯Ã‚Â¼Ã…â€™help content tips
	private JLabel about; // the menu item "about" in menu "help"ÃƒÂ¯Ã‚Â¼Ã…â€™about content tips
	private JLabel clientList; // in the manager management interfaceÃƒÂ¯Ã‚Â¼Ã…â€™clients list tips
	private JLabel clientOperationInfo; // in the manager management interfaceÃƒÂ¯Ã‚Â¼Ã…â€™clients operation information tips
	private JLabel hostAddress; // in the clients connection interfaceÃƒÂ¯Ã‚Â¼Ã…â€™IP address tips
	private JLabel chatInfo; // in the clients chat interfaceÃƒÂ¯Ã‚Â¼Ã…â€™chat record tips
	private JLabel chatInput; // in the clients chat interfaceÃƒÂ¯Ã‚Â¼Ã…â€™chat information input tips
	private JLabel clientNumber;
	private JLabel colorInput;
	private JLabel chooseFile;
	private JLabel editInfo;
	private JLabel usernameLabel;
	private JLabel clientNumberLabel;
	
	private JTextField inputFilePath; // the menu item "open file" in menu "file"ÃƒÂ¯Ã‚Â¼Ã…â€™the text field to input file
											// path
	private JTextField inputHostAddress; // in the clients connection interfaceÃƒÂ¯Ã‚Â¼Ã…â€™the text field to input IP address
	private JTextField clientNumberTextField;
	private JTextField usernameTextField;
	private JTextField clientNumberStartTextField;
	private String username;
	private String text = "";
	
	private Socket socket;
	
	private Boolean isServer;
	
	private Border border = BorderFactory.createLineBorder(Color.BLACK);	
	
	private JComboBox chooseType;
	private JPopupMenu pencilPopup;
	private JPopupMenu linePopup;
	private JPopupMenu circlePopup;
	private JPopupMenu ovalPopup;
	private JPopupMenu rectPopup;
	private JPopupMenu eraserPopup;
	private JPopupMenu textPopup;
	
    private JMenu fontSizeMenu;
	private JMenu fontStyleMenu;
	private JMenu fillMenu;
	private JMenu frameSizeMenu;
	private JMenu boldMenu;
	private JMenu italicMenu;
	private JMenu lineStyleMenu;
	
	private JMenuItem lightSizeMenu;
	private JMenuItem thickSizeMenu;
	private JMenuItem thickerSizeMenu;
	private JMenuItem thickestSizeMenu;
	private JMenuItem bigSizeMenu;
	private JMenuItem smallSizeMenu;
	private JMenuItem mediumSizeMenu;
	private JMenuItem fillMenuItem;
	private JMenuItem notFillMenuItem;
	private JMenuItem timeNewRomanMenu;
	private JMenuItem arialMenu;
	private JMenuItem georgiaMenu;
	private JMenuItem lightSizeMenu1;
	private JMenuItem thickSizeMenu1;
	private JMenuItem thickerSizeMenu1;
	private JMenuItem thickestSizeMenu1;
	private JMenuItem boldMenuItem;
	private JMenuItem notBoldMenuItem;
	private JMenuItem italicMenuItem;
	private JMenuItem notItalicMenuItem;
	private JMenuItem lineMenuItem;
	private JMenuItem linkMenuItem;
	private JMenuItem dashedLineMenuItem;
	private JMenuItem directionalConnectorMenuItem;
	
	

	// initialize
	public void initialize() {
		
		// bounds
		frame.setBounds(500, 250, 1100, 600);

		// frame name
		frame.setTitle("Shared White Board");

		// font
		setFont();

		// set home page
		setHomePage();

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		closeFrameTips();
		frame.setVisible(true);
	}

	// set global font
	private void setFont() {
		otherFont = new Font("FZShuTi", Font.BOLD, 13);
		UIManager.put("Label.font", otherFont);
		UIManager.put("Button.font", otherFont);
		UIManager.put("Panel.font", otherFont);
		UIManager.put("TextField.font", otherFont);
		UIManager.put("TextArea.font", otherFont);
		UIManager.put("Menu.font", otherFont);
		UIManager.put("MenuItem.font", otherFont);
		UIManager.put("MenuBar.font", otherFont);
	}

	// home panelÃƒÂ¯Ã‚Â¼Ã…â€™with start button and exit button
	private void setHomePage() {

		// home panel background
		setHomePagePic();

		// home panel font
		homePageButtonFont = new Font("STXinwei", Font.BOLD, 50);

		// home panel button: START,EXIT
		startButton = new JButton("START");
		exitButton = new JButton("EXIT");

		// button font
		startButton.setFont(homePageButtonFont);
		exitButton.setFont(homePageButtonFont);

		startButton.setForeground(Color.white);
		exitButton.setForeground(Color.white);
		startButton.setBounds(750, 200, 300, 100);
		exitButton.setBounds(750, 350, 300, 100);
		startButton.setContentAreaFilled(false);
		exitButton.setContentAreaFilled(false);
		startButton.setBorderPainted(false);
		exitButton.setBorderPainted(false);

		// add
		homePanel.add(startButton);
		homePanel.add(exitButton);

		// Page jump, START: jump to the white board interface, EXIT: warm prompt box
		// appears, EXIT the program
		startButton.addActionListener(new PageListener());
		exitButton.addActionListener(new PageListener());
		homePanel.setVisible(true);
	}

	// home panel background
	private void setHomePagePic() {
		homePanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				ImageIcon background = new ImageIcon(getClass().getResource("/picture/pic.jpg"));
				Image picture = background.getImage();
				g.drawImage(picture, 0, 0, background.getIconWidth(), background.getIconHeight(),
						background.getImageObserver());
				frame.setSize(background.getIconWidth(), background.getIconHeight());

			}
		};
		homePanel.setLayout(null);
		frame.add(homePanel);
		frame.pack();
	}
	
	private void openDrawBoard() {
		homePanel.setVisible(false);
		startPanel = new JPanel();
		startPanel.setBackground(Color.lightGray);
		startPanel.setBounds(0, 0, 1100, 600);
		frame.add(startPanel);
		startPage(startPanel);
		startPanel.setVisible(true);
		System.out.println("100");
		if(isServer) {
			usernameTextField.setText("Server");
			clientNumberStartTextField.setText("None");
		}
		
	}
	// To realize page jump, click START to enter the white board panel, click EXIT
	// to realize the warm prompt box, EXIT the program
	private class PageListener extends JFrame implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.equals("START")) {
				openDrawBoard();
				if(isServer) {
					ListenConnectionThread listenConnectionThread=new ListenConnectionThread(server);
					new Thread(listenConnectionThread).start();
				}else {
					
					RequestConnectionThread requestConnectionThread=new RequestConnectionThread(client);
					new Thread(requestConnectionThread).start();
					
				}
				
				
				
			} else if (command.equals("EXIT")) {
				int tips = JOptionPane.showConfirmDialog(null, "Do you want to exit the Shared White Board?", "Tips",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (tips == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					return;
				}
			}
		}
	}

	// white board panel, with "File" menu: newÃƒÂ¯Ã‚Â¼Ã¢â‚¬ÂºopenÃƒÂ¯Ã‚Â¼Ã¢â‚¬ÂºsaveÃƒÂ¯Ã‚Â¼Ã¢â‚¬Âºsave asÃƒÂ¯Ã‚Â¼Ã¢â‚¬Âºclose "Help"
	// menuÃƒÂ¯Ã‚Â¼Ã…Â¡help;about
	// tool bar:
	private void startPage(JPanel startPanel) {
		startPanel.setLayout(null);
		setFont();

		// initialize
		whiteBoardPanel=new JPanel();
		
		
		System.out.println(whiteBoardPanel.getGraphics());
		whiteBoardPanel.setBackground(Color.white);
		whiteBoardPanel.setBounds(76, 10, 535, 450);
		whiteBoardPanel.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		// initialize
		chatInfo = new JLabel("Chating Information:");
		chatInput = new JLabel("Input:");
		colorInput = new JLabel("Color:");
		editInfo = new JLabel("Editing Information:");
		usernameLabel = new JLabel("Username:");
		clientNumberLabel = new JLabel("Client Number:");
		chatInfoTextArea = new JTextArea();
		chatInputTextField = new JTextField();
		
		editInfoTextArea = new JTextArea();
		usernameTextField= new JTextField();
		clientNumberStartTextField= new JTextField();
		// ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â´Ã‚Â»ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã¯Â¿Â½Ã‚Â¢ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½ ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â´Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¯Â¿Â½ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€�ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½
		chatInfoTextArea.setLineWrap(true);
		chatInfoTextArea.setWrapStyleWord(true);
		
		editInfoTextArea.setLineWrap(true);
		editInfoTextArea.setWrapStyleWord(true);

		sendChatButton = new JButton("SEND");
		chatInfoScrollPane = new JScrollPane(chatInfoTextArea);

		// bound
		chatInfo.setBounds(812, 10, 180, 25);
		chatInput.setBounds(812, 438, 50, 50);
		colorInput.setBounds(10, 10, 60, 25);
		editInfo.setBounds(620, 10, 183, 25);
		usernameLabel.setBounds(620, 365,80, 50);
		clientNumberLabel.setBounds(620, 415, 100, 50);
		editInfoTextArea.setBounds(620, 30, 184, 329);
		chatInfoTextArea.setBounds(812, 30, 262, 400);
		chatInputTextField.setBounds(865, 443, 208, 40);
		chatInfoScrollPane.setBounds(812, 30, 262, 400);
		usernameTextField.setBounds(700, 370,104, 40);
		clientNumberStartTextField.setBounds(725, 420, 78, 40);
		editInfoTextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		chatInfoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sendChatButton.setBounds(905, 494, 80, 25);
		chatInfoTextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		chatInputTextField
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		usernameTextField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		clientNumberStartTextField.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		// add
		

		// send message action listener
		sendChatButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
				text = chatInputTextField.getText();
				
				if (text.equals("")) {
					JOptionPane.showMessageDialog(null, "Input failed, please input content!", "Error",
							JOptionPane.ERROR_MESSAGE); // ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¨ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¿ÃƒÆ’Ã¢â‚¬Â¦Ãƒâ€šÃ‚Â¾ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¦ÃƒÆ’Ã¢â‚¬Â¦Ãƒâ€šÃ‚Â½ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©ÃƒÆ’Ã‚Â¢ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬Ãƒâ€šÃ‚Â¡ÃƒÆ’Ã¢â‚¬Â¦ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¯ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¼ÃƒÆ’Ã¢â‚¬Â¦ÃƒÂ¢Ã¢â€šÂ¬Ã¢â€žÂ¢ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¨ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¿ÃƒÆ’Ã¢â‚¬Â¦Ãƒâ€šÃ‚Â¾ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¦ÃƒÆ’Ã¢â‚¬Â¦Ãƒâ€šÃ‚Â½ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¥ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¤ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â±ÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â¨ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â´ÃƒÆ’Ã¢â‚¬Å¡Ãƒâ€šÃ‚Â¥
				} else {
					if(isServer) {
						server.sendText(text);
					}else {
						client.sendText(text);
					}
				}
			}  
	   });
		
		
        dm = new DrawMethod(whiteBoardPanel,this);
        whiteBoardPanel.addMouseListener(dm);
        whiteBoardPanel.addMouseMotionListener(dm);
        
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		fileMenu = new JMenu("File(F)");
		helpMenu = new JMenu("Help(H)");
		undoAndRedoMenu = new JMenu("Undo&Redo(U&R)");
		undoMenu = new JMenuItem("Undo(U)");
		redoMenu = new JMenuItem("Redo(R)");
		newMenuItem = new JMenuItem("New(N)");
		undoAndRedoMenu.add(undoMenu);
		undoAndRedoMenu.addSeparator();
		undoAndRedoMenu.add(redoMenu);
		//new function
		newMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				newFunction();
			}
		});
		openMenuItem = new JMenuItem("Open(O)");
		saveMenuItem = new JMenuItem("Save(S)");
		saveAsMenuItem = new JMenuItem("Save As(A)");
		closeMenuItem = new JMenuItem("Close(C)");
		helpMenuItem = new JMenuItem("Help(H)");
		aboutMenuItem = new JMenuItem("About(F)");
		
		
		

		// "open" menu item action listener
		openMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openFunction();
			}
		});

		// "save" menu item action listener
		saveMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFunction();

			}
		});

		// "save as" menu item action listener
		saveAsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveAsFunction();

			}
		});

		// "close" menu item action listener
		closeMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFunction();
			}
		});

		// "help" menu item action listener
		helpMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				helpFrame = new JFrame();
				helpFrame.setBounds(750, 450, 400, 300);
				helpFrame.setTitle("Help");
				setFont();
				setHelpPage();
				helpFrame.setDefaultCloseOperation(helpFrame.HIDE_ON_CLOSE);
				helpFrame.setVisible(true);
			}
		});

		// "about" menu item action listener
		aboutMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutFrame = new JFrame();
				aboutFrame.setBounds(750, 450, 400, 300);
				aboutFrame.setTitle("About");
				setFont();
				setAboutPage();
				aboutFrame.setDefaultCloseOperation(aboutFrame.HIDE_ON_CLOSE);
				aboutFrame.setVisible(true);
			}
		});

		// add
		menuBar.add(fileMenu);
		
		menuBar.add(undoAndRedoMenu);
		menuBar.add(helpMenu);
		
		undoMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(isServer) {
					dm.undo();
					server.sendNewFile();
				}
				else
					client.sendUndo();
			}
		});
		redoMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(isServer) {
					dm.redo();
					server.sendNewFile();
				}
					
				else
					client.sendRedo();
			}
		});
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveAsMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(closeMenuItem);
		helpMenu.add(helpMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutMenuItem);

		// add tool bar
		toolBar = new JToolBar();
		toolBar.setBounds(75, 470, 690, 50);
		toolBar.setBackground(Color.white);
		toolBar.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		toolBar.setFloatable(false);
		// add button on tool bar
		addButton();

		// the description of tool function
		tipsTextArea = new JTextArea(15, 30);
		tipsTextArea.setEditable(false);
		tipsScrollPane = new JScrollPane(tipsTextArea);

		colorButton1 = new JButton();
		colorButton1.setBounds(8, 30, 30, 30);
		colorButton1.setBackground(new Color(255,179,167));
		colorButton1.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton1.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton1);
		//colorButton1.addActionListener(dl);
		colorButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(255,179,167);
			}
		});
		
		colorButton3 = new JButton();
		colorButton3.setBounds(8, 65, 30, 30);
		colorButton3.setBackground(new Color(237,87,54));
		colorButton3.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton3.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton3);
		//colorButton3.addActionListener(dl);
		colorButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(237,87,54);
			}
		});
		
		colorButton5 = new JButton();
		colorButton5.setBounds(8, 100, 30, 30);
		colorButton5.setBackground(new Color(219,90,107));
		colorButton5.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton5.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton5);
		//colorButton5.addActionListener(dl);
		colorButton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(219,90,107);
			}
		});
		
		colorButton7 = new JButton();
		colorButton7.setBounds(8, 135, 30, 30);
		colorButton7.setBackground(new Color(242,12,0));
		colorButton7.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton7.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton7);
		//colorButton7.addActionListener(dl);
		colorButton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(242,12,0);
			}
		});
		
		colorButton9 = new JButton();
		colorButton9.setBounds(8, 170, 30, 30);
		colorButton9.setBackground(new Color(157,41,51));
		colorButton9.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton9.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton9);
		//colorButton9.addActionListener(dl);
		colorButton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(157,41,51);
			}
		});
		
		colorButton11 = new JButton();
		colorButton11.setBounds(8, 205, 30, 30);
		colorButton11.setBackground(new Color(179,109,97));
		colorButton11.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton11.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton11);
		//colorButton11.addActionListener(dl);
		colorButton11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(179,109,97);
			}
		});
		
		colorButton13 = new JButton();
		colorButton13.setBounds(8, 240, 30, 30);
		colorButton13.setBackground(new Color(250,255,114));
		colorButton13.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton13.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton13);
		//colorButton13.addActionListener(dl);
		colorButton13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(250,255,114);
			}
		});
		
		colorButton15 = new JButton();
		colorButton15.setBounds(8, 275, 30, 30);
		colorButton15.setBackground(new Color(255,137,54));
		colorButton15.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton15.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton15);
		//colorButton15.addActionListener(dl);
		colorButton15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(255,137,54);
			}
		});
		
		colorButton17 = new JButton();
		colorButton17.setBounds(8, 310, 30, 30);
		colorButton17.setBackground(new Color(255,199,115));
		colorButton17.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton17.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton17);
		//colorButton17.addActionListener(dl);
		colorButton17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(255,199,115);
			}
		});
		
		colorButton19 = new JButton();
		colorButton19.setBounds(8, 345, 30, 30);
		colorButton19.setBackground(new Color(179,92,68));
		colorButton19.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton19.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton19);
		//colorButton19.addActionListener(dl);
		colorButton19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(179,92,68);
			}
		});
		
		colorButton21 = new JButton();
		colorButton21.setBounds(8, 380, 30, 30);
		colorButton21.setBackground(new Color(96,40,30));
		colorButton21.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton21.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton21);
		//colorButton21.addActionListener(dl);
		colorButton21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(96,40,30);
			}
		});
		
		colorButton23 = new JButton();
		colorButton23.setBounds(8, 415, 30, 30);
		colorButton23.setBackground(new Color(12,137,24));
		colorButton23.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton23.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton23);
		//colorButton23.addActionListener(dl);
		colorButton23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(12,137,24);
			}
		});
		
		colorButton2 = new JButton();
		colorButton2.setBounds(40, 30, 30, 30);
		colorButton2.setBackground(new Color(27,209,165));
		colorButton2.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton2.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton2);
		//colorButton2.addActionListener(dl);
		colorButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(27,209,165);
			}
		});

		colorButton4 = new JButton();
		colorButton4.setBounds(40, 65, 30, 30);
		colorButton4.setBackground(new Color(68,206,246));
		colorButton4.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton4.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton4);
		//colorButton4.addActionListener(dl);
		colorButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(68,206,246);
			}
		});
		
		colorButton6 = new JButton();
		colorButton6.setBounds(40, 100, 30, 30);
		colorButton6.setBackground(new Color(6,82,121));
		colorButton6.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton6.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton6);
		//colorButton6.addActionListener(dl);
		colorButton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(6,82,121);
			}
		});
		
		colorButton8 = new JButton();
		colorButton8.setBounds(40, 135, 30, 30);
		colorButton8.setBackground(new Color(75,92,196));
		colorButton8.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton8.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton8);
		//colorButton8.addActionListener(dl);
		colorButton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(75,92,196);
			}
		});
		
		colorButton10 = new JButton();
		colorButton10.setBounds(40, 170, 30, 30);
		colorButton10.setBackground(new Color(141,75,187));
		colorButton10.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton10.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton10);
		//colorButton10.addActionListener(dl);
		colorButton10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(141,75,187);
			}
		});
		
		colorButton12 = new JButton();
		colorButton12.setBounds(40, 205, 30, 30);
		colorButton12.setBackground(new Color(176,164,227));
		colorButton12.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton12.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton12);
		//colorButton12.addActionListener(dl);
		colorButton12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(176,164,227);
			}
		});
		
		colorButton14 = new JButton();
		colorButton14.setBounds(40, 240, 30, 30);
		colorButton14.setBackground(new Color(48,223,243));
		colorButton14.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton14.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton14);
		//colorButton14.addActionListener(dl);
		colorButton14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(48,223,243);
			}
		});
		
		colorButton16 = new JButton();
		colorButton16.setBounds(40, 275, 30, 30);
		colorButton16.setBackground(new Color(255,255,255));
		colorButton16.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton16.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton16);
		//colorButton16.addActionListener(dl);
		colorButton16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(255,255,255);
			}
		});
		
		colorButton18 = new JButton();
		colorButton18.setBounds(40, 310, 30, 30);
		colorButton18.setBackground(new Color(22,24,35));
		colorButton18.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton18.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton18);
		//colorButton18.addActionListener(dl);
		colorButton18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(22,24,35);
			}
		});
		
		colorButton20 = new JButton();
		colorButton20.setBounds(40, 345, 30, 30);
		colorButton20.setBackground(new Color(37,248,203));
		colorButton20.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton20.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton20);
		//colorButton20.addActionListener(dl);
		colorButton20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(37,248,203);
			}
		});
		
		colorButton22 = new JButton();
		colorButton22.setBounds(40, 380, 30, 30);
		colorButton22.setBackground(new Color(155,68,0));
		colorButton22.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton22.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton22);
		//colorButton22.addActionListener(dl);
		colorButton22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(155,68,0);
			}
		});
		
		colorButton24 = new JButton();
		colorButton24.setBounds(40, 415, 30, 30);
		colorButton24.setBackground(new Color(200,60,35));
		colorButton24.setOpaque(true); //foregroundè®¾ç½®é€�æ˜Ž
		colorButton24.setBorderPainted(false); //æœ€å�Žæ˜¾ç¤ºçº¢è‰²
		startPanel.add(colorButton24);
		//colorButton24.addActionListener(dl);
		colorButton24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								     
                dm.color = new Color(200,60,35);
			}
		});
		startPanel.add(usernameLabel);
		startPanel.add(clientNumberLabel);
		
		startPanel.add(usernameTextField);
		startPanel.add(clientNumberStartTextField);
		startPanel.add(toolBar);
		startPanel.add(tipsScrollPane, BorderLayout.CENTER);
		startPanel.add(whiteBoardPanel);
		startPanel.add(chatInfo);
		startPanel.add(chatInput);
		startPanel.add(colorInput);
		startPanel.add(chatInfoScrollPane);
		startPanel.add(chatInputTextField);
		startPanel.add(sendChatButton);
		startPanel.add(editInfo);
		startPanel.add(editInfoTextArea);
	}

	// open file
	private class OpenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("F:\\");
			int val = fileChooser.showOpenDialog(null); // ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¦Ã¢â‚¬Â°Ã¢â‚¬Å“ÃƒÂ¥Ã‚Â¼Ã¢â€šÂ¬ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â¯Ã¯Â¿Â½ÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â 
			if (val == fileChooser.APPROVE_OPTION) {
				// ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
				inputFilePath.setText(fileChooser.getSelectedFile().toString());
			} else {
				// ÃƒÂ¦Ã…â€œÃ‚ÂªÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬â„¢Ã‚Â®
				inputFilePath.setText("no choose file");
			}
		}
	}

	// save file
	private class SaveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser("F:\\");
			int val = fileChooser.showSaveDialog(null); // ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¤Ã‚Â¿Ã¯Â¿Â½ÃƒÂ¥Ã‚Â­Ã‹Å“ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â¯Ã¯Â¿Â½ÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â 
			if (val == fileChooser.APPROVE_OPTION) {
				// ÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶
				inputFilePath.setText(fileChooser.getSelectedFile().toString());
			} else if (val == JFileChooser.CANCEL_OPTION) {
				// ÃƒÂ¦Ã…â€œÃ‚ÂªÃƒÂ¦Ã‚Â­Ã‚Â£ÃƒÂ¥Ã‚Â¸Ã‚Â¸ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¦Ã¢â‚¬â€œÃ¢â‚¬Â¡ÃƒÂ¤Ã‚Â»Ã‚Â¶ÃƒÂ¯Ã‚Â¼Ã…â€™ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¦Ã¢â‚¬Â¹Ã‚Â©ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã…â€™Ã¢â‚¬Â°ÃƒÂ©Ã¢â‚¬â„¢Ã‚Â®
				inputFilePath.setText("no choose file");
			}
		}
	}

	// help panel
	private void setHelpPage() {
		helpPanel = new JPanel();
		helpPanel.setLayout(null);
		helpFrame.add(helpPanel);
		helpPanel.setBackground(Color.white);

		// initialize
		help = new JLabel("Help:");
		helpTextArea = new JTextArea(8, 31);

		// bound
		help.setBounds(10, 10, 90, 20);
		helpTextArea.setBounds(10, 40, 360, 200);
		helpTextArea.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(3, 5, 5, 3)));

		// ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â´Ã‚Â»ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã¯Â¿Â½Ã‚Â¢ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½
		helpTextArea.setLineWrap(true);

		// ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â´Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¯Â¿Â½ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€�ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½
		helpTextArea.setWrapStyleWord(true);

		// content
		helpTextArea.setText("This project is a shared whiteboard." + "\n" + "Features:" + "\n"
				+ "1. This project enables multiple people to connect to the whiteboard and share the whiteboard content."
				+ "\n" + "2. This project only has a server to manage customers, clear customers, and operate on files."
				+ "\n"
				+ "3. After connecting the whiteboard, the customer can realize painting, draw basic shapes (straight line, circle, rectangle, oval), input text, freely select color, erase, chat, etc.");

		// add
		helpPanel.add(help);
		helpPanel.add(helpTextArea);
		helpPanel.setVisible(true);
	}

	// about panel
	private void setAboutPage() {
		aboutPanel = new JPanel();
		aboutPanel.setLayout(null);
		aboutFrame.add(aboutPanel);
		aboutPanel.setBackground(Color.white);

		// initialize
		about = new JLabel("About:");
		aboutTextArea = new JTextArea(8, 31);

		// bound
		about.setBounds(10, 10, 90, 20);
		aboutTextArea.setBounds(10, 40, 360, 200);
		aboutTextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(3, 5, 5, 3)));

		// ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â´Ã‚Â»ÃƒÂ¨Ã¢â‚¬Â¡Ã‚ÂªÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¦Ã¯Â¿Â½Ã‚Â¢ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½
		aboutTextArea.setLineWrap(true);

		// ÃƒÂ¦Ã‚Â¿Ã¢â€šÂ¬ÃƒÂ¦Ã‚Â´Ã‚Â»ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¨Ã‚Â¡Ã…â€™ÃƒÂ¤Ã‚Â¸Ã¯Â¿Â½ÃƒÂ¦Ã¢â‚¬â€œÃ‚Â­ÃƒÂ¥Ã‚Â­Ã¢â‚¬â€�ÃƒÂ¥Ã…Â Ã…Â¸ÃƒÂ¨Ã†â€™Ã‚Â½
		aboutTextArea.setWrapStyleWord(true);

		// content
		aboutTextArea.setText("distributed system : assignment2" + "\n" + "Huan Cao : GUI frame" + "\n"
				+ "Dongfang Wang : draw function" + "\n" + "Qingfeng Xu : other function");

		// add
		aboutPanel.add(about);
		aboutPanel.add(aboutTextArea);
		aboutPanel.setVisible(true);
	}

	// add button on tool bar:
	// manageButton;connectButton;newDrawingButton;pencilButton;lineButton;circleButton;rectangleButton;ovalButton;eraserButton;textButton;colorButton;chatWindowButton
	private void addButton() {

		// The manager manages clients function
		URL manageURL=WhiteBoardGUI.class.getResource("/picture/manage.jpg");
		Icon manageIcon=new ImageIcon(manageURL);
		manageButton = new JButton(manageIcon);
		manageButton.setOpaque(false);  
		manageButton.setContentAreaFilled(false); 
		manageButton.setFocusPainted(false);
		manageButton.setBorder(null); 
		manageButton.setToolTipText("Manage clients");
		manageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manageFrame = new JFrame();
				manageFrame.setLocation(100, 450);
				manageFrame.setSize(600, 500);
				manageFrame.setTitle("Server");
				setFont();
				setManagePage();
				manageFrame.setDefaultCloseOperation(manageFrame.HIDE_ON_CLOSE);
				manageFrame.setVisible(true);
			}
		});
		toolBar.add(manageButton);
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();
		toolBar.addSeparator();

		// The clients connect server function
		
		toolBar.addSeparator();

		// The add pages function
		URL newPageURL=WhiteBoardGUI.class.getResource("/picture/newpage.jpg");
		Icon newPageIcon=new ImageIcon(newPageURL);
		newDrawingButton = new JButton(newPageIcon);
		newDrawingButton.setOpaque(false);  
		newDrawingButton.setContentAreaFilled(false); 
		newDrawingButton.setFocusPainted(false);
		newDrawingButton.setBorder(null); 
		newDrawingButton.setToolTipText("Create a new page");
		newDrawingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tips = JOptionPane.showConfirmDialog(null, "Do you want to create a new page? (the old page will be deleted!)", "Tips",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (tips == JOptionPane.YES_OPTION) {
					//System.out.println("ÃƒÂ¥Ã‚Â¢Ã…Â¾ÃƒÂ¥Ã…Â Ã‚Â ÃƒÂ©Ã‚Â¡Ã‚ÂµÃƒÂ©Ã¯Â¿Â½Ã‚Â¢ÃƒÂ¥Ã…Â Ã‚Â¨ÃƒÂ¤Ã‚Â½Ã…â€œ");
					if(isServer) {
						server.sendNewPage();
					}
					else {
						client.sendNewPage();
					}
					whiteBoardPanel.repaint();
					dm.cleanShape();
					for(int i=0;i<dm.cleanText.size();i++) {
						JTextArea clean = dm.cleanText.get(i);
						clean.setVisible(false);
					}
					dm.cleanText = new ArrayList<JTextArea>();
				} else {
					//System.out.println("ÃƒÂ¤Ã‚Â»Ã¢â€šÂ¬ÃƒÂ¤Ã‚Â¹Ã‹â€ ÃƒÂ¤Ã‚Â¹Ã…Â¸ÃƒÂ¦Ã‚Â²Ã‚Â¡ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬ËœÃƒÂ§Ã¢â‚¬ï¿½Ã…Â¸");
					return;
				}
			}
		});
		toolBar.add(newDrawingButton);
	
// choose shape
	    URL chooseShapeURL=WhiteBoardGUI.class.getResource("/picture/width.jpg");
		Icon chooseShapeIcon=new ImageIcon(chooseShapeURL);
		chooseShapeButton = new JButton(chooseShapeIcon);
		
		chooseShapeButton.setOpaque(false);  
		chooseShapeButton.setContentAreaFilled(false); 
		chooseShapeButton.setFocusPainted(false);
		chooseShapeButton.setBorder(null); 
		chooseShapeButton.setToolTipText("Choose the shape");
		
		chooseShapeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    System.out.println("选择图形操作");
				dm.shape = "chooseshape";
				
			}
		});
		toolBar.add(chooseShapeButton);
		toolBar.addSeparator();
		
		// The pencil function
		URL pencilURL=WhiteBoardGUI.class.getResource("/picture/pencil.jpg");
		Icon pencilIcon=new ImageIcon(pencilURL);
		pencilButton = new JButton(pencilIcon);
		pencilButton.setOpaque(false);  
		pencilButton.setContentAreaFilled(false); 
		pencilButton.setFocusPainted(false);
		pencilButton.setBorder(null); 
		pencilButton.setToolTipText("Draw with pencil");
		pencilPopup = new JPopupMenu();
		fontSizeMenu=new JMenu("SIZE");
		lightSizeMenu=new JMenuItem("LIGHT");
		lightSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "freehandDrawing";
            	dm.pencilWidth = 1;
                JOptionPane.showMessageDialog(frame, "light selected");
            }
        });
		
		thickSizeMenu=new JMenuItem("THICK");
		thickSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "freehandDrawing";
            	dm.pencilWidth = 5;
                JOptionPane.showMessageDialog(frame, "thick selected");
            }
        });
		thickerSizeMenu=new JMenuItem("THICKER");
		thickerSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "freehandDrawing";
            	dm.pencilWidth = 9;
                JOptionPane.showMessageDialog(frame, "thicker selected");
            }
        });
		thickestSizeMenu=new JMenuItem("THICKEST");
		thickestSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "freehandDrawing";
            	dm.pencilWidth = 15;
                JOptionPane.showMessageDialog(frame, "thickest selected");
            }
        });
		
		fontSizeMenu.add(lightSizeMenu);
		fontSizeMenu.add(thickSizeMenu);
		fontSizeMenu.add(thickerSizeMenu);
		fontSizeMenu.add(thickestSizeMenu);
	
		pencilPopup.add(fontSizeMenu);
	
        pencilButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "freehandDrawing";
        		} else if (e.getButton() == e.BUTTON3) {
        			pencilPopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(pencilButton);
		toolBar.addSeparator();

		// The line function
		URL lineURL=WhiteBoardGUI.class.getResource("/picture/line.jpg");
		Icon lineIcon=new ImageIcon(lineURL);
		lineButton = new JButton(lineIcon);
		lineButton.setOpaque(false);  
		lineButton.setContentAreaFilled(false); 
		lineButton.setFocusPainted(false);
		lineButton.setBorder(null); 
		lineButton.setToolTipText("Draw a line");
		linePopup = new JPopupMenu();
        lineStyleMenu=new JMenu("LINE STYLE");
		lineMenuItem=new JMenuItem("LINE");
		lineMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "line";
            	dm.width = 1;
                JOptionPane.showMessageDialog(frame, "light selected");
            }
        });
		
		linkMenuItem=new JMenuItem("LINK");
		linkMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "link";
                JOptionPane.showMessageDialog(frame, "thick selected");
            }
        });
		dashedLineMenuItem=new JMenuItem("DASHED LINE");
		dashedLineMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "dashedLine";
                JOptionPane.showMessageDialog(frame, "thicker selected");
            }
        });
		directionalConnectorMenuItem=new JMenuItem("DIRECTIONAL CONNECTOR");
		directionalConnectorMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            dm.shape = "directionalConnector";
            	
                JOptionPane.showMessageDialog(frame, "thickest selected");
            }
        });
		
		lineStyleMenu.add(lineMenuItem);
		lineStyleMenu.add(linkMenuItem);
		lineStyleMenu.add(dashedLineMenuItem);
		lineStyleMenu.add(directionalConnectorMenuItem);
		linePopup.add(lineStyleMenu);
		
		fontSizeMenu=new JMenu("SIZE");
		lightSizeMenu=new JMenuItem("LIGHT");
		lightSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "line";
            	dm.width = 1;
                JOptionPane.showMessageDialog(frame, "light selected");
            }
        });
		
		thickSizeMenu=new JMenuItem("THICK");
		thickSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "line";
            	dm.width = 5;
                JOptionPane.showMessageDialog(frame, "thick selected");
            }
        });
		thickerSizeMenu=new JMenuItem("THICKER");
		thickerSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "line";
            	dm.width = 9;
                JOptionPane.showMessageDialog(frame, "thicker selected");
            }
        });
		thickestSizeMenu=new JMenuItem("THICKEST");
		thickestSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "line";
            	dm.width = 15;
                JOptionPane.showMessageDialog(frame, "thickest selected");
            }
        });
		
		fontSizeMenu.add(lightSizeMenu);
		fontSizeMenu.add(thickSizeMenu);
		fontSizeMenu.add(thickerSizeMenu);
		fontSizeMenu.add(thickestSizeMenu);
	
		linePopup.add(fontSizeMenu);
	
		lineButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "line";
        		} else if (e.getButton() == e.BUTTON3) {
        			linePopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(lineButton);
		toolBar.addSeparator();

		// The circle function
		URL circleURL=WhiteBoardGUI.class.getResource("/picture/circle.png");
		Icon circleIcon=new ImageIcon(circleURL);
		circleButton = new JButton(circleIcon);
		circleButton.setOpaque(false);  
		circleButton.setContentAreaFilled(false); 
		circleButton.setFocusPainted(false);
		circleButton.setBorder(null); 
		circleButton.setToolTipText("Draw a circle");
		circlePopup = new JPopupMenu();
		fontSizeMenu=new JMenu("SIZE");
		fillMenu=new JMenu("FILL");
		notFillMenuItem=new JMenuItem("EMPTY");
		notFillMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "circle";
            	dm.fillFlag = false;
                JOptionPane.showMessageDialog(frame, "empty selected");
            }
        });
		
		fillMenuItem=new JMenuItem("FILL");
		fillMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "circle";
            	dm.fillFlag = true;
                JOptionPane.showMessageDialog(frame, "fill selected");
            }
        });
		lightSizeMenu=new JMenuItem("LIGHT");
		lightSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "circle";
            	dm.width = 1;
                JOptionPane.showMessageDialog(frame, "light selected");
            }
        });
		
		thickSizeMenu=new JMenuItem("THICK");
		thickSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "circle";
            	dm.width = 5;
                JOptionPane.showMessageDialog(frame, "thick selected");
            }
        });
		thickerSizeMenu=new JMenuItem("THICKER");
		thickerSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "circle";
            	dm.width = 9;
                JOptionPane.showMessageDialog(frame, "thicker selected");
            }
        });
		thickestSizeMenu=new JMenuItem("THICKEST");
		thickestSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "circle";
            	dm.width = 15;
                JOptionPane.showMessageDialog(frame, "thickest selected");
            }
        });
		
		fontSizeMenu.add(lightSizeMenu);
		fontSizeMenu.add(thickSizeMenu);
		fontSizeMenu.add(thickerSizeMenu);
		fontSizeMenu.add(thickestSizeMenu);
	    fillMenu.add(notFillMenuItem);
		fillMenu.add(fillMenuItem);
		circlePopup.add(fontSizeMenu);
		circlePopup.add(fillMenu);
	
		circleButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "circle";
        		} else if (e.getButton() == e.BUTTON3) {
        			circlePopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(circleButton);
		toolBar.addSeparator();

		// The rectangle function
		URL rectangleURL=WhiteBoardGUI.class.getResource("/picture/reg.jpg");
		Icon rectangleIcon=new ImageIcon(rectangleURL);
		rectangleButton = new JButton(rectangleIcon);
		rectangleButton.setOpaque(false);  
		rectangleButton.setContentAreaFilled(false); 
		rectangleButton.setFocusPainted(false);
		rectangleButton.setBorder(null); 
		rectangleButton.setToolTipText("Draw a rectangle");
		rectPopup = new JPopupMenu();
		fontSizeMenu=new JMenu("SIZE");
		fillMenu=new JMenu("FILL");
		notFillMenuItem=new JMenuItem("EMPTY");
		notFillMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "rectangle";
            	dm.fillFlag = false;
                JOptionPane.showMessageDialog(frame, "empty selected");
            }
        });
		
		fillMenuItem=new JMenuItem("FILL");
		fillMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "rectangle";
            	dm.fillFlag = true;
                JOptionPane.showMessageDialog(frame, "fill selected");
            }
        });
		lightSizeMenu=new JMenuItem("LIGHT");
		lightSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "rectangle";
            	dm.width = 1;
                JOptionPane.showMessageDialog(frame, "light selected");
            }
        });
		
		thickSizeMenu=new JMenuItem("THICK");
		thickSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "rectangle";
            	dm.width = 5;
                JOptionPane.showMessageDialog(frame, "thick selected");
            }
        });
		thickerSizeMenu=new JMenuItem("THICKER");
		thickerSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "rectangle";
            	dm.width = 9;
                JOptionPane.showMessageDialog(frame, "thicker selected");
            }
        });
		thickestSizeMenu=new JMenuItem("THICKEST");
		thickestSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "rectangle";
            	dm.width = 15;
                JOptionPane.showMessageDialog(frame, "thickest selected");
            }
        });
		
		fontSizeMenu.add(lightSizeMenu);
		fontSizeMenu.add(thickSizeMenu);
		fontSizeMenu.add(thickerSizeMenu);
		fontSizeMenu.add(thickestSizeMenu);
	
	    fillMenu.add(notFillMenuItem);
		fillMenu.add(fillMenuItem);
		rectPopup.add(fontSizeMenu);
		rectPopup.add(fillMenu);
	
		rectangleButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "rectangle";
        		} else if (e.getButton() == e.BUTTON3) {
        			rectPopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(rectangleButton);
		toolBar.addSeparator();

		// The oval function
		URL ovalURL=WhiteBoardGUI.class.getResource("/picture/oval.jpg");
		Icon ovalIcon=new ImageIcon(ovalURL);
		ovalButton = new JButton(ovalIcon);
		ovalButton.setOpaque(false);  
		ovalButton.setContentAreaFilled(false); 
		ovalButton.setFocusPainted(false);
		ovalButton.setBorder(null); 
		ovalButton.setToolTipText("Draw a oval");
		ovalPopup = new JPopupMenu();
		fontSizeMenu=new JMenu("SIZE");
		fillMenu=new JMenu("FILL");
		notFillMenuItem=new JMenuItem("EMPTY");
		notFillMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "oval";
            	dm.fillFlag = false;
                JOptionPane.showMessageDialog(frame, "empty selected");
            }
        });
		
		fillMenuItem=new JMenuItem("FILL");
		fillMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "oval";
            	dm.fillFlag = true;
                JOptionPane.showMessageDialog(frame, "fill selected");
            }
        });
		lightSizeMenu=new JMenuItem("LIGHT");
		lightSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "oval";
            	dm.width = 1;
                JOptionPane.showMessageDialog(frame, "light selected");
            }
        });
		
		thickSizeMenu=new JMenuItem("THICK");
		thickSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "oval";
            	dm.width = 5;
                JOptionPane.showMessageDialog(frame, "thick selected");
            }
        });
		thickerSizeMenu=new JMenuItem("THICKER");
		thickerSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "oval";
            	dm.width = 9;
                JOptionPane.showMessageDialog(frame, "thicker selected");
            }
        });
		thickestSizeMenu=new JMenuItem("THICKEST");
		thickestSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "oval";
            	dm.width = 15;
                JOptionPane.showMessageDialog(frame, "thickest selected");
            }
        });
		
		fontSizeMenu.add(lightSizeMenu);
		fontSizeMenu.add(thickSizeMenu);
		fontSizeMenu.add(thickerSizeMenu);
		fontSizeMenu.add(thickestSizeMenu);
	
		fillMenu.add(notFillMenuItem);
		fillMenu.add(fillMenuItem);
		ovalPopup.add(fontSizeMenu);
		ovalPopup.add(fillMenu);
	
		ovalButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "oval";
        		} else if (e.getButton() == e.BUTTON3) {
        			ovalPopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(ovalButton);
		toolBar.addSeparator();

		// The eraser function
		URL eraserURL=WhiteBoardGUI.class.getResource("/picture/eraser.jpg");
		Icon eraserIcon=new ImageIcon(eraserURL);
		eraserButton = new JButton(eraserIcon);
		eraserButton.setOpaque(false);  
		eraserButton.setContentAreaFilled(false); 
		eraserButton.setFocusPainted(false);
		eraserButton.setBorder(null); 
		eraserButton.setToolTipText("Erase the drawing");
		eraserPopup = new JPopupMenu();
		bigSizeMenu=new JMenuItem("BIG");
		bigSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "erasing";
            	dm.erasingWidth = 80;
                JOptionPane.showMessageDialog(frame, "BIG selected");
            }
        });
        
        mediumSizeMenu=new JMenuItem("MEDIUM");
        mediumSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "erasing";
            	dm.erasingWidth = 40;
            	JOptionPane.showMessageDialog(frame, "MEDIUM selected");
            }
        });
        
        smallSizeMenu=new JMenuItem("SMALL");
        smallSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "erasing";
            	dm.erasingWidth = 20;
            	JOptionPane.showMessageDialog(frame, "SMALL selected");
            }
        });
        
		eraserPopup.add(bigSizeMenu);
		eraserPopup.add(mediumSizeMenu);
		eraserPopup.add(smallSizeMenu);
	
		eraserButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "erasing";
        		} else if (e.getButton() == e.BUTTON3) {
        			eraserPopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(eraserButton);
		toolBar.addSeparator();
		
		// The text function
		URL textURL=WhiteBoardGUI.class.getResource("/picture/text.jpg");
		Icon textIcon=new ImageIcon(textURL);
		textButton = new JButton(textIcon);
		textButton.setOpaque(false);  
		textButton.setContentAreaFilled(false); 
		textButton.setFocusPainted(false);
		textButton.setBorder(null); 
		textButton.setToolTipText("Write the text");
		textPopup = new JPopupMenu();
		fontSizeMenu=new JMenu("FONT SIZE");
		lightSizeMenu=new JMenuItem("LIGHT");
		lightSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.fontWidth =12;
                JOptionPane.showMessageDialog(frame, "3 selected");
            }
        });
		
		thickSizeMenu=new JMenuItem("THICK");
		thickSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.fontWidth =18;
                JOptionPane.showMessageDialog(frame, "6 selected");
            }
        });
		thickerSizeMenu=new JMenuItem("THICKER");
		thickerSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.fontWidth =24;
                JOptionPane.showMessageDialog(frame, "9 selected");
            }
        });
		thickestSizeMenu=new JMenuItem("THICKEST");
		thickestSizeMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.fontWidth =30;
                JOptionPane.showMessageDialog(frame, "15 selected");
            }
        });
		
		fontStyleMenu=new JMenu("STYLE");
		timeNewRomanMenu=new JMenuItem("Time New Roman");
		timeNewRomanMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.style = "TimesRoman";
                JOptionPane.showMessageDialog(frame, "3 selected");
            }
        });
		
		arialMenu=new JMenuItem("Calibri");
		arialMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.style = "Calibri";
                JOptionPane.showMessageDialog(frame, "selected");
            }
        });
		georgiaMenu=new JMenuItem("Chalkboard");
		georgiaMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.style = "Chalkboard";
            	
                JOptionPane.showMessageDialog(frame, "selected");
            }
        });
        
        frameSizeMenu=new JMenu("FRAME SIZE");
		lightSizeMenu1=new JMenuItem("LIGHT");
		lightSizeMenu1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.frameWidth = 1;
                JOptionPane.showMessageDialog(frame, "3 selected");
            }
        });
		
		thickSizeMenu1=new JMenuItem("THICK");
		thickSizeMenu1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.frameWidth = 2;
                JOptionPane.showMessageDialog(frame, "6 selected");
            }
        });
		thickerSizeMenu1=new JMenuItem("THICKER");
		thickerSizeMenu1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.frameWidth = 3;
                JOptionPane.showMessageDialog(frame, "9 selected");
            }
        });
		thickestSizeMenu1=new JMenuItem("THICKEST");
		thickestSizeMenu1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
            	dm.frameWidth = 4;
                JOptionPane.showMessageDialog(frame, "15 selected");
            }
        });
        
        boldMenu=new JMenu("BOLD");
        notBoldMenuItem=new JMenuItem("NORMAL");
		notBoldMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
                JOptionPane.showMessageDialog(frame, "6 selected");
            }
        });
		boldMenuItem=new JMenuItem("BOLD");
		boldMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
                JOptionPane.showMessageDialog(frame, "3 selected");
            }
        });
		
	
        italicMenu=new JMenu("ITALIC");
        notItalicMenuItem=new JMenuItem("NORMAL");
		notItalicMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
                JOptionPane.showMessageDialog(frame, "6 selected");
            }
        });
		italicMenuItem=new JMenuItem("ITALIC");
		italicMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            	dm.shape = "text";
                JOptionPane.showMessageDialog(frame, "3 selected");
            }
        });
    
        frameSizeMenu.add(lightSizeMenu1);
		frameSizeMenu.add(thickSizeMenu1);
		frameSizeMenu.add(thickerSizeMenu1);
		frameSizeMenu.add(thickestSizeMenu1);
		
		fontSizeMenu.add(lightSizeMenu);
		fontSizeMenu.add(thickSizeMenu);
		fontSizeMenu.add(thickerSizeMenu);
		fontSizeMenu.add(thickestSizeMenu);
		fontStyleMenu.add(timeNewRomanMenu);
		fontStyleMenu.add(arialMenu);
		fontStyleMenu.add(georgiaMenu);
	
	    boldMenu.add(notBoldMenuItem);
	    boldMenu.add(boldMenuItem);
	    italicMenu.add(notItalicMenuItem);
	    italicMenu.add(italicMenuItem);
	    textPopup.add(frameSizeMenu);
		textPopup.add(fontSizeMenu);
		textPopup.add(fontStyleMenu);
	
	    textPopup.add(boldMenu);
    	textPopup.add(italicMenu);
		textButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
            	if (e.getButton() == e.BUTTON1) {
            		dm.shape = "text";
        		} else if (e.getButton() == e.BUTTON3) {
        			textPopup.show(e.getComponent(), e.getX(), e.getY());
        		}
                
            }
        });
		toolBar.add(textButton);
		toolBar.addSeparator();

		// The color function
		URL colorURL=WhiteBoardGUI.class.getResource("/picture/color.jpg");
		Icon colorIcon=new ImageIcon(colorURL);
		colorButton = new JButton(colorIcon);
		colorButton.setOpaque(false);  
		colorButton.setContentAreaFilled(false); 
		colorButton.setFocusPainted(false);
		colorButton.setBorder(null); 
		colorButton.setToolTipText("Change a color");
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				colorFrame = new JFrame("Choose Color");
				colorFrame.setBounds(800, 450, 350, 100);
				setFont();
				setColorPage();
				colorFrame.setDefaultCloseOperation(colorFrame.HIDE_ON_CLOSE);
				colorFrame.setVisible(true);
			}
		});
		toolBar.add(colorButton);

	}

	// Click manage and the manage window pops up,with
	// clientList,clientOperationInfo,clientListTextArea,clientOperationInfoTextArea,startServiceButton,stopServiceButton,clientListScrollPane,clientOperationInfoScrollPane
	private void setManagePage() {
		// layout
		managePanel = new JPanel();
		managePanel.setLayout(null);
		manageFrame.add(managePanel);
		managePanel.setBackground(Color.white);

		// initialize
		clientList = new JLabel("Client List:");
		clientOperationInfo = new JLabel("Client Operation Information");
		clientListTextArea = new JTextArea();
		clientOperationInfoTextArea = new JTextArea();
		clientNumber = new JLabel("Client Number:");
		clientNumberTextField = new JTextField();

		// ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â¿ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â´Ãƒâ€šÃ‚Â»ÃƒÆ’Ã‚Â¨ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¡Ãƒâ€šÃ‚ÂªÃƒÆ’Ã‚Â¥Ãƒâ€¦Ã‚Â Ãƒâ€šÃ‚Â¨ÃƒÆ’Ã‚Â¦ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¢ÃƒÆ’Ã‚Â¨Ãƒâ€šÃ‚Â¡Ãƒâ€¦Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¥Ãƒâ€¦Ã‚Â Ãƒâ€¦Ã‚Â¸ÃƒÆ’Ã‚Â¨Ãƒâ€ Ã¢â‚¬â„¢Ãƒâ€šÃ‚Â½ ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â¿ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â´Ãƒâ€šÃ‚Â»ÃƒÆ’Ã‚Â¦ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬Å“Ãƒâ€šÃ‚Â­ÃƒÆ’Ã‚Â¨Ãƒâ€šÃ‚Â¡Ãƒâ€¦Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¤Ãƒâ€šÃ‚Â¸ÃƒÂ¯Ã‚Â¿Ã‚Â½ÃƒÆ’Ã‚Â¦ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬Å“Ãƒâ€šÃ‚Â­ÃƒÆ’Ã‚Â¥Ãƒâ€šÃ‚Â­ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬ï¿½ÃƒÆ’Ã‚Â¥Ãƒâ€¦Ã‚Â Ãƒâ€¦Ã‚Â¸ÃƒÆ’Ã‚Â¨Ãƒâ€ Ã¢â‚¬â„¢Ãƒâ€šÃ‚Â½
		clientListTextArea.setLineWrap(true);
		clientListTextArea.setWrapStyleWord(true);
		clientListTextArea.setText(clientInformation);
		// ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â¿ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â´Ãƒâ€šÃ‚Â»ÃƒÆ’Ã‚Â¨ÃƒÂ¢Ã¢â€šÂ¬Ã‚Â¡Ãƒâ€šÃ‚ÂªÃƒÆ’Ã‚Â¥Ãƒâ€¦Ã‚Â Ãƒâ€šÃ‚Â¨ÃƒÆ’Ã‚Â¦ÃƒÂ¯Ã‚Â¿Ã‚Â½Ãƒâ€šÃ‚Â¢ÃƒÆ’Ã‚Â¨Ãƒâ€šÃ‚Â¡Ãƒâ€¦Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¥Ãƒâ€¦Ã‚Â Ãƒâ€¦Ã‚Â¸ÃƒÆ’Ã‚Â¨Ãƒâ€ Ã¢â‚¬â„¢Ãƒâ€šÃ‚Â½ ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â¿ÃƒÂ¢Ã¢â‚¬Å¡Ã‚Â¬ÃƒÆ’Ã‚Â¦Ãƒâ€šÃ‚Â´Ãƒâ€šÃ‚Â»ÃƒÆ’Ã‚Â¦ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬Å“Ãƒâ€šÃ‚Â­ÃƒÆ’Ã‚Â¨Ãƒâ€šÃ‚Â¡Ãƒâ€¦Ã¢â‚¬â„¢ÃƒÆ’Ã‚Â¤Ãƒâ€šÃ‚Â¸ÃƒÂ¯Ã‚Â¿Ã‚Â½ÃƒÆ’Ã‚Â¦ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬Å“Ãƒâ€šÃ‚Â­ÃƒÆ’Ã‚Â¥Ãƒâ€šÃ‚Â­ÃƒÂ¢Ã¢â€šÂ¬Ã¢â‚¬ï¿½ÃƒÆ’Ã‚Â¥Ãƒâ€¦Ã‚Â Ãƒâ€¦Ã‚Â¸ÃƒÆ’Ã‚Â¨Ãƒâ€ Ã¢â‚¬â„¢Ãƒâ€šÃ‚Â½
		clientOperationInfoTextArea.setLineWrap(true);
		clientOperationInfoTextArea.setWrapStyleWord(true);

		stopServiceButton = new JButton("STOP SERVICE");
		clientListScrollPane = new JScrollPane(clientListTextArea);
		clientOperationInfoScrollPane = new JScrollPane(clientOperationInfoTextArea);

		// bound
		clientList.setBounds(20, 10, 100, 25);
		clientOperationInfo.setBounds(250, 10, 200, 25);
		clientListTextArea.setBounds(20, 50, 200, 300);
		clientOperationInfoTextArea.setBounds(250, 50, 310, 385);
		clientListScrollPane.setBounds(20, 50, 200, 300);
		clientNumber.setBounds(40, 360, 120, 25);
		clientNumberTextField.setBounds(160, 355, 50, 40);
		clientListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		clientOperationInfoScrollPane.setBounds(250, 50, 310, 380);
		clientOperationInfoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		stopServiceButton.setBounds(37, 403, 175, 25);
		
		clientNumberTextField
		.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		clientListTextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		clientOperationInfoTextArea
				.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

		// add
		managePanel.add(clientList);
		managePanel.add(clientOperationInfo);
		managePanel.add(stopServiceButton);
		managePanel.add(clientListScrollPane);
		managePanel.add(clientNumber);
		managePanel.add(clientNumberTextField);
		managePanel.add(clientOperationInfoScrollPane);

		// stop service action listener
		stopServiceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server.disconnectWithClient(Integer.parseInt(clientNumberTextField.getText()));
			}
		});
		managePanel.setVisible(true);
	}


	// Click connect and the connect window pops up,with
	// hostAddress,inputHostAddress,succeedConnectionButton,disconnectButton
	

	// Click color and the color window pops up,with color,chooseColorButton
	private void setColorPage() {
		// layout
		colorPanel = new JPanel();
		colorPanel.setLayout(null);
		colorFrame.add(colorPanel);
		colorPanel.setBackground(Color.white);

		// initialize
		chooseColorButton = new JButton("Choose Color");

		// bound
		chooseColorButton.setBounds(100, 15, 130, 25);

		// choose color action listener
		chooseColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ÃƒÂ¦Ã‹Å“Ã‚Â¾ÃƒÂ§Ã‚Â¤Ã‚ÂºÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ¥Ã¢â€žÂ¢Ã‚Â¨ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â¯Ã¯Â¿Â½ÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â , ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ï¿½ÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¯Ã‚Â¼Ã‹â€ ÃƒÂ§Ã‚ÂºÃ‚Â¿ÃƒÂ§Ã‚Â¨Ã¢â‚¬Â¹ÃƒÂ¥Ã‚Â°Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ©Ã‹Å“Ã‚Â»ÃƒÂ¥Ã‚Â¡Ã…Â¾, ÃƒÂ§Ã¢â‚¬ÂºÃ‚Â´ÃƒÂ¥Ã‹â€ Ã‚Â°ÃƒÂ¥Ã‚Â¯Ã‚Â¹ÃƒÂ¨Ã‚Â¯Ã¯Â¿Â½ÃƒÂ¦Ã‚Â¡Ã¢â‚¬Â ÃƒÂ¨Ã‚Â¢Ã‚Â«ÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ©Ã¢â‚¬â€�Ã‚Â­ÃƒÂ¯Ã‚Â¼Ã¢â‚¬Â°
				Color colorChosen = JColorChooser.showDialog(colorFrame, "Choose Color", null);
				// ÃƒÂ¥Ã‚Â¦Ã¢â‚¬Å¡ÃƒÂ¦Ã…Â¾Ã…â€œÃƒÂ§Ã¢â‚¬ï¿½Ã‚Â¨ÃƒÂ¦Ã‹â€ Ã‚Â·ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ¦Ã‚Â¶Ã‹â€ ÃƒÂ¦Ã‹â€ Ã¢â‚¬â€œÃƒÂ¥Ã¢â‚¬Â¦Ã‚Â³ÃƒÂ©Ã¢â‚¬â€�Ã‚Â­ÃƒÂ§Ã‚ÂªÃ¢â‚¬â€�ÃƒÂ¥Ã¯Â¿Â½Ã‚Â£, ÃƒÂ¥Ã‹â€ Ã¢â€žÂ¢ÃƒÂ¨Ã‚Â¿Ã¢â‚¬ï¿½ÃƒÂ¥Ã¢â‚¬ÂºÃ…Â¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ color ÃƒÂ¤Ã‚Â¸Ã‚Âº null
				if (colorChosen == null) {
					return;
				}
      
				// ÃƒÂ¦Ã…Â Ã…Â ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¨Ã‚Â®Ã‚Â¾ÃƒÂ§Ã‚Â½Ã‚Â®ÃƒÂ¤Ã‚Â¸Ã‚ÂºÃƒÂ¦Ã‚Â Ã¢â‚¬Â¡ÃƒÂ§Ã‚Â­Ã‚Â¾ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ¨Ã†â€™Ã…â€™ÃƒÂ¦Ã¢â€žÂ¢Ã‚Â¯
				chooseColorButton.setBackground(colorChosen);
				// ÃƒÂ¦Ã…Â Ã…Â ÃƒÂ©Ã¢â€šÂ¬Ã¢â‚¬Â°ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ¤Ã‚Â¼Ã‚Â ÃƒÂ©Ã¢â€šÂ¬Ã¯Â¿Â½ÃƒÂ¥Ã‹â€ Ã‚Â°drawMethod
                dm.color = colorChosen;
				// ÃƒÂ¨Ã…Â½Ã‚Â·ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬â€œÃƒÂ©Ã‚Â¢Ã…â€œÃƒÂ¨Ã¢â‚¬Â°Ã‚Â²ÃƒÂ§Ã…Â¡Ã¢â‚¬Å¾ ARGB ÃƒÂ¥Ã¯Â¿Â½Ã¢â‚¬Å¾ÃƒÂ¤Ã‚Â¸Ã‚ÂªÃƒÂ¥Ã‹â€ Ã¢â‚¬Â ÃƒÂ©Ã¢â‚¬Â¡Ã¯Â¿Â½ÃƒÂ¥Ã¢â€šÂ¬Ã‚Â¼
			}
		});

		// add
		colorPanel.add(chooseColorButton);
		colorPanel.setVisible(true);
	}

	// Click chat and the chat window pops up

	// Click closed, to achieve a warm jump box prompt, exit the program
	private void closeFrameTips() {
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitFunction();
				
				
			}
		});
	}
	
	private void setWidthPage() {
		widthPanel = new JPanel();
		widthPanel.setLayout(null);
		widthFrame.add(widthPanel);
		widthPanel.setBackground(Color.white);
	}
	
	public void setUsernameAndClientNumber(String username, String clientNumberID) {
		this.username = username;
		this.clientNumberID = clientNumberID;
		usernameTextField.setText(username);
		clientNumberStartTextField.setText(clientNumberID);
	}
	
	public void setServer(Server server) {
		this.server=server;
	}
	
	public void setClient(Client client) {
		this.client=client;
	}
	
	public void setNeedSavingFlag(boolean need) {
		this.needSavingFlag=need;
	}

	public void setClientInformation(String clientInformation) {
		this.clientInformation=clientInformation;
	}
	
	public void setClientListTextArea(String clientInformation) {
		if(clientListTextArea!=null)
		{
			clientListTextArea.setText("");
			clientListTextArea.setText(clientInformation);
		}
			
	}
	
	private void setReceiveText(String receiveText) {
		this.receiveText=receiveText;
	}
	
	

	public void setMyChatInfoTextArea(String receiveText) {
		chatInfoTextArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		
		chatInfoTextArea.append(receiveText);
		chatInfoTextArea.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		chatInfoTextArea.append("\n");
	}
	
	public void setChatInfoTextArea(String receiveText) {
		chatInfoTextArea.append(receiveText);
		chatInfoTextArea.append("\n");
	}

	public void setIsServer(Boolean isServer) {
		this.isServer=isServer;
	}
	
	public boolean getIsServer() {
		return this.isServer;
	}
	public Client getClient() {
		return this.client;
	}
	
	public Server getServer() {
		return this.server;
	}
	
	
	public void setChatInputTextField() {
		chatInputTextField.setText("");
	}

	public JPanel getWhiteBoardPanel() {
		return this.whiteBoardPanel;
	}
	
	public DrawMethod getDrawMethod() {
		return this.dm;
	}
	
	public void setEditInfoTextArea(String editingClients) {
		this.editInfoTextArea.setText(editingClients);
	}
	public void initialSaveWithSelectPath() {
		chooseFileFrame = new JFrame("Choose File");
		chooseFilePanel = new JPanel();
		chooseFilePanel.setLayout(null);
		browseButton = new JButton("BROWSE");
		filePath = new JLabel("File Path:");
		saveFileButton = new JButton("SAVE FILE");
		chooseFile=new JLabel("File Type:");
		chooseType=new JComboBox();
		chooseType.addItem("jpg");
		chooseType.addItem("png");
		chooseType.addItem("pdf");
		inputFilePath = new JTextField(30);
		chooseFileFrame.setBounds(635, 230, 650, 500);
		filePath.setBounds(10, 10, 100, 50);
		inputFilePath.setBounds(80, 20, 440, 30);
		browseButton.setBounds(525, 20, 100, 30);
		saveFileButton.setBounds(380, 60, 130, 30);
		chooseFile.setBounds(100, 60, 80, 30);
		chooseType.setBounds(170, 60, 100, 30);
		chooseFilePanel.add(filePath);
		chooseFilePanel.add(inputFilePath);
		chooseFilePanel.add(browseButton);
		chooseFileFrame.add(chooseFilePanel);
		chooseFilePanel.add(saveFileButton);
		chooseFilePanel.add(chooseFile);
		chooseFilePanel.add(chooseType);
		browseButton.addActionListener(new SaveActionListener());
		chooseFileFrame.setDefaultCloseOperation(chooseFileFrame.HIDE_ON_CLOSE);
		chooseFileFrame.setVisible(true);
	}
	
	public void initialSelectOpenFile() {
		chooseFileFrame = new JFrame("Choose File");
		chooseFilePanel = new JPanel();
		chooseFilePanel.setLayout(null);
		browseButton = new JButton("BROWSE");
		openFileButton = new JButton("OPEN");
		filePath = new JLabel("File Path:");
		inputFilePath = new JTextField(25);
		chooseFileFrame.setBounds(635, 230, 650, 500);
		filePath.setBounds(10, 10, 100, 50);
		inputFilePath.setBounds(80, 20, 440, 30);
		browseButton.setBounds(525, 20, 100, 30);
		openFileButton.setBounds(250, 60, 130, 30);
		chooseFilePanel.add(filePath);
		chooseFilePanel.add(inputFilePath);
		chooseFilePanel.add(browseButton);
		chooseFilePanel.add(openFileButton);
		chooseFileFrame.add(chooseFilePanel);
		browseButton.addActionListener(new OpenActionListener());
		chooseFileFrame.setDefaultCloseOperation(chooseFileFrame.HIDE_ON_CLOSE);
		chooseFileFrame.setVisible(true);
	}
	
	public void clearBoard() {
		dm.clearAllItems((Graphics2D) whiteBoardPanel.getGraphics());
		for(int i=0;i<dm.cleanText.size();i++) {
			JTextArea clean = dm.cleanText.get(i);
			clean.setVisible(false);
		}
		dm.cleanText = new ArrayList<JTextArea>();
	}
	public void clearMemory() {
		dm.listShape=new ArrayList<ShapeList>();
	}
	//public void initialSaveWithoutSelectPath()
	public void newFunction() {
		if(isServer) {
			if(needSavingFlag) {
				int saving = JOptionPane.showConfirmDialog(null, "Do you want to save the painting?", "Tips",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (saving == JOptionPane.YES_OPTION) {
					if(dm.path.equals("")) {
						initialSaveWithSelectPath();
						saveFileButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e1) {
								
								chooseFileFrame.setVisible(false);
								SaveFile.writeNewFile(dm.listShape, inputFilePath.getText(),chooseType.getSelectedItem().toString(),whiteBoardPanel);
								//clear the memory
								dm.clearMemory();
								//clear the page
								clearBoard();
								server.sendNewPage();
							}
						});
					}
					else {
						SaveFile.writeNewFile(dm.listShape, dm.path,whiteBoardPanel);
						//clear the memory
						dm = new DrawMethod(whiteBoardPanel,this);
						//clear the page
						clearBoard();
						server.sendNewPage();
					}
				}	
				else {
					//clear the memory
					dm = new DrawMethod(whiteBoardPanel,this);
					//clear the page
					clearBoard();
					server.sendNewPage();
				}
			}
			else {
				//clear the memory
				dm = new DrawMethod(whiteBoardPanel,this);
				//clear the page
				clearBoard();
				server.sendNewPage();
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Sorry, client cannot open a new file.");
		}
	}

	
	public void openAction() {
		initialSelectOpenFile();
		openFileButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e1) {
				
                clearBoard();
                dm.clearMemory();
                dm.path=inputFilePath.getText();
				dm.setListShape(OpenFile.readHistoryFile(dm.path));
				
				dm.drawAllItems((Graphics2D)whiteBoardPanel.getGraphics(),new WhiteBoardGUI());
				
				chooseFileFrame.setVisible(false);
				//server.sendNewPage();
				server.sendNewFile();
			}
		});
	}
	public void openFunction() {
		if(isServer) {
			if(needSavingFlag) {
				int saving = JOptionPane.showConfirmDialog(null, "Do you want to save the painting?", "Tips",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (saving == JOptionPane.YES_OPTION) {
					if(dm.path.equals("")) {
						initialSaveWithSelectPath();
						saveFileButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e1) {
								
								chooseFileFrame.setVisible(false);
								SaveFile.writeNewFile(dm.listShape, inputFilePath.getText(),chooseType.getSelectedItem().toString(),whiteBoardPanel);
								openAction();
								
							}
						});
					}
					else {
						SaveFile.writeNewFile(dm.listShape, dm.path,whiteBoardPanel);
						openAction();
						//server.sendNewPage();
						//server.sendNewFile();
					}
				}	
				else {
					openAction();
					
				}
			}
			else {
				openAction();
				
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Sorry, client cannot open a new file.");
		}
	}
	
	
	
	public void saveFunction() {
		if(isServer) {
			if(dm.path.equals("")) {
				initialSaveWithSelectPath();
				saveFileButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e1) {
						
						chooseFileFrame.setVisible(false);
						SaveFile.writeNewFile(dm.listShape, inputFilePath.getText(),chooseType.getSelectedItem().toString(),whiteBoardPanel);
						dm.path=inputFilePath.getText()+"."+chooseType.getSelectedItem().toString();
						setNeedSavingFlag(false);
					}
				});
			}
			else {
				SaveFile.writeNewFile(dm.listShape, dm.path,whiteBoardPanel);
				setNeedSavingFlag(false);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Sorry, client cannot save a file.");
		}
		
	}
	
	public void saveAsFunction() {
		initialSaveWithSelectPath();
		saveFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				chooseFileFrame.setVisible(false);
				SaveFile.writeNewFile(dm.listShape, inputFilePath.getText(),chooseType.getSelectedItem().toString(),whiteBoardPanel);
			
			}
		});
	}
	
	public void exitFunction() {
		int tips = JOptionPane.showConfirmDialog(null, "Do you want to exit the Shared White Board?", "Tips",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (tips == JOptionPane.YES_OPTION) {
			
			if(isServer) {
				if(needSavingFlag) {
					int saving = JOptionPane.showConfirmDialog(null, "Do you want to save the painting?", "Tips",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (saving == JOptionPane.YES_OPTION) {
				if(dm.path.equals("")) {
					initialSaveWithSelectPath();
					saveFileButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e1) {
							
							chooseFileFrame.setVisible(false);
							SaveFile.writeNewFile(dm.listShape, inputFilePath.getText(),chooseType.getSelectedItem().toString(),whiteBoardPanel);
							System.exit(0);
						}
					});
				}
				else {
					SaveFile.writeNewFile(dm.listShape, dm.path,whiteBoardPanel);
					System.exit(0);
				}
				}
					else {
						System.exit(0);
					}
			}
			else {
				System.exit(0);
			}
			}
			else {
				System.exit(0);
			}
		}
		else {
			
		}
		
	}
	
	public void closeFunction() {
		if(isServer) {
			if(needSavingFlag) {
				int saving = JOptionPane.showConfirmDialog(null, "Do you want to save the painting?", "Tips",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (saving == JOptionPane.YES_OPTION) {
			if(dm.path.equals("")) {
				initialSaveWithSelectPath();
				saveFileButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e1) {
						
						chooseFileFrame.setVisible(false);
						SaveFile.writeNewFile(dm.listShape, inputFilePath.getText(),chooseType.getSelectedItem().toString(),whiteBoardPanel);
						clearBoard();
						dm.clearMemory();
						server.sendCloseFile();
					}
				});
			}
			else {
				SaveFile.writeNewFile(dm.listShape, dm.path,whiteBoardPanel);
				clearBoard();
				dm.clearMemory();
				server.sendCloseFile();
			}
		}
				else{
					clearBoard();
					dm.clearMemory();
					server.sendCloseFile();
				}
					}
				}
		else {
			JOptionPane.showMessageDialog(null, "Sorry, client cannot close a file.");
		}
	}
	public List<JTextArea> getCleanText() {
		return dm.cleanText;
	}
    public List<ShapeList> getListShape(){
    	return dm.listShape;
    }
    public void updateLog(Log log) {
    	String logMessage="";
    	for(Object i:log.getLog()) {
    		logMessage+=i.toString()+"\n";
    	}
    	this.editInfoTextArea.setText(logMessage);
    }
    public JButton getChooseShapeButton() {
    	return this.chooseShapeButton;
    }
}
