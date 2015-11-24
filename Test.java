package se.ltu.m7017e.lab2;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;


class Test {
 public static void main(String[] args) {
		
		Gst.init("Hosting", args);
		Pipeline mypipeline;
		String address="130.240.93.133";
		int sendPort=7777;
		

        String fromHost = String
                .format(" udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! audioconvert ! autoaudiosink",
                        8888);
        String toHost = String.format(
				" autoaudiosrc ! queue ! mulawenc ! rtppcmupay ! udpsink host=%s port=%d sync=false async=false",
				address, sendPort);

        String pipeline = toHost + fromHost;
        // And so, we finally create it.
        mypipeline = Pipeline.launch(pipeline);
        mypipeline.setState(State.PLAYING);
		
		
		
		
		for( ; ; ){
              
         }
		//mypipeline.setState(State.NULL);
    	//mypipeline = null;
    	//Gst.quit();
}
}