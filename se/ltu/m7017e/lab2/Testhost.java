package se.ltu.m7017e.lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

class Testhost {


	 public static void main(String[] args) {
		
		Gst.init("Receiving", args);
		Pipeline hostPipeline;
		int recvPort=1234;
		int sendPort=6789;
		String guest1Address="127.0.0.1";

		String caps = "audio/x-raw-int, channels=1, rate=8000, width=16, depth=16, endianness=1234, signed=true";

		String audioFromHost = String.format(
		 		"autoaudiosrc ! %s ! tee name=fromhost ! queue !", caps);

		String audioToGuest1 = String.format(
	 		"liveadder name=toguest1 ! mulawenc ! rtppcmupay ! queue ! "
		  				+ "udpsink host=%s port=%d async=false sync=false",
		  		guest1Address, sendPort);

		String audioFromGuest1 = String
				.format("udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! "
						+ "capsfilter ! %s ! tee name=fromguest1 ! queue ! audioconvert ! liveadder name=tohost",
						recvPort, caps);
		String audioToHost = "! queue ! autoaudiosink";
		String crossmix = "fromguest1. ! queue ! toguest1.";

		// String pipeline = String.format("%s %s %s %s",audioFromHost, audioToGuest,
		 //		audioFromGuest, audioToHost);

		String pipeline = String.format("%s %s %s %s %s",
		 audioFromGuest1,audioToHost,audioFromHost,audioToGuest1,crossmix);

			// 	String pipeline = String.format("%s %s",
		 // audioFromGuest1,audioToHost);

		// And so, we finally create the pipeline.
		hostPipeline = Pipeline.launch(pipeline);
		hostPipeline.setState(State.PLAYING);

		for( ; ; ){
              
         }
		//mypipeline.setState(State.NULL);
    	//mypipeline = null;
    	//Gst.quit();
	}

}