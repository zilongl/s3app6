package ConcurrenceControl;

public interface ConcurrenceControl {
       
       public abstract void acquire(int handle, int process);
       
       public abstract void release(int handle, int process);
}
