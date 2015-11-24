import Buffer.Buffer;
import Buffer.BoundedBuffer;
import Consumer.Consumer;
import Producer.Producer;

/**
 *This class contain the executable main for creating a Buffer and a Producer
 */
public class ServerProducer
{
	public static void main(String args[]) {
		Buffer server = new BoundedBuffer(args[0]);

      		// now create producer threads
      		Thread producerThread = new Thread(new Producer(server,1));
      		producerThread.start();

      		// Create multiple Producer
      		//Thread producerThread2 = new Thread(new Producer(server,2));
      		//producerThread2.start();       
	}
}
