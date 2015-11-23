package ConcurrenceControl;

import static Buffer.BoundedBuffer.*;
import java.lang.InterruptedException;
import SharedMemoryUtilities.SharedMemory;

public class ConcurrenceControlPeterson implements ConcurrenceControl{

  	private SharedMemory sm = new SharedMemory();

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

	@Override
	public synchronized void release(int handle, int position){
		writeIntToSharedMemory(handle, position, 0);
	}

   //Reading and writing methods
   public void writeIntToSharedMemory(int handle, int position, int value){
      String stringValue = Integer.toString(value);
      sm.write(handle, position, stringValue);
   }
   public int readIntFromSharedMemory(int handle, int position){
      return Integer.parseInt(sm.read(handle, position));
   }
}