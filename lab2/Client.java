package lab2;

import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;

/**
 * This class is our model for the client
 * according to MVC design pattern
 */

class Client {
 
    private Pipeline clientPipeline;

    /**
     * The constructor initializes Gstreamer and instantiates an empty pipeline.
     */
	Client() {
        String[] args = {};
        Gst.init("Client", args);
	}


    /**
     * This function instantiates the client
     * 
     * @param receivPort     
     *            Port from which the client receives from the server
     * @param serverAddress
     *            IP address of the server
     * @param sendPort
     *            Port to which the client sends to the server
     */
    void startClient(int receivPort, String serverAddress, int sendPort) {

    //client audio source -> queue -> encode -> payload -> send on server's udp address 
    String audioToServer = String
            .format(" autoaudiosrc ! queue ! mulawenc ! rtppcmupay ! udpsink host=%s port=%d sync=false async=false",
                    serverAddress, sendPort);

    // From the UDP source we take the input as RTP-> queue -> payunload -> decode -> convert the audio -> to client audio sink  
    String audioFromServer = String
            .format(" udpsrc port=%d caps=\"application/x-rtp\" ! queue ! rtppcmudepay ! mulawdec ! audioconvert ! autoaudiosink",
                    receivPort);
    
    // Here we form our full argument list by adding all the previous variables
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
   /**
     * Ends the conference for client side
    */
    void endConference() {
    	clientPipeline.setState(State.NULL);
    	clientPipeline = null;
    	Gst.quit();
    }
}