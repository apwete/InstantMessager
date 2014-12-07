/****************************************************************************
 * @Class: CIST 2372 Java 2 												*
 * @Term: Fall 2014 														*
 * @Lab:  Java Project														*
 * @author: William M. Driver 												*
 * @date: 11/30/2014 														*
 * @Description: IM Client													*
 *  																		*
 * @version: 1.0 															* 
 * @update: 																*
 ****************************************************************************/

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

public class Client extends JFrame implements ActionListener {

	/************************** Behaviors *******************************/
	private JPanel bottom = new JPanel();
	private JTextField usertext;
	private JButton send;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "CLIENT - START";
	private String serverIP;
	private Socket connection;
	
	

	/************************* Constructors *****************************/
	public Client(String host) {
		super("Client");
		serverIP = host;
		bottom .setBorder(BorderFactory.createBevelBorder(1));
		usertext = new JTextField("", 20);
		usertext.setEditable(false);
		usertext.addActionListener(this);
		send = new JButton("Send");
		send.addActionListener(this);
		chatWindow = new JTextArea();
		chatWindow.setEditable(false);
		bottom.add(usertext);
		bottom.add(send);
		add(new JScrollPane(chatWindow));
		add(bottom, BorderLayout.SOUTH);
		setSize(350, 450);
		setLocation(10, 480);
		setVisible(true);
	} // end Server Constructor

	/********************* Operational methods **************************/
	public void startRunning() {

		// if(message.equals("CLIENT - START")){
		connectToServer();
		setupStreams();
		whileChating();
		closeStuff();
		// }
	} // end startRunning()

	private void connectToServer() {
		try {
			showMessage("\nConnecting . . .");
			connection = new Socket(InetAddress.getByName(serverIP), 8888);
			showMessage("\nNow Coneceted to Server");
		} catch (IOException ioe) {
			showMessage("\nERROR: Cannot make a conection"
					+ "\nIssue in waitforConnection");
		} // end try
	} // end ConnectToServer()

	private void setupStreams() {
		try {
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			input = new ObjectInputStream(connection.getInputStream());
			showMessage("\nYou are read to connecto to the Server\n");
		} catch (IOException ioe) {
			showMessage("\nERROR: Cannot connect to client"
					+ "\nIssue in setupStreams");
		} // end try
	} // end setupStreams

	private void whileChating() {
		String message = "You are now connected to Server";
		showMessage("\n" + message);
		ableToType(true);
		do {
			// talky talky
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch (ClassNotFoundException cnfe) {
				showMessage("\nERROR: Could not read what client sent"
						+ "\nIssue in whileChating");
			} catch (IOException e) {
				showMessage("\nERROR: Could not read what client sent"
						+ "\nIssue in whileChating");
			} // end try
		} while (!message.equals("SERVER - END")); // end do/while loop
	} // end whileChating()

	private void closeStuff() {
		showMessage("\n\nClose Chat\n");
		sendMessage("END");
		// ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException ioe) {
			showMessage("\nERROR: Failed to close streams"
					+ "\nIssue in closeStuff");
		} // end try
	} // end closeCrap()

	/*********************** general Methods ****************************/
	private void sendMessage(String message) {
		try {
			output.writeObject("CLIENT - " + message);
			output.flush(); // clean out stream
			showMessage("\nCLIENT - " + message + "\n");
		} catch (IOException ioe) {
			chatWindow.append("\nERROR: Cannot send message."
					+ "\nIssue in snedMessage");
		} // end try
	} // end sendMessage()

	private void showMessage(final String words) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(words);
			}
		}); // end SwingUtilities
	} // end showMessage()

	private void ableToType(final boolean type) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				usertext.setEditable(type);
			}
		}); // end SwingUtilities
	} // end abletoType()

	/*********************** Listeners ****************************/
	public void actionPerformed(ActionEvent e) {
		sendMessage(e.getActionCommand());
		usertext.setText("");
	} // ActionListener()
} // end Server class