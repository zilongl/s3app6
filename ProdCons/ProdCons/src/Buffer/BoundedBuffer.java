package Buffer;

public class BoundedBuffer implements Buffer
{ 
   private static final int   BUFFER_SIZE = 3;

   private volatile int count;
   private volatile boolean lockConsumer, lockProducer;

   private volatile int in;   // points to the next free position in the buffer
   private volatile int out;  // points to the next full position in the buffer
   private Object[] buffer;
       
   public BoundedBuffer()
   {
      // buffer is initially empty
      count = 0;
      in = 0;
      out = 0;
      lockConsumer = false;
      lockProducer = false;
      
      buffer = new Object[BUFFER_SIZE];
   }

   // producer calls this method
   public void insert(Object item) {

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
      lockProducer=false; //Liberate the lock after usage
   }
   
   // consumer calls this method
   public Object remove() {
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
   }

}
