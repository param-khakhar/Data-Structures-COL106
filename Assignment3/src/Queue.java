// This class implements the Queue
public class Queue<V> implements QueueInterface<V>{

    //TODO Complete the Queue implementation
    private NodeBase<V>[] queue;
    private int capacity, currentSize, front, rear;
	
	@SuppressWarnings("unchecked")
	public Queue(int capacity) {
		queue = new NodeBase[capacity];
    	this.capacity = capacity;
    	this.currentSize = 0;
    	this.front = 0;
    	this.rear = 0;
    }

    public int size() {
    	return currentSize;
    }

    public boolean isEmpty() {
    	return (currentSize == 0);
    }
	
    public boolean isFull() {
    	return (currentSize == capacity);
    }

    public void enqueue(Node<V> node) {
    	if(!this.isFull()) {
    	queue[rear] = node;
    	rear += 1;
    	currentSize += 1;
    	}
    }

    public NodeBase<V> dequeue() {
    	if(this.isEmpty()) {
    		return null;
    	}
    	NodeBase<V> discard = queue[front];
    	for(int i=front;i<rear-1;i++) {
    		queue[i] = queue[i+1];
    	}
    	queue[rear-1] = null;
    	rear -= 1;
    	currentSize -= 1;
    	return discard;
    }
}