package ConcurrenceControl;

public interface ConcurrenceControl {
       /**
	 	* aquire the right for the critical section.
	 	*/
       public abstract void acquire(int handle, int process);
       /**
	 	* release the right when quitting critical section.
	 	*/
       public abstract void release(int handle, int process);
}
