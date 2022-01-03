
public class BNode<K,T> {
	private BNode<K,T> left;
	private BNode<K,T> right;
	private BNode<K,T> parent;
	private T value;
	private K key;
	public BNode(BNode<K,T> l,BNode<K,T> r,BNode<K,T> p,T val,K key2) {
		left = l;
		right = r;
		value = val;
		key = key2;
		parent = p;
	}
	
	public int compareTo(BNode<K,T> n) {
		String [] arr1 = key.toString().split(" ");
		String [] arr2 = n.getKey().toString().split(" ");
		int result = arr1[0].compareTo(arr2[0]);
		if(result==0) {
			return arr1[1].compareTo(arr2[1]);
		}
		else {
			return result;
		}
	}
	
	public int compareTo1(BNode<K,T> n) {
		String [] arr1 = key.toString().split(" ");
		String [] arr2 = n.getKey().toString().split(" ");
		int result = arr1[0].compareTo(arr2[0]);
		return result;
	}
	
	public BNode<K,T> getRight(){
		return right;
	}
	
	public BNode<K,T> getLeft(){
		return left;
	}
	
	public T getValue() {
		return value;
	}
	
	public K getKey() {
		return key;
	}
	
	public void setKey(K k1) {
		key = k1;
	}
	
	public void setRight(BNode<K,T> r) {
		right = r;
	}
	
	public void setLeft(BNode<K,T> l) {
		left = l;
	}
	
	public void setValue(T v) {
		value = v;
	}
	
	public BNode<K,T> getParent(){
		return parent;
	}
	
	public void setParent(BNode<K,T> p1) {
		parent = p1;
	}
	
	public String toString() {
		return key.toString();
	}
}
