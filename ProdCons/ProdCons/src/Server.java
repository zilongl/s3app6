import Buffer.BoundedBuffer;
import Buffer.Buffer;
import Consumer.Consumer;
import Producer.Producer;
public class Server
{
	public static void main(String args[]) {
		Buffer server = new BoundedBuffer();

      		// now create the producer and consumer threads
      		Thread producerThread = new Thread(new Producer(server,1));
      		Thread producerThread2 = new Thread(new Producer(server,2));
      		Thread consumerThread = new Thread(new Consumer(server,1));
      		Thread consumerThread2 = new Thread(new Consumer(server,2));
      
      		producerThread.start();
      		producerThread2.start();
      		consumerThread.start();         
      		consumerThread2.start();        
	}
}
