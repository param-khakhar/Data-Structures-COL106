
public class Pair<K1,K2>{
	K1 k1;
	K2 k2;
	boolean recentlyEmpty = false;
	
	Pair(K1 s1,K2 s2){
		this.k1 = s1;
		this.k2 = s2;
	
	}
	
	public K1 First() {
		return k1;
	}
	
	public K2 Second() {
		return k2; 
	}
	
	public boolean isRecentlyEmpty() {
		return recentlyEmpty;
	}
	
	public void setRecentlyEmpty(boolean p) {
		recentlyEmpty = p;
	}
	
	public String toString() {
		return k1.toString() + k2.toString();
	}
	
	public void setSecond(K2 s4) {
		k2 = s4;
	}
	
	public int compareTo(Pair<K1,K2> p) {
		return this.toString().compareTo(p.toString());
	}
}
