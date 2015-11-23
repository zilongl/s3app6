package Buffer;

import ConcurrenceControl.*;
import SharedMemoryUtilities.SharedMemory;

public class BoundedBuffer implements Buffer
{ 
   public static final int   BUFFER_SIZE = 3;

   public static final int CONSUMER = 0; 
   public static final int PRODUCER = 1;  
   public static final int TURN = 0;   
   public static final int LOAD = 1;
   public static final int SPACE = 2;
   public static final int BUFFER_BEGIN = 3;
   private volatile int in;  // points to the next free position in the buffer
   private volatile int out; // points to the next full position in the buffer
   private int handle;
   ConcurrenceControlPeterson control = new ConcurrenceControlPeterson();

   /*Shared memory cases 
   0: TURN=0;
   
   count-->divided in 2 
   1: load = 0; number of product loaded (Consumer aquiring load)
   2: space = N; number of space left (Producer aquiring space)

   3+N : table with N+1 cases
   */

   public BoundedBuffer(String memory_address)
   {
      handle = Integer.parseInt(memory_address);

      // variables initialisation 
      in = 0;
      out = 0;
   }

   // producer calls this method
   public synchronized void insert(String item) {

      try{
         control.acquire(handle, PRODUCER);
         System.out.println("AQUIRED");
         SharedMemory.write(handle, (in+BUFFER_BEGIN), item);
         in = (in + 1) % BUFFER_SIZE;

         System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete enmagasine. Il y a = " +  (readIntFromSharedMemory(handle, LOAD)+1) + " produits.");

         control.release(handle, LOAD);
      }
      catch(Throwable t){
         System.out.println(t);
      }

      /*
       //Mutual Exclusion  or use "synchronized" in java
      while (lockProducer == true)
         ;//do nothing 

      lockProducer = true; //Put the lock

      while (count == BUFFER_SIZE) 
         ; // do nothing
      
      // add an item to the buffer
      buffer[in] = item;
      in = (in + 1) % BUFFER_SIZE;

    	if ((count+1) == BUFFER_SIZE)
         System.out.println("BOUNDEDBUFFER: Le produit " + item + " a ete enmagasine. Buffer PLEIN!!!");
      else
         System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete enmagasine. Il y a = " +  (count+1) + " produits.");

      ++count; // Semaphore count Signal after all actions 
      lockProducer=false; //Liberate the lock after usage*/

   }
   
   // consumer calls this method
   public synchronized String remove() {

      String item = " ";

      try{
         control.acquire(handle, CONSUMER);

         item = SharedMemory.read(handle, (out+BUFFER_BEGIN));
         out = (out + 1) % BUFFER_SIZE;
         
         System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete consomme. Il y a = " +  readIntFromSharedMemory(handle, LOAD) + " produits.");

         control.release(handle, SPACE);
      }
      catch(Throwable t)
      {
         System.out.println(t);
      }

      return item;


      /*
      Object item;
      
      //Mutual Exclusion  or use "synchronized" in java
      while (lockConsumer == true)
        ;//do nothing 

      lockConsumer = true; //Put the lock

      while (count == 0) 
         ; // do nothing
      
      // remove an item from the buffer
      
      item = buffer[out];
      out = (out + 1) % BUFFER_SIZE;

	   if ((count-1) == 0)
         System.out.println("BOUNDEDBUFFER: Le produit " + item + " a ete consomme. Buffer VIDE");
      else
         System.out.println("BOUNDEDBUFFER: Le produit " + item + " a ete consomme. Il y a = " +  (count-1) + " produits.");
      
      --count; // Semaphore count Signal after all actions
      lockConsumer=false; //Liberate the lock after usage

      return item;
      */
   }
      //Reading and writing methods
   public static void writeIntToSharedMemory(int handle, int position, int value){
      String stringValue = Integer.toString(value);
      SharedMemory.write(handle, position, stringValue);
   }
   public static int readIntFromSharedMemory(int handle, int position){
      return Integer.parseInt(SharedMemory.read(handle, position));
   }
}
