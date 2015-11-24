package ConcurrenceControl;

import static Buffer.BoundedBuffer.*;
import java.lang.InterruptedException;
import SharedMemoryUtilities.SharedMemory;

/**
 *This class implement the Peterson's solution with acquire and release.
 *This class read and write values into the Shared Memory for a inter-process communication.
 *@author Zi Long
 *
 */
public class ConcurrenceControlPeterson implements ConcurrenceControl{

  	private SharedMemory sm = new SharedMemory();

  	/**
  	*aquire the space for the process with peterson's solution (For 2 process only)
  	*@param handle The memory adress
  	*@param process The process number(0 or 1)
  	*/
  	@Override
	public synchronized void acquire(int handle, int process){
		int other = 1-process;
		writeIntToSharedMemory(handle, TURN, process);
		writeIntToSharedMemory(handle, process, 1);
		while (readIntFromSharedMemory(handle, other) == 1 && readIntFromSharedMemory(handle, TURN) == process){
			try{
				//System.out.println("WAIT");
				Thread.sleep(1);
			}	
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	/**
	*Release the space for the process with peterson's solution (For 2 process only)
	*@param handle The memory adress
	*@param process The process number(0 or 1)
  	*/
	@Override
	public synchronized void release(int handle, int position){
		writeIntToSharedMemory(handle, position, 0);
	}

	 //Reading and writing methods
	/**
	*Write an integer into a String talbe of the Shared Memory
	*@param handle The memory adress
	*@param position The position of the string in SharedMemory
	*@param value The integer to write
	*/
   public void writeIntToSharedMemory(int handle, int position, int value){
      String stringValue = Integer.toString(value);
      sm.write(handle, position, stringValue);
   }
   /**
   *Get an integer from a String talbe of the Shared Memory
   *@param handle The memory adress
   *@param position The position of the string in SharedMemory
   *@return The integer to get
   */
   public int readIntFromSharedMemory(int handle, int position){
      return Integer.parseInt(sm.read(handle, position));
   }
}