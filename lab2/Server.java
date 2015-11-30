package lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;


/**
 * This class is our model for the server
 * according to MVC design pattern
 */

class Server {

	private Pipeline serverPipeline;
	private int recvPortForClient1;
	private int sendPortForClient1;
	private int recvPortForClient2;
	private int sendPortForClient2;
	private String client1Addr;
	private String client2Addr;

	/**
	 * The constructor initializes Gstreamer and instantiates an empty pipeline.
	 */
	Server() {
		String[] args = {};
		Gst.init("Server", args);
	}

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

	void startServer(String client1Addr, int recvPortForClient1,
		int sendPortForClient1, String client2Addr, 
		int recvPortForClient2, int sendPortForClient2) {


		// Here we set the caps to the audio quality which is sufficient for human voice in order to minimize the bandwidth usage
		String caps = "audio/x-raw-int, channels=1, rate=8000, width=16, depth=16, endianness=1234, signed=true";

		// Our source comes from the microphone, tee is used here to name the inut micaudio. we will use this later for crossmixing 
		// We then need to queue so that we have a buffer for the next processing command
		String micAudio = String.format(
		 		"autoaudiosrc ! %s ! tee name=micaudio ! queue !", caps);

		// Still to be commented 
		String audioToClient1 = String.format(
	 		"liveadder name=toclient1 ! mulawenc ! rtppcmupay ! queue ! "
		  				+ "udpsink host=%s port=%d async=false sync=false",
		  		client1Addr, sendPortForClient1);

		// Still to be commented 
		String audioToClient2 = String
				.format("micaudio. ! queue ! liveadder name=toclient2 ! mulawenc ! "
						+ "rtppcmupay ! queue ! udpsink host=%s port=%d async=false sync=false",
						client2Addr, sendPortForClient2);
		
		// Still to be commented 
		String audioFromClient1 = String
				.format("udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! "
						+ "capsfilter ! %s ! tee name=fromguest1 ! queue ! audioconvert ! liveadder name=tohost",
						recvPortForClient1, caps);
		
		// Still to be commented 
		String audioFromClient2 = String
				.format("udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! "
					+ "capsfilter ! %s ! tee name=fromguest2 ! queue ! audioconvert ! tohost.",
					recvPortForClient2, caps);

		// Still to be commented 
		String audioToHost = "! queue ! autoaudiosink";
		
		// We crossmix all the inputs in order to play it in only 1 playbin
		String crossmix = "fromguest1. ! queue ! toclient2. fromguest2. ! queue ! toclient1.";

		// Here we form our full argument list by adding all the previous variables
		String pipeline = String.format("%s %s %s %s %s %s", micAudio, audioToClient1, audioToClient2,
				audioFromClient1, audioToHost, audioFromClient2, crossmix);

		serverPipeline = Pipeline.launch(pipeline);
		serverPipeline.setState(State.PLAYING);
		System.out.println("Server - In client1 send port CLASS");
		System.out.println(sendPortForClient1);
		System.out.println("Server - In client1 address CLASS");
		System.out.println(client1Addr);
		System.out.println("Server - In client1 receive port CLASS");
        System.out.println(recvPortForClient1);

	}

	/**
	 * Ends the conference for server side
 	*/
	void endConference() {
		serverPipeline.setState(State.NULL);
		serverPipeline = null;
		Gst.quit();
	}

}