/****************************************************************************
 * @Class: CIST 2372 Java 2 												*
 * @Term: Fall 2014 														*
 * @Lab:  Java Project														*
 * @author: William M. Driver 												*
 * @date: 11/30/2014 														*
 * @Description: IM Server Tester											*
 *  																		*
 * @version: 1.0 															* 
 * @update: 																*
 ****************************************************************************/

import javax.swing.JFrame;

public class ClientTest {
	public static void main(String[] args) {
		Client client = new Client("127.0.0.1");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.startRunning();
	}

}
