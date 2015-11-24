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
	   int handle = sm.alloc(BUFFER_SIZE+BUFFER_BEGIN, 15);

     sm.write(handle, PRODUCER, "0"); //producer = 0;
     sm.write(handle, CONSUMER, "0"); //consumer = 0;
     sm.write(handle, TURN, "0"); //turn = 0;
     sm.write(handle, COUNT, "0"); //count = 0;
     sm.write(handle, IN, "0");
     sm.write(handle, OUT, "0");

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
