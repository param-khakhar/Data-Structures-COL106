
public class Node<T> {
	private T data;
	private Node<T> next;
	
	public Node(T t) {
		data = t;
	}
	
	public T getData() {
		return  data;
	}
	
	public void setData(T t1) {
		data = t1;
	}
	
	public void setNext(Node<T> n) {
		next = n;
	}
	
	public Node<T> getNext(){
		return next;
	}
	
	public boolean hasNext() {
		return next != null;
	}
}
