package Buffer;

public interface Buffer
{
	/**
	 * insert an item into the Buffer.
	 */
	public abstract void insert(String item);

	/**
	 * remove an item from the Buffer.
	 */
	public abstract String remove();
}
