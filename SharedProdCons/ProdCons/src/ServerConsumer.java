import Buffer.Buffer;
import Buffer.BoundedBuffer;
import Consumer.Consumer;
import Producer.Producer;

/**
 *This class contain the executable main for creating a Buffer and a Consumer
 */
public class ServerConsumer
{
	public static void main(String args[]) {
		Buffer server = new BoundedBuffer(args[0]);

      		// now create consumer threads
      		Thread consumerThread = new Thread(new Consumer(server,1));
      		consumerThread.start();    
	}
}
