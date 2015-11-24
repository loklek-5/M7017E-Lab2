package se.ltu.m7017e.lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

class Testhost {


	 public static void main(String[] args) {
		
		Gst.init("Receiving", args);
		Pipeline hostPipeline;
		int guest1RecvPort=7777;
	

		String caps = "audio/x-raw-int, channels=1, rate=8000, width=16, depth=16, endianness=1234, signed=true";

		String audioFromGuest1 = String
				.format("udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! "
						+ "capsfilter ! %s ! tee name=fromguest1 ! queue ! audioconvert ! liveadder name=tohost",
						guest1RecvPort, caps);
		String audioToHost = "! queue ! autoaudiosink";
	
		String pipeline = String.format("%s %s ",
				audioFromGuest1, audioToHost);

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