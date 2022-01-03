import java.lang.Math;

public class SeparateChaining<K,T> implements MyHashTable_<K,T> {
	int size;
	private Pair<K,BinarySearchTree<K,T>> [] arr;
	SeparateChaining(int k){
		size = k;
		arr =new Pair[size];
	}
	
	/**public int insert(K key,T object) {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		int count;
	
		if (arr[index] == null) {
			arr[index] = new Pair<>(key,new BinarySearchTree<>());
			count = arr[index].Second().insert(new BNode<K,T>(null,null,null,object,key));
		}
		else {
			count = arr[index].Second().insert(new BNode<K,T>(null,null,null,object,key));
		}
		return count;
	}**/
	
	public int insert(K key,T object) {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		int count;
	
		if (arr[index] == null) {
			arr[index] = new Pair<>(key,new BinarySearchTree<>());
			count = arr[index].Second().insert(new BNode<K,T>(null,null,null,object,key));
		}
		else {
			count = arr[index].Second().insert(new BNode<K,T>(null,null,null,object,key));
		}
		return count;
	}
	
	/**public int update(K key,T object) {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		BinarySearchTree temp = arr[index].Second();
		BNode<K,T> r = temp.getRoot();
		int count = 1;
		boolean found = false;
		BNode<K,T> x = r;
				while((x!=null)&&(key.toString().compareTo(x.getKey().toString())!=0)) {
			if(key.toString().compareTo(x.getKey().toString())<0) {
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
			count += 1;
		}
		if(x!=null) {
		x.setValue(object);
		return count;
		}
		else {
			return -1;
		}
	}**/
	
	public int update(K key,T object) {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		BinarySearchTree temp = arr[index].Second();
		BNode<K,T> r = temp.getRoot();
		int count = 1;
		boolean found = false;
		BNode<K,T> y = r;
		if(y!=null) {
		String s = key.toString();
		String n = y.toString();
		String [] arr1 = s.split(" ");
		String [] arr2 = n.split(" ");
		while((y!=null)&&!((arr1[0].compareTo(arr2[0])==0)&&(arr1[1].compareTo(arr2[1])==0))) {
			if(arr1[0].compareTo(arr2[0])<0) {
				y = y.getLeft();
			}
			else if(arr1[0].compareTo(arr2[0])>0) {
				y = y.getRight();
			}
			else {
				y = y.getRight();
			}
			if(y!=null) {
			arr2 = y.getKey().toString().split(" ");
			count += 1;
			}
		}
		}
		if(y!=null) {
		y.setValue(object);
		return count;
		}
		else {
			return -1;
		}
	}
	
	/**public int delete(K key) {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		int result = arr[index].Second().delete(key);
		return result;
	}**/
	
	public int delete(K key) {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		int result = arr[index].Second().delete(key);
		return result;
	}
	
	/**public boolean contains(K key){
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		BNode<K,T> n = arr[index].Second().search(key);
		if (n!=null) {
			return true;
		}
		else {
			return false;
		}
	}**/
	
	public boolean contains(K key){
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		BNode<K,T> n = arr[index].Second().search(key);
		if (n!=null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**public T get(K key) throws NotFoundException{
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		BNode<K,T> x = arr[index].Second().search(key);
		if (x!=null) {
			return x.getValue();
		}
		else {
			throw new NotFoundException();
		}
	}**/
	
	public T get(K key) throws NotFoundException{
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		BNode<K,T> x = arr[index].Second().search(key);
		if (x!=null) {
			return x.getValue();
		}
		else {
			throw new NotFoundException();
		}
	}
	
	/**public String address(K key) throws NotFoundException {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		String temp = arr[index].Second().searchAddress(key);
		if (temp != null) {
		return Integer.toString(index)+temp;
		}
		else {
			throw new NotFoundException();
		}
	}**/
	
	public String address(K key) throws NotFoundException {
		String Key = key.toString();
		String [] arrTemp = Key.split(" ");
		String ind = arrTemp[0] + arrTemp[1];
		int index = (int)djb2(ind.toString(),size);
		String temp = arr[index].Second().searchAddress(key);
		if (temp != null) {
		return Integer.toString(index)+temp;
		}
		else {
			throw new NotFoundException();
		}
	}
		

	 
	 
	public static long djb2(String str, int hashtableSize) { 
	    long hash = 5381; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = ((hash << 5) + hash) + str.charAt(i); 
	    } 
	    return Math.abs(hash) % hashtableSize; 
	}
}
