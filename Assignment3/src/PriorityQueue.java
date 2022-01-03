public class PriorityQueue<V> implements QueueInterface<V>{

    private NodeBase<V>[] queue;
    private int capacity, currentSize;
	
    //TODO Complete the Priority Queue implementation
    // You may create other member variables/ methods if required.
    @SuppressWarnings("unchecked")
	public PriorityQueue(int capacity) {
    	this.queue = new NodeBase[capacity];
    	this.currentSize = 0;
    	this.capacity = capacity;
    }

    public int size() {
    	return currentSize;
    }

    public boolean isEmpty() {
    	return (currentSize==0);
    }
	
    public boolean isFull() {
    	return (currentSize==capacity);
    }

    public void enqueue(Node<V> node) {
    	if(currentSize==0) {
    		queue[0] = node;
    		currentSize += 1;
    	}
    	else if(!this.isFull()) {
    		int index = currentSize;
    		while((index>0)&&(queue[index-1].getPriority()>node.getPriority())) {
    			queue[index] = queue[index-1];
    			index -= 1;
    		}
    		queue[index] = node;
    		currentSize += 1;
    	}
    }

    // In case of priority queue, the dequeue() should 
    // always remove the element with minimum priority value
    public NodeBase<V> dequeue() {
    	if(this.isEmpty()) {
    		return null;
    	}
    	NodeBase<V> discard = queue[0];
    	for(int i=0;i<currentSize-1;i++) {
    		queue[i] = queue[i+1];
    	}
    	queue[currentSize-1] = null;
    	currentSize -= 1;
    	return discard;
    	
    }

    public void display () {
	if (this.isEmpty()) {
            System.out.println("Queue is empty");
	}
	for(int i=0; i<currentSize; i++) {
            queue[i+1].show();
	}
    }
}
