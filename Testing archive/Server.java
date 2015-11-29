package lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

class Server {

	private Pipeline serverPipeline;
			private int recvPortForClient1=1234;
		private int sendPortForClient1=6789;
		private int recvPortForClient2=9876;
		private int sendPortForClient2=5432;
		private String guest1Address="192.168.1.71";
		private String guest2Address="192.168.1.68";


	Server() {
		String[] args = {};
		Gst.init("Server", args);
	}

	void startServer(String client1Addr, int recvPortForClient1,
		int sendPortForClient1, String client2Addr, 
		int recvPortForClient2, int sendPortForClient2) {



		String caps = "audio/x-raw-int, channels=1, rate=8000, width=16, depth=16, endianness=1234, signed=true";

		String micAudio = String.format(
		 		"autoaudiosrc ! %s ! tee name=micaudio ! queue !", caps);

		String audioToClient1 = String.format(
	 		"liveadder name=toclient1 ! mulawenc ! rtppcmupay ! queue ! "
		  				+ "udpsink host=%s port=%d async=false sync=false",
		  		guest1Address, sendPortForClient1);
		String audioToClient2 = String
				.format("micaudio. ! queue ! liveadder name=toclient2 ! mulawenc ! "
						+ "rtppcmupay ! queue ! udpsink host=%s port=%d async=false sync=false",
						guest2Address, sendPortForClient2);

		String audioFromClient1 = String
				.format("udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! "
						+ "capsfilter ! %s ! tee name=fromguest1 ! queue ! audioconvert ! liveadder name=tohost",
						recvPortForClient1, caps);
		String audioFromClient2 = String
				.format("udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! "
					+ "capsfilter ! %s ! tee name=fromguest2 ! queue ! audioconvert ! tohost.",
					recvPortForClient2, caps);

		String audioToHost = "! queue ! autoaudiosink";
		String crossmix = "fromguest1. ! queue ! toclient2. fromguest2. ! queue ! toclient1.";


		String pipeline = String.format("%s %s %s %s %s %s", micAudio, audioToClient1, audioToClient2,
				audioFromClient1, audioToHost, audioFromClient2, crossmix);

		serverPipeline = Pipeline.launch(pipeline);
		serverPipeline.setState(State.PLAYING);


	}
	void endConference() {
		serverPipeline.setState(State.NULL);
		serverPipeline = null;
		Gst.quit();
	}

}