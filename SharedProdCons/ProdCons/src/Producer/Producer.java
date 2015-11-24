package Producer;
import Buffer.Buffer;
import Tools.SleepTools;
/**
 * This class is a producer that produce messages(String) and insert to a buffer  
 */
public class Producer implements Runnable
{
   int number;

/**
 *Constructor with buffer only
 *@param b instance of buffer to give 
 */
   public Producer(Buffer b) {
      buffer = b;
   }

/**
 *Constructor with buffer and a identificator for this particuliar Producer
 *@param b buffer to give 
 *@param number identificator for this particuliar Producer
 */
   public Producer(Buffer b, int number) {
      buffer = b;
      this.number = number;
   }
   
/**
 * This method begin a thread for the Producer
 */
   public void run()
   {
   String message;
      int i=0;
      while (true) {
         System.out.println("PRODUCTEUR "+number+" : En train de dormir...");
	      SleepTools.nap();

         //controled nap
         //try { Thread.sleep(2000); }
         //catch (InterruptedException e) {}

         message = new String("Produit no " + i + " ");      
         System.out.println("PRODUCTEUR "+number+" :Message produit " + message);
           
         buffer.insert(message);

         i++;
      }
   }
   
   private  Buffer buffer;
}
