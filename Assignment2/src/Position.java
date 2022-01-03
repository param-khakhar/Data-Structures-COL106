class Position<T> implements Position_<T>{
	
	T val;
	private Position<T> next;
	public Position(T value){
		val = value;
		next = null;
		}
	
	public T value() {
		return val;
	}
	
	public void setNext(Position<T> pos) {
		this.next = pos;
	}
	
	public Position<T> after(){
		return next;
	}
}