package Consumer;
import Buffer.Buffer;
import Tools.SleepTools;

public class Consumer implements Runnable
{
   int number;

   public Consumer(Buffer b) { 
      buffer = b;
      this.number = 99999;
   } 

   public Consumer(Buffer b, int number) { 
      buffer = b;
      this.number = number;
   }
   
   public void run()
   {
   String message;
   
     while (true)
      {
         System.out.println("CONSOMMATEUR "+number+" : En train de dormir...");
	      //SleepTools.nap(); 

         //controled nap
         try { Thread.sleep(2000); }
         catch (InterruptedException e) {}
         
         System.out.println("CONSOMMATEUR "+number+" : Pret a consommer...");
           
         message = (String)buffer.remove();
      }
   }
   
   private  Buffer buffer;
}


