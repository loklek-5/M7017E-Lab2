package lab2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is our controller for the view GUI
 * according to MVC design pattern
 */

class Controller {
	private static Client client;
	private static Server server;

	/**
	 * Runs the GUI
	 * 
	 * @param args Not used, here for testing if needed
	 */
	public static void main(String[] args) {
		GUI gui = new GUI();
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });	}

	/**
	 * This function instantiates the server in order to have a conversation
	 * with 2 more people, that is a total of 3 with the hist himself
	 * 
	 * @param client1Addr
	 *            IP address of the 1rst client
	 * @param recvPortForClient1
	 *            Port from which the server receives from the 1rst client
	 * @param sendPortForClient1
	 *            Port to which the server sends to the 1rst client
	 * @param client2Addr
	 *            IP address of the 2nd client
	 * @param recvPortForClient2
	 *            Port from which the server receives from the 2nd client
	 * @param sendPortForClient2
	 *            Port to which the server sends to the 2nd client
	 */
	static void serverStarts(String client1Addr, int recvPortForClient1,
			int sendPortForClient1, String client2Addr, int recvPortForClient2,
			int sendPortForClient2) {
		server = new Server();
		server.startServer(client1Addr, recvPortForClient1, sendPortForClient1,
				client2Addr, recvPortForClient2, sendPortForClient2);
	}

	/**
	 * This function instantiates the client 
	 * 
	 * @param receivPort	 
	 *            Port from which the client receives from the server
	 * @param serverAddress
 	 *            IP address of the server
	 * @param sendPort
	 *            Port to which the client sends to the server
	 */
	static void clientStarts(int receivPort, String serverAddress,
			int sendPort) {
		client = new Client();
		client.startClient(receivPort, serverAddress, sendPort);
	}

	/**
	 * Ends the session for the peer executing it
	 */
	static void disconnect() {
		if (client != null)
			client.endConference();
		if (server != null)
			server.endConference();
		client = null;
		server = null;
	}
}