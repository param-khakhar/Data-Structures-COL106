import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Seller<V> extends SellerBase<V> {
    /*protected PriorityQueue<V> catalog; // Shared priority queue
    protected Lock lock; //Shared lock
    protected Condition full, empty; // Shared condition variables
    private int sleepTime; // Sleep duration (in ms) for current thread
    protected Queue<V> inventory; // List of items (shared between sellers)*/
	
    public Seller (int sleepTime, int catalogSize, Lock lock, Condition full, Condition empty, PriorityQueue<V> catalog, Queue<V> inventory) {
        //TODO Complete the constructor method by initializing the attibutes
        // ...
    	this.catalog = catalog;
    	this.lock = lock;
    	this.full = full;
    	this.empty = empty;
    	setSleepTime(sleepTime);
    	this.inventory = inventory;
    }
    
    public void sell() throws InterruptedException {
	try {
		lock.lock();
		while(catalog.isFull()) {	
		empty.await();
		}
	if(!inventory.isEmpty()) {
		NodeBase<V> node = inventory.dequeue();
		//System.out.println("Added: "+node.priority);
		catalog.enqueue((Node<V>)node);
	}	
	} catch(Exception e) {
            e.printStackTrace();
	} finally {
			full.signal();
            lock.unlock();
	}
    }
}