package Buffer;

import ConcurrenceControl.*;
import SharedMemoryUtilities.SharedMemory;

public class BoundedBuffer implements Buffer
{ 
   public static final int   BUFFER_SIZE = 3;

   public static final int PRODUCER = 0; 
   public static final int CONSUMER = 1; 
   public static final int TURN = 2;   
   public static final int COUNT = 3;
   public static final int BUFFER_BEGIN = 4;
   private volatile int in;  // points to the next free position in the buffer
   private volatile int out; // points to the next full position in the buffer
   private volatile int count;
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
      count = 0;
   }

   // producer calls this method
   public synchronized void insert(String item) {

      try{
         boolean full = false;
         while (full == false || count >= 3){
            control.acquire(handle, PRODUCER);
            count = readIntFromSharedMemory(handle, COUNT); // critical section
            control.release(handle, PRODUCER);
            Thread.sleep(1);
            full = true;
         }

         System.out.println("AQUIRED");
         SharedMemory.write(handle, (in+BUFFER_BEGIN), item);
         in = (in + 1) % BUFFER_SIZE;

         System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete enmagasine. Il y a = " + (count+1) + " produits.");

         control.acquire(handle, PRODUCER);
         writeIntToSharedMemory(handle, COUNT, (count+1)); // critical section
         control.release(handle, PRODUCER);
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
         boolean empty = false;

         while (empty == false || count <=0){
            control.acquire(handle, CONSUMER);
            count = readIntFromSharedMemory(handle, COUNT); // critical section
            control.release(handle, CONSUMER);
            Thread.sleep(1);
            empty = true;
         }

         item = SharedMemory.read(handle, (out+BUFFER_BEGIN));
         out = (out + 1) % BUFFER_SIZE;
         
         System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete consomme. Il y a = " +  (count-1) + " produits.");

         control.acquire(handle, CONSUMER);
         writeIntToSharedMemory(handle, COUNT, (count-1)); // critical section
         control.release(handle, CONSUMER);

         
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
