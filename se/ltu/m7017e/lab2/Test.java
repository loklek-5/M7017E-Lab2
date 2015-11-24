package se.ltu.m7017e.lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;


class Test {
 public static void main(String[] args) {
		
		Gst.init("Hosting", args);
		Pipeline mypipeline;
		String address="to be Changed";
		int sendPort=6666;
		int recvPort=7777;

		String toHost = String.format(
				" autoaudiosrc ! queue ! mulawenc ! rtppcmupay ! udpsink host=%s port=%d sync=false async=false",
				address, sendPort);
		String fromHost = String.format(" udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! audioconvert ! autoaudiosink",
                        recvPort);
		
		
		String pipeline = String.format("%s", toHost)+String.format("%s", fromHost);

		// And so, we finally create the pipeline.
		mypipeline = Pipeline.launch(pipeline);
		mypipeline.setState(State.PLAYING);
		for( ; ; ){
              
         }
		//mypipeline.setState(State.NULL);
    	//mypipeline = null;
    	//Gst.quit();
}
}