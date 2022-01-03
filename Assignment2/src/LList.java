import java.util.Iterator;

class LList<T> implements LinkedList_<T>{
	
	class PosIterator implements Iterator<Position_<T>>{
		private Position<T> curr;
		private Position<T> last;
		PosIterator(Position<T> node){
			curr = node;
			last = null;
		}
		
	public boolean hasNext() {
		return !(curr.after()==null);
	}
	
	public Position<T> next(){
		curr = curr.after();
		last = curr;
		return last;
	}
	}
	
	class ValIterator implements Iterator<T>{
		private PosIterator posIter;
		private ValIterator(Position<T> node){
			 posIter = new PosIterator(node); 
		}
		public boolean hasNext(){
			return (posIter.hasNext());
		}
		public T next(){
			posIter.next();
			return posIter.curr.value();
		}
	}

	private Position<T> first = new Position<T>(null);
	private Position<T> curr;
	private int count = 0;
	public LList(){
		curr = first;
	}
	
	public Position_<T> add(T e){
		Position<T> node = new Position<T>(e);
		curr.setNext(node);
		curr = node;
		count += 1;
		return curr;
	}
	
	public Iterator<Position_<T>> positions(){
	return new PosIterator(first);
	}
	
	public Iterator<T> positions1(){
		return new ValIterator(first);
	}
	
	public int count() {
		return count;
	}
	
}