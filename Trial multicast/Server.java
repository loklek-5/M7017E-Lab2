package se.ltu.m7017e.lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

class Server {


	 public static void main(String[] args) {
		
		Gst.init("server", args);
		Pipeline serverPipeline;
		int port=8888;
		int multicastPort=3000;
		String multicastAddress="127.0.0.1";


		String caps = "\"application/x-rtp\"";

		String audioswitch = String.format(
		 		"udpsrc port=%d caps=%s ! queue ! udpsink host=%s auto-multicast=true port=%d",port,caps,multicastAddress,multicastPort);

		String pipeline = String.format("%s", audioswitch);

			// 	String pipeline = String.format("%s %s",
		 // audioFromGuest1,audioToHost);

		// And so, we finally create the pipeline.
		serverPipeline = Pipeline.launch(pipeline);
		serverPipeline.setState(State.PLAYING);

		for( ; ; ){
              
         }
		//mypipeline.setState(State.NULL);
    	//mypipeline = null;
    	//Gst.quit();
	}

}