package Consumer;
import Buffer.Buffer;
import Tools.SleepTools;

/**
 * This class is a consumer that remove messages(String) from a buffer  
 */
public class Consumer implements Runnable
{
   int number;
/**
 *Constructor with buffer only
 *@param b instance of buffer to give 
 */
   public Consumer(Buffer b) { 
      buffer = b;
      this.number = 99999;
   } 
/**
 *Constructor with buffer and a identificator for this particuliar Consumer
 *@param b buffer to give 
 *@param number identificator for this particuliar Consumer
 */
   public Consumer(Buffer b, int number) { 
      buffer = b;
      this.number = number;
   }
   
/**
 * This method begin a thread for the Consumer
 */
   public void run()
   {
   String message;
   
     while (true)
      {
         System.out.println("CONSOMMATEUR "+number+" : En train de dormir...");
	      SleepTools.nap(); 

         //controled nap
         //try { Thread.sleep(2000); }
         //catch (InterruptedException e) {}
         
         System.out.println("CONSOMMATEUR "+number+" : Pret a consommer...");
           
         message = (String)buffer.remove();
      }
   }
   
   private  Buffer buffer;
}


