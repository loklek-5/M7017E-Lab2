package se.ltu.m7017e.lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

class Client {


	 public static void main(String[] args) {
		
		Gst.init("Client", args);
		Pipeline clientPipeline;
		int port=8888;
		int multicastPort=3000;
		String serverAddress="127.0.0.1";
		String multicastAddress="224.1.1.1";


		//String caps = "audio/x-raw-int, channels=1, rate=8000, width=16, depth=16, endianness=1234, signed=true";

		String audioToServer = String.format(
		 		"autoaudiosrc ! queue ! mulawenc ! rtppcmupay ! udpsink host=%s port=%d",serverAddress,port);

		String audioFromServer = String.format("udpsrc multicast-group=%s auto-multicast=true port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! audioconvert ! autoaudiosink",multicastAddress,multicastPort);
		                                       
		String pipeline = String.format("%s", audioToServer) + String.format("%s", audioFromServer);
		// And so, we finally create the pipeline.
		clientPipeline = Pipeline.launch(pipeline);
		clientPipeline.setState(State.PLAYING);

		for( ; ; ){
              
         }
		//mypipeline.setState(State.NULL);
    	//mypipeline = null;
    	//Gst.quit();
	}

}