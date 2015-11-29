package lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

class Client {
 
 		Pipeline clientPipeline;
		Client() {
        String[] args = {};
        Gst.init("Client", args);
    	}

        void startClient(int receivPort, String serverAddress, int sendPort) {

        String audioToServer = String
                .format(" autoaudiosrc ! queue ! mulawenc ! rtppcmupay ! udpsink host=%s port=%d sync=false async=false",
                        serverAddress, sendPort);

        String audioFromServer = String
                .format(" udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! audioconvert ! autoaudiosink",
                        receivPort);
        String pipeline = audioToServer + audioFromServer;
        clientPipeline = Pipeline.launch(pipeline);
        clientPipeline.setState(State.PLAYING);

        System.out.println("Client - In receive port CLASS");
        System.out.println(receivPort);
        System.out.println("Client - In server address CLASS");
        System.out.println(serverAddress);
        System.out.println("Client - In send port CLASS");
        System.out.println(sendPort);
    	}
		
        void endConference() {
    	clientPipeline.setState(State.NULL);
    	clientPipeline = null;
    	Gst.quit();
    }
}