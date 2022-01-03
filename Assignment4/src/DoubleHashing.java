import java.lang.Math;

public class DoubleHashing<K, T> implements MyHashTable_<K,T>{
	private int size;
	private Pair<K,T> [] arr;
	DoubleHashing(int s){
		size = s;
		arr = new Pair[size];
	}
	
	public int insert(K key,T s) {
		int calc = 1;
		boolean inserted = false;
		long h1 = djb2(key.toString(),size);
		long h2 = sdbm(key.toString(),size);
		int h = (int)(h1)%size;
		while((inserted == false)&&(calc<=size)) {
			
			if ((arr[h] == null)||(arr[h].isRecentlyEmpty())){
				arr[h] = new Pair<>(key,s);
				inserted = true;
			}
			else {
			h = (int)(h1+calc*h2)%size;
			calc += 1;
			}
		}
		return calc;
	}
	
	public int update(K key,T s) {
		int calc = 1;
		boolean found = false;
		boolean exist = false;
		long h1 = djb2(key.toString(),size);
		long h2 = sdbm(key.toString(),size);
		int h = (int)(h1)%size;
		while((calc<=size)&&(found == false)) {
			if (arr[h].First().toString().equals(key.toString())){
				if(arr[h].isRecentlyEmpty()) {
					return -1;
				}
				else {
				arr[h].setSecond(s);
				found = true;
				exist = true;
				}
			}
			else if(arr[h] == null) {
				exist = false;
				found = true;
			}
			else {
				h = (int)(h1+calc*h2)%size;
				calc += 1;
			}
		}
		if(exist) {
		return calc;
		}
		else {
			return -1;
		}
	}
	
	public boolean contains(K key) {
		int calc = 1;
		boolean found = false;
		boolean exist = false;
		long h1 = djb2(key.toString(),size);
		long h2 = sdbm(key.toString(),size);
		int h = (int)(h1)%size;
		while((found == false)&&(calc<size)) {
			if (arr[h].First().toString().equals(key.toString())){
				if(arr[h].isRecentlyEmpty()) {
					exist = false;
					found = true;
				}
				else {
				exist = true;
				found = true;
				}
			}
			else if(arr[h]==null) {
				exist = false;
				found = true;
			}
			else {
				h = (int)(h1+calc*h2)%size;
				calc += 1;
			}
		}
		return exist;
	}
	
	public int delete(K key){
		int calc = 1;
		boolean found = false;
		boolean exist = false;
		long h1 = djb2(key.toString(),size);
		long h2 = sdbm(key.toString(),size);
		int h = (int)(h1)%size;
		while((found == false)&&(calc<size)) {
			if ((arr[h].First().toString().equals(key.toString()))){
				if(!arr[h].isRecentlyEmpty()) {
				found = true;
				arr[h].setRecentlyEmpty(true);
				arr[h].setSecond(null);
				exist = true;
				}
				else {
					exist = false;
					found = true;
				}
			}
			else if(arr[h]==null) {
				exist = false;
				found = true;
			}
			else {
				h = (int)(h1+calc*h2)%size;
				calc += 1;
			}
		}
		if((exist&&(found))) {
		return calc;
		}
		else {
			return -1;
		}
	}
	
	public T get(K key) throws NotFoundException {
		boolean exist = false;
		T s = null;
		int calc = 1;
		boolean found = false;
		long h1 = djb2(key.toString(),size);
		long h2 = sdbm(key.toString(),size);
		int h = (int)(h1)%size;
		while((found == false)&&(calc<size)) {
			if (arr[h].First().toString().equals(key.toString())){
				if(!arr[h].isRecentlyEmpty()) {
				found = true;
				s = arr[h].Second();
				exist = true;
				}
				else {
					exist = false;
					found = true;
				}
			}
			else if(arr[h]==null) {
				exist = false;
				found = true;
			}
			else {
				h = (int)(h1+calc*h2)%size;
				calc += 1;
			}
		}
		if(s == null) {
			throw new NotFoundException();
		}
		else {
			return s;
		}
	}
	
	public String address(K key) throws NotFoundException {
		boolean exist = true;
		int calc = 1;
		boolean found = false;
		long h1 = djb2(key.toString(),size);
		long h2 = sdbm(key.toString(),size);
		int h = (int)(h1)%size;
		int index = h;
		while((found == false)&&(calc<size)) {
			if (arr[h].First().toString().equals(key.toString())){
				if(arr[h].recentlyEmpty) {
				found = true;
				exist = false;
				}
				else {
				index = h;
				found = true;
				}
			}
			else if(arr[h]==null) {
				exist = false;
				found = true;
			}
			else {
				h = (int)(h1+calc*h2)%size;
				calc += 1;
			}
		}
		String s = Integer.toString(index);
		if(!exist) {
			throw new NotFoundException();
		}
		else {
			return s;
		}
	}
	

	public static long djb2(String str, int hashtableSize) { 
	    long hash = 5381; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = ((hash << 5) + hash) + str.charAt(i); 
	    } 
	    return Math.abs(hash) % hashtableSize; 
	}
 
	public static long sdbm(String str, int hashtableSize) { 
	    long hash = 0; 
	    for (int i = 0; i < str.length(); i++) { 
	        hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash; 
	    } 
	    return Math.abs(hash) % (hashtableSize - 1) + 1; 
	}
}
