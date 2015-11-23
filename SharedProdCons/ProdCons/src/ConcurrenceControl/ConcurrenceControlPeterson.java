package ConcurrenceControl;

import static Buffer.BoundedBuffer.*;
import java.lang.InterruptedException;
import SharedMemoryUtilities.SharedMemory;

public class ConcurrenceControlPeterson implements ConcurrenceControl{

  	private SharedMemory sm = new SharedMemory();

  	@Override
	public synchronized void acquire(int handle, int process){
		int other = 1-process;
		int position = process + 1;
		int positionOther = other + 1;
		writeIntToSharedMemory(handle, TURN, process);
		System.out.println("WRITED PROCESS IN TURN");
		System.out.println(SharedMemory.read(handle, positionOther) + "       " + SharedMemory.read(handle, TURN));
		while (readIntFromSharedMemory(handle, positionOther) <= 0 && readIntFromSharedMemory(handle, TURN) == process){
			try{
				//System.out.println("WAIT");
				Thread.sleep(1);
			}	
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		writeIntToSharedMemory(handle, position, readIntFromSharedMemory(handle, position)-1);
		System.out.println("WRITED-1");
	}

	@Override
	public synchronized void release(int handle, int position){
		writeIntToSharedMemory(handle, position, readIntFromSharedMemory(handle, position)+1);
		System.out.println(readIntFromSharedMemory(handle, position));
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