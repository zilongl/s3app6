package Tools;

/**
 *This class control sleep for the current thread.
 */
public class SleepTools
{
/**
 *This method sleep a random number between 0 and NAP_TIME 
 */
	public static void nap() {
		nap(NAP_TIME);
	}

/**
 *This method sleep a random number between 0 and duration
 */
	public static void nap(int duration) {
        	int sleeptime = (int) (duration * Math.random() );
        	try { Thread.sleep(sleeptime*1000); }
        	catch (InterruptedException e) {}
	}

	private static final int NAP_TIME = 5;
}
