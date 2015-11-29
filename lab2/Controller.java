package lab2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

class Controller {
	private static Client client;
	private static Server server;

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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });	}

	static void serverStarts(String client1Addr, int recvPortForClient1,
			int sendPortForClient1, String client2Addr, int recvPortForClient2,
			int sendPortForClient2) {
		server = new Server();
		server.startServer(client1Addr, recvPortForClient1, sendPortForClient1,
				client2Addr, recvPortForClient2, sendPortForClient2);
	}

	static void clientStarts(int receivPort, String serverAddress,
			int sendPort) {
		client = new Client();
		client.startClient(receivPort, serverAddress, sendPort);
	}

	static void disconnect() {
		if (client != null)
			client.endConference();
		if (server != null)
			server.endConference();
		client = null;
		server = null;
	}
}