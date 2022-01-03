
public class LinkedList<T> {
	
	private Node<T> head = null;
	private Node<T> last = null;
	private int size = 0;
	
	public void insert(T t) {
		Node<T> n = new Node<T>(t);
		if(size==0) {
			head = n;
			last = n;
			size = 1;
		}
		else {
			last.setNext(n);
			size += 1;
			last = n;
		}
	}
	
	public Node<T> getHead(){
		return head;
	}
	
	public int getSize() {
		return size;
	}
	
	public T remove(){
		if(size>0) {
			size -= 1;
			Node<T> curr = head;
			head = head.getNext();
			return curr.getData();
		}
			return null;
	}
}
