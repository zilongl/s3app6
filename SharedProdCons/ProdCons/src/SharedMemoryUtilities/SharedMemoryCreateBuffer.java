package SharedMemoryUtilities;

import static Buffer.BoundedBuffer.*;
import java.io.*;


public class SharedMemoryCreateBuffer
{

  public static void main(String[] args)
  {
    System.out.println("Starting Shared Memory Example Write");

    try
    {
      SharedMemory sm = new SharedMemory();

      System.out.println("Allocation de la memoire");
	   int handle = sm.alloc(9,15);

     sm.write(handle, IN, "0"); //in = 0;
     sm.write(handle, OUT, "0"); //out = 0;
     sm.write(handle, LOAD, "0"); //load = 0;
     sm.write(handle, SPACE, Integer.toString(BUFFER_SIZE)); //space = BUFFER_SIZE;
     sm.write(handle, LOCKCONSUMER, "1"); //lockConsumer = 1;
     sm.write(handle, LOCKPRODUCER, "1"); //lockProducer = 1; 

     sm.displaySharedMemory(handle);

	   InputStreamReader converter = new InputStreamReader (System.in);
	   BufferedReader		in = new BufferedReader (converter);
      String anyKey = null;
	   try {
	      anyKey = in.readLine();
	   } catch( Exception e ) {
	      System.out.println("Erreur de lecture de string");
	   }

      System.out.println("On doit liberer la memoire partage...");
      sm.free(handle);
    }
    
    catch(Throwable t)
    {
      System.out.println(t);
    }
    
    System.out.println("Exiting Shared Memory Example");
  }
}
