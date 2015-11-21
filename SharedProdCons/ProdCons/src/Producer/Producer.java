package Producer;
import Buffer.Buffer;
import Tools.SleepTools;

public class Producer implements Runnable
{
   int number;

   public Producer(Buffer b) {
      buffer = b;
   }

   public Producer(Buffer b, int number) {
      buffer = b;
      this.number = number;
   }
   
   public void run()
   {
   String message;
      int i=0;
      while (true) {
         System.out.println("PRODUCTEUR "+number+" : En train de dormir...");
	      //SleepTools.nap();

         //controled nap
         try { Thread.sleep(2000); }
         catch (InterruptedException e) {}

         message = new String("Produit no " + i + " ");      
         System.out.println("PRODUCTEUR "+number+" :Message produit " + message);
           
         buffer.insert(message);

         i++;
      }
   }
   
   private  Buffer buffer;
}
