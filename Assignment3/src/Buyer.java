import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Buyer<V> extends BuyerBase<V> {
    /*protected PriorityQueue<V> catalog; // The shared priority queue
    protected Lock lock; // Shared lock
    protected Condition full, empty; // Shared condition variables
    private int sleepTime; // Sleep duration (in ms) for current thread
    private int iteration; // No. of iterations for buyer threads*/
    public Buyer (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, int iteration) {
    	this.catalog = catalog;
    	this.lock = lock;
    	this.full = full;
    	this.empty = empty;
    	setSleepTime(sleepTime);
    	setIteration(iteration);
    }
    
    public void buy() throws InterruptedException {
	try {
		lock.lock();
		while(catalog.isEmpty()) {
			full.await();
		}
		NodeBase<V> n = catalog.dequeue();
	    System.out.print("Consumed "); // DO NOT REMOVE (For Automated Testing)
            n.show(); // DO NOT REMOVE (For Automated Testing)
            // ...
	} catch (Exception e) {
            e.printStackTrace();
	} finally {
            //TODO Complete this block
		empty.signal();
		lock.unlock();
	}
    }
    }

