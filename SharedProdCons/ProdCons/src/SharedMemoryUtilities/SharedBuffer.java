package SharedMemoryUtilities;

import Buffer.Buffer;
import SharedMemoryUtilities.SharedMemory;

public class SharedBuffer implements Buffer
{ 
   private static final int   BUFFER_SIZE = 3;

   private final int IN = 0;   // points to the next free position in the buffer
   private final int OUT = 1;  // points to the next full position in the buffer
   private final int LOAD = 2;
   private final int SPACE = 3;
   private final int LOCKCONSUMER = 4;
   private final int LOCKPRODUCER = 5;
   private final int BUFFER_BEGIN = 6;
   private int in;
   private int out;
   private int handle;
   private SharedMemory sm = new SharedMemory();

   /*Shared memory cases 
   0: in=0;
   1: out=0;
   
   count-->divided in 2 semaphores
   2: semaphore load = 0;
   3: semaphore space = N;

   4: semaphore lockConsumer = 0;
   5: semaphore lockProducer = 0;

   6+N : table with N+1 cases
   */

   public SharedBuffer(String memory_address)
   {
      handle = Integer.parseInt(memory_address);

      // variables initialisation 
      in = 0;
      out = 0;
      writeIntToSharedMemory(IN, 0); //in = 0;
      writeIntToSharedMemory(OUT, 0); //out = 0;
      writeIntToSharedMemory(LOAD, 0); //load = 0;
      writeIntToSharedMemory(SPACE, BUFFER_SIZE); //space = BUFFER_SIZE;
      writeIntToSharedMemory(LOCKCONSUMER, 1); //lockConsumer = 1;
      writeIntToSharedMemory(LOCKPRODUCER, 1); //lockProducer = 1; 
   }

   // producer calls this method
   public void insert(String item) {

      try{

      waitShared(LOCKPRODUCER);
      waitShared(SPACE);

      writeToSharedMemory(in+BUFFER_BEGIN, item);
      in = (in + 1) % BUFFER_SIZE;

      System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete enmagasine. Il y a = " +  readFromSharedMemory(LOAD) + " produits.");

      signalShared(LOAD);
      signalShared(LOCKPRODUCER);
      }
      catch(Throwable t)
      {
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
   public String remove() {

      String item = " ";

      try{
      waitShared(LOCKCONSUMER);
      waitShared(LOAD);

      item = readFromSharedMemory(out+BUFFER_BEGIN);
      out = (out + 1) % BUFFER_SIZE;
      
      System.out.println("BOUNDEDBUFFER: Le produit " + item + "a ete enmagasine. Il y a = " +  readFromSharedMemory(LOAD) + " produits.");

      signalShared(SPACE);
      signalShared(LOCKCONSUMER);
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

   // basic Semaphores functions
   public int wait(int semaphore){
      while (semaphore <= 0)
         ; // do nothing
      return semaphore-1;
   }
   public int signal(int semaphore){
      return semaphore+1;
   }

   //Complex Semaphores methods
   public void waitShared(int position){
      while (readIntFromSharedMemory(position) <= 0)
         ;// do nothing
      writeIntToSharedMemory(position, readIntFromSharedMemory(position)-1);
   }
   public void signalShared(int position){
      writeIntToSharedMemory(position, readIntFromSharedMemory(position)+1);
   }

   //Reading and writing methods
   public void writeToSharedMemory(int position, String chain){
      sm.write(handle, position, chain);
   }
   public void writeIntToSharedMemory(int position, int value){
      String stringValue = Integer.toString(value);
      sm.write(handle, position, stringValue);
   }
   public String readFromSharedMemory(int position){
      return sm.read(handle, position);
   }
   public int readIntFromSharedMemory(int position){
      return Integer.parseInt(sm.read(handle, position));
   }

}
