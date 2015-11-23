import Buffer.Buffer;
import Buffer.BoundedBuffer;
import Consumer.Consumer;
import Producer.Producer;
public class ServerProducer
{
	public static void main(String args[]) {
		Buffer server = new BoundedBuffer(args[0]);

      		// now create producer threads
      		Thread producerThread = new Thread(new Producer(server,1));
      		producerThread.start();        
	}
}
